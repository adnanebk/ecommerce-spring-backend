package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.UserOrderDto;
import com.adnanbk.ecommerce.mappers.OrderMapper;
import com.adnanbk.ecommerce.services.UserOderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {



    private final UserOderService userOderService;
    private final OrderMapper orderMapper;

    @GetMapping
    @ApiOperation(value = "get orders of the authenticated user")
    public List<UserOrderDto> getOrdersByUserName(Principal principal) {
        return userOderService.findByEmail(principal.getName())
                .stream().map(orderMapper::toDto).toList();
    }

    @PostMapping
    @ApiOperation(value = "create a new order")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOrderDto saveOrder(@RequestBody @Valid UserOrderDto userOrderDto) {

        return Optional.of(userOrderDto)
                .map(orderMapper::toEntity)
                .map(userOderService::saveOrder)
                .map(orderMapper::toDto).orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "remove a user order")
    public void remove(@PathVariable long id) {
      userOderService.remove(id);
    }
}
