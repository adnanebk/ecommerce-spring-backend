package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.UserOrder;
import com.adnanbk.ecommerce.services.UserOderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {



    private final UserOderService userOderService;

    @GetMapping
    @ApiOperation(value = "get orders of the authenticated user")
    public List<UserOrder> getOrdersByUserName(Principal principal) {
        return userOderService.findByEmail(principal.getName());
    }

    @PostMapping
    @ApiOperation(value = "create a new order")
    public ResponseEntity<UserOrder> saveOrder(@RequestBody @Valid UserOrder userOrder, Principal principal) {
        UserOrder savedUserOrder = userOderService.saveOrder(userOrder, principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUserOrder.getId()).toUri();
        return ResponseEntity.created(location).body(savedUserOrder);
    }
}
