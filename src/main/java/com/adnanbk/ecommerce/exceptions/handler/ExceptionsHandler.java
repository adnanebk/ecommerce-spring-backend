package com.adnanbk.ecommerce.exceptions.handler;

import com.adnanbk.ecommerce.dto.ApiErrorDto;
import com.adnanbk.ecommerce.dto.ResponseError;
import com.adnanbk.ecommerce.exceptions.*;
import com.adnanbk.ecommerce.exceptions.factories.ResponseErrorFactory;
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
            return generateUniqueErrorMessage(cause);
        }
        if (ex instanceof DataIntegrityViolationException cause) {

            return generateUniqueErrorMessage(cause);
        }
        return new ApiErrorDto("An error has been thrown during database modification ");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<ResponseError> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors().forEach(
                er -> errors.add(ResponseErrorFactory.create(ex.getBindingResult().getTarget(),er.getField(), er.getDefaultMessage()))
        );
        ex.getBindingResult().getGlobalErrors()
                .forEach(x -> {
                    if (x.getDefaultMessage() != null)
                        errors.add(ResponseErrorFactory.create(Objects.requireNonNull(x.getCode()), x.getDefaultMessage()));
                });
        return generateApiErrors(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ApiErrorDto handleValidationException(ValidationException ex) {
        return new ApiErrorDto(formatMessage(formatMessage(ex.getMessage())));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorDto badCredentialsException(BadCredentialsException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()));
    }
    @ExceptionHandler(ProductSkuAlreadyExistException.class)
    public ApiErrorDto productSkuAlreadyExistException(ProductSkuAlreadyExistException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ApiErrorDto productNotFoundException(ProductNotFoundException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()));
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public ApiErrorDto invalidPasswordException(InvalidPasswordException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()),Set.of(new ResponseError("currentPassword",formatMessage(ex.getMessage()))));
    }
 

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorDto jWTVerificationException(JWTVerificationException ex) {
        if(ex instanceof TokenExpiredException)
            return new ApiErrorDto(formatMessage(ex.getMessage()),"jwt.expired");

        return new ApiErrorDto(formatMessage(ex.getMessage()));
    }

    @ExceptionHandler(UserNotEnabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorDto userNotEnabledException(UserNotEnabledException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()),"user.not.enabled");
    }
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto invalidTokenException(InvalidTokenException ex) {
        return new ApiErrorDto(formatMessage(ex.getMessage()),"user.not.enabled");
    }



    private Set<ResponseError> generateErrors(ConstraintViolationException cause) {
        Set<ResponseError> errors = new HashSet<>();
        for (ConstraintViolation<?> violation : cause.getConstraintViolations()) {
            if (violation.getPropertyPath() != null)
                errors.add(ResponseErrorFactory.create(violation.getLeafBean(),violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errors;
    }

    private ApiErrorDto generateUniqueErrorMessage(Exception cause) {
        ResponseError responseError = ResponseErrorFactory.create(this.errorMessagesUtil.getMessage("error.already-exist"),cause);
        if (responseError == null)
            return new ApiErrorDto("An error has been thrown during database modification");

        return generateApiErrors(Set.of(responseError));

    }

    private ApiErrorDto generateApiErrors(Set<ResponseError> responseErrors) {
        return new ApiErrorDto("Try to fix these errors", responseErrors);
    }
    private String formatMessage(String message) {
        return message.startsWith("error.")?errorMessagesUtil.getMessage(message):message;
    }
}
