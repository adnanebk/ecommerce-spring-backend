package com.adnanbk.ecommerce.exceptions;

import com.adnanbk.ecommerce.dto.ApiErrorDto;
import com.adnanbk.ecommerce.dto.ResponseError;
import com.adnanbk.ecommerce.dto.ResponseErrorFactory;
import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestControllerAdvice
public class ExceptionsHandler {


    @ExceptionHandler({PersistenceException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(RuntimeException ex) {

        if (NestedExceptionUtils.getMostSpecificCause(ex) instanceof ConstraintViolationException cause) {

            return ResponseEntity.badRequest().body(generateApiErrors(generateErrors(cause)));
        }
        if (NestedExceptionUtils.getMostSpecificCause(ex) instanceof SQLIntegrityConstraintViolationException cause) {
            return returnUniqueErrorMessage(cause);
        }
        if (ex instanceof DataIntegrityViolationException cause) {

            return returnUniqueErrorMessage(cause);
        }
        return ResponseEntity.badRequest().body("An error has been thrown during database modification ");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<ResponseError> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors().forEach(
                er ->
                        errors.add(new ResponseError(er.getField(), er.getDefaultMessage()))
        );

        ex.getBindingResult().getGlobalErrors()
                .forEach(x -> {
                    if (x.getDefaultMessage() != null)
                        errors.add(new ResponseError(Objects.requireNonNull(x.getCode()), x.getDefaultMessage()));

                });
        return ResponseEntity.badRequest().body(generateApiErrors(errors));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorDto> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDto> badCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Exception> jWTVerificationException(JWTVerificationException ex) {

        return ResponseEntity.badRequest().body(new RuntimeException("error while verifying jwt token"));
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
                errors.add(new ResponseError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errors;
    }

    private ResponseEntity<Object> returnUniqueErrorMessage(Exception cause) {
        String message = cause.getMessage().toLowerCase();
        ResponseError responseError = ResponseErrorFactory.createResponseError(message);
        if (responseError == null)
            return ResponseEntity.badRequest().body(new ApiErrorDto("An error has been thrown during database modification"));

        return ResponseEntity.badRequest().body(generateApiErrors(Set.of(responseError)));

    }

    private ApiErrorDto generateApiErrors(Set<ResponseError> responseErrors) {
        return new ApiErrorDto(HttpStatus.BAD_REQUEST.value(), "Try to fix these errors", responseErrors);
    }
}
