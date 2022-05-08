package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.CreditCardDto;
import com.adnanbk.ecommerce.dto.CreditCardEditDto;
import com.adnanbk.ecommerce.mappers.CreditCardMapper;
import com.adnanbk.ecommerce.services.CreditCardService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creditCards")
@AllArgsConstructor
public class CreditCardController {


    private CreditCardService creditCardService;
    private CreditCardMapper creditCardMapper;

    @GetMapping
    @ApiOperation(value = "get all credit cards by the email of the authenticated a user")
    public List<CreditCardDto> getUserCreditCards(Principal principal) {
        return creditCardService.getByEmail(principal.getName())
                .stream().map(creditCardMapper::toDto).toList();
    }


    @PostMapping
    @ApiOperation(value = "create a new user credit card")
    @ResponseStatus(HttpStatus.CREATED)
    public    CreditCardDto saveCreditCard(@RequestBody @Valid CreditCardEditDto creditCardDto, Principal principal) {
       return Optional.of(creditCardDto)
                .map(creditCardMapper::toEntity)
                .map(card->creditCardService.saveCard(card,principal.getName()))
                .map(creditCardMapper::toDto).orElseThrow();

    }
    @PutMapping("/{id}")
    @ApiOperation(value = "update a user credit card")
    public void updateCreditCard(@RequestBody @Valid CreditCardEditDto creditCardDto, @PathVariable Long id, Principal principal) {
          Optional.of(creditCardDto)
                .map(creditCardMapper::toEntity)
                .ifPresent(card->creditCardService.update(card,id,principal.getName()));
    }

    @PatchMapping("/active/{id}")
    @ApiOperation(value = "active the user credit card")
    public void activateCreditCard(@PathVariable long id) {
         creditCardService.activateCreditCard(id);
    }
}
