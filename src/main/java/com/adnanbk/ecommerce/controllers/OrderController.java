package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.UserOrder;
import com.adnanbk.ecommerce.services.UserOderService;
import com.adnanbk.ecommerce.validations.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/orders/")
@RequiredArgsConstructor
public class OrderController {


    @InitBinder("userOrder") // add this parameter to apply this binder only to request parameters with this name
    protected void bidValidator(WebDataBinder binder) {
        binder.addValidators(orderValidator);
    }

    private final UserOderService userOderService;
    private final OrderValidator orderValidator;

    @GetMapping("/username/{userName}")
    public List<UserOrder> getOrdersByUserName(@PathVariable String userName) {
        return userOderService.findByUserName(userName);
    }

    @PostMapping
    public ResponseEntity<UserOrder> saveOrder(@RequestBody @Valid UserOrder order, Principal principal) {
        UserOrder savedUserOrder = userOderService.saveOrder(order, principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUserOrder.getId()).toUri();
        return ResponseEntity.created(location).body(savedUserOrder);
    }
}