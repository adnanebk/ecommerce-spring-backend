package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.services.CreditCardService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/creditCards")
@AllArgsConstructor
public class CreditCardController {


    private CreditCardService creditCardService;

    @GetMapping
    @ApiOperation(value = "get all credit cards by the email of the authenticated a user")
    public List<CreditCard> getUserCreditCards(Principal principal) {
        return creditCardService.getByEmail(principal.getName());
    }


    @PostMapping
    @ApiOperation(value = "create a new user credit card")
    public ResponseEntity<CreditCard> saveCreditCard(@RequestBody @Valid CreditCard creditCard, Principal principal) {
        CreditCard savedCreditCard = creditCardService.saveCard(creditCard, principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCreditCard.getId()).toUri();
        return ResponseEntity.created(location).body(savedCreditCard);
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "update a user credit card")
    public CreditCard updateCreditCard(@RequestBody @Valid CreditCard creditCard,@PathVariable Long id,Principal principal) {
        return creditCardService.update(creditCard,id,principal.getName());
    }

    @PatchMapping("/active/{id}")
    @ApiOperation(value = "make the user credit card primary")
    public void activateCreditCard(@PathVariable long id) {
         creditCardService.activateCreditCard(id);
    }
}
