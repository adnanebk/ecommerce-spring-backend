package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.AppUser;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.Optional;



public interface UserRepo extends CrudRepository<AppUser, Long> {


    boolean existsByEmail(String email);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    AppUser findByUserName(String userName);

    @Override
    <S extends AppUser> S save(S s);

    Optional<AppUser> findByEmail(String email);

}
