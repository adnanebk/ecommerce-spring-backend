package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.CreditCardDto;
import com.adnanbk.ecommerce.mappers.CreditCardMapper;
import com.adnanbk.ecommerce.services.CreditCardService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
    public    CreditCardDto saveCreditCard(@RequestBody @Valid CreditCardDto creditCardDto, Principal principal) {
       return Optional.of(creditCardDto)
                .map(creditCardMapper::toEntity)
                .map(card->creditCardService.saveCard(card,principal.getName()))
                .map(creditCardMapper::toDto).orElseThrow();

    }
    @PutMapping("/{id}")
    @ApiOperation(value = "update a user credit card")
    public CreditCardDto updateCreditCard(@RequestBody @Valid CreditCardDto creditCardDto,@PathVariable Long id,Principal principal) {
        return Optional.of(creditCardDto)
                .map(creditCardMapper::toEntity)
                .map(card->creditCardService.update(card,id,principal.getName()))
                .map(creditCardMapper::toDto).orElseThrow();
    }

    @PatchMapping("/active/{id}")
    @ApiOperation(value = "make the user credit card primary")
    public void activateCreditCard(@PathVariable long id) {
         creditCardService.activateCreditCard(id);
    }
}
