package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.AppUser;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.QueryHint;
import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface UserRepo extends CrudRepository<AppUser, Long> {


    boolean existsByEmail(String email);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    AppUser findByUserName(String userName);


    Optional<AppUser> findByEmail(String email);

}
