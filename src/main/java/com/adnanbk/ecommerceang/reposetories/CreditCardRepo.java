package com.adnanbk.ecommerceang.reposetories;
import com.adnanbk.ecommerceang.models.CreditCard;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {

    Optional<CreditCard> findByCardNumber(String cardNumber);

    @RestResource(path="byUserName")
    @Cacheable(value="creditCardCache",key="#userName")
    List<CreditCard> findByAppUser_UserNameOrderByActiveDesc(String userName);

    @Override
    @CacheEvict(value = "creditCardCache",key="#s.appUser.userName")
    <S extends CreditCard> S save(S s);
}
