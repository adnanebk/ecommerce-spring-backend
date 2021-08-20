package com.adnanbk.ecommerceang.Controllers;

import com.adnanbk.ecommerceang.models.CreditCard;
import com.adnanbk.ecommerceang.services.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardController {


    private CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public ResponseEntity<CreditCard> saveCreditCard(@RequestBody @Valid CreditCard creditCard, Principal principal){
        CreditCard savedCreditCard =creditCardService.saveCard(creditCard,principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCreditCard.getId()).toUri();
        return ResponseEntity.created(location).body(savedCreditCard);
    }
    @PatchMapping("/active")
    public ResponseEntity<Iterable<CreditCard>> activatedCreditCard(@RequestBody CreditCard creditCard, Principal principal){
        if(principal==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"you have no access");
        var cards =creditCardService.activatedCreditCard(creditCard);
        return ResponseEntity.ok().body(cards);
    }
}
