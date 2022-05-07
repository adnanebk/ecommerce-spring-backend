package com.adnanbk.ecommerce.events;


import com.adnanbk.ecommerce.models.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.persistence.Transient;

@Getter
@Setter
public final class OnRegistrationCompleteEvent extends ApplicationEvent {
    private  String url;
    @Transient
    private  AppUser user;

    public OnRegistrationCompleteEvent(String url, AppUser user) {
        super(user);
        this.url = url;
        this.user = user;
    }


}
