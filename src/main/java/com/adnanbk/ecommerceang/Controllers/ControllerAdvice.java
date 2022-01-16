package com.adnanbk.ecommerceang.Controllers;

import com.adnanbk.ecommerceang.dto.ApiError;
import com.adnanbk.ecommerceang.dto.ResponseError;
import com.adnanbk.ecommerceang.exceptions.InvalidTokenException;
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
public class ControllerAdvice {


    @ExceptionHandler({PersistenceException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(RuntimeException ex) {
        System.err.println("******persistence exception******" + ex.getMessage());

        if (NestedExceptionUtils.getMostSpecificCause(ex) instanceof ConstraintViolationException cause) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Try to fix these errors", generateErrors(cause));
            return ResponseEntity.badRequest().body(apiError);
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
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<Object> errors = new HashSet<>();

        ex.getBindingResult().getFieldErrors().forEach(
                er ->
                        errors.add(new ResponseError(er.getField(), er.getDefaultMessage()))
        );

        ex.getBindingResult().getGlobalErrors()
                .forEach(x -> {
                    if (x.getDefaultMessage() != null)
                        errors.add(new ResponseError(Objects.requireNonNull(x.getCode()), x.getDefaultMessage()));
                    else
                        errors.add(x.getCode());
                });
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Try to fix these errors", errors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
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

        if (message.contains("product(name)"))
            message = "product name already exists";
        else if (message.contains("product(sku)"))
            message = "product sku already exists";
        else if (message.contains("product_category(name)"))
            message = "category name already exists";
        else if (message.contains("credit_card(card_name)"))
            message = "Card name already exists";
        else if (message.contains("user(user_name)"))
            message = "User name already exists";
        else if (message.contains("user(email)"))
            message = "Email already exists";
        else
            message = "An error has been thrown during database modification";

        return ResponseEntity.badRequest().body(new ApiError(message));
    }
}
