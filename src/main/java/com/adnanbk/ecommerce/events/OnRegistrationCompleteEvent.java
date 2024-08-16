package com.adnanbk.ecommerce.events;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public final class OnRegistrationCompleteEvent extends ApplicationEvent {
    private  RegistrationEventSource eventSource;

    public OnRegistrationCompleteEvent(RegistrationEventSource eventSource) {
        super(eventSource);
        this.eventSource = eventSource;

    }


}
