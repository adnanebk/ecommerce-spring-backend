package com.adnanbk.ecommerce.exceptions.handler;

import com.adnanbk.ecommerce.dto.ApiErrorDto;
import com.adnanbk.ecommerce.dto.ResponseError;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.adnanbk.ecommerce.exceptions.factories.ResponseErrorFactory.createResponseError;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class ExceptionsHandler {

    private final ErrorMessagesUtil errorMessagesUtil;

    @ExceptionHandler({PersistenceException.class, ConstraintViolationException.class})
    public ApiErrorDto handleConstraintViolation(RuntimeException ex) {

        if (NestedExceptionUtils.getMostSpecificCause(ex) instanceof ConstraintViolationException cause) {

            return generateApiErrors(generateErrors(cause));
        }
        if (NestedExceptionUtils.getMostSpecificCause(ex) instanceof SQLIntegrityConstraintViolationException cause) {
            return returnUniqueErrorMessage(cause);
        }
        if (ex instanceof DataIntegrityViolationException cause) {

            return returnUniqueErrorMessage(cause);
        }
        return new ApiErrorDto("An error has been thrown during database modification ");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<ResponseError> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors().forEach(
                er -> errors.add(createResponseError(er.getField(), er.getDefaultMessage()))
        );
        ex.getBindingResult().getGlobalErrors()
                .forEach(x -> {
                    if (x.getDefaultMessage() != null)
                        errors.add(createResponseError(Objects.requireNonNull(x.getCode()), x.getDefaultMessage()));
                });
        return generateApiErrors(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ApiErrorDto handleValidationException(ValidationException ex) {
        return new ApiErrorDto(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorDto badCredentialsException(BadCredentialsException ex) {
        return new ApiErrorDto(ex.getMessage());
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ApiErrorDto invalidPasswordException(InvalidPasswordException ex) {
        return new ApiErrorDto(ex.getMessage(),Set.of(new ResponseError(ex.getFieldName(),ex.getMessage())));
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorDto jWTVerificationException(JWTVerificationException ex) {
        if(ex instanceof TokenExpiredException)
            return new ApiErrorDto(ex.getMessage(),"jwt.expired");

        return new ApiErrorDto(ex.getMessage());
    }

    @ExceptionHandler(UserNotEnabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorDto userNotEnabledException(UserNotEnabledException ex) {
        return new ApiErrorDto(ex.getMessage(),"user.not.enabled");
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    public String invalidTokenException(InvalidTokenException ex) {
        return "<h2>" + ex.getMessage() + "</h2>";
    }


    private Set<ResponseError> generateErrors(ConstraintViolationException cause) {
        Set<ResponseError> errors = new HashSet<>();
        for (ConstraintViolation<?> violation : cause.getConstraintViolations()) {
            if (violation.getPropertyPath() != null)
                errors.add(createResponseError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errors;
    }

    private ApiErrorDto returnUniqueErrorMessage(Exception cause) {
        ResponseError responseError = createResponseError(cause.getMessage(),errorMessagesUtil);
        if (responseError == null)
            return new ApiErrorDto("An error has been thrown during database modification");

        return generateApiErrors(Set.of(responseError));

    }

    private ApiErrorDto generateApiErrors(Set<ResponseError> responseErrors) {
        return new ApiErrorDto("Try to fix these errors", responseErrors);
    }
}
