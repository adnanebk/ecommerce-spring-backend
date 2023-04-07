package com.adnanbk.ecommerce.events;


import com.adnanbk.ecommerce.events.listeners.EventSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public final class OnRegistrationCompleteEvent extends ApplicationEvent {
    private  EventSource eventSource;

    public OnRegistrationCompleteEvent(EventSource eventSource) {
        super(eventSource);
        this.eventSource = eventSource;

    }


}
