package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/creditCards")
@AllArgsConstructor
public class CreditCardController {


    private CreditCardService creditCardService;


    @PostMapping
    public ResponseEntity<CreditCard> saveCreditCard(@RequestBody @Valid CreditCard creditCard, Principal principal) {
        CreditCard savedCreditCard = creditCardService.saveCard(creditCard, principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCreditCard.getId()).toUri();
        return ResponseEntity.created(location).body(savedCreditCard);
    }

    @PatchMapping("/active")
    public ResponseEntity<Iterable<CreditCard>> activatedCreditCard(@RequestBody CreditCard creditCard) {
        var cards = creditCardService.activeCreditCard(creditCard);
        return ResponseEntity.ok().body(cards);
    }
}
