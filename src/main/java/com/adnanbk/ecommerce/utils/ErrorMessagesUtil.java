package com.adnanbk.ecommerce.utils;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ErrorMessagesUtil {

    private  final MessageSource messageSource;

   public String getDefaultMessage(String code) {
       return messageSource.getMessage(code,null, LocaleContextHolder.getLocale());
   }
    public String getMessage(String code) {
        return messageSource.getMessage(code,null,LocaleContextHolder.getLocale() );
    }

}
