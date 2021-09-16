package com.adnanbk.ecommerceang.reposetories;

import com.adnanbk.ecommerceang.models.AppUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.QueryHint;
import java.util.Optional;

@ApiImplicitParams(@ApiImplicitParam(name = "authorization",
        value = "Bearer jwt-token",paramType = "header"))
public interface UserRepo extends CrudRepository<AppUser,Long> {

    boolean existsByUserName(String userName);
     boolean existsByEmail(String email);


    @RestResource(path="byUserName")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    AppUser findByUserName(String userName);

    @Override
    <S extends AppUser> S save(S s);

    Optional<AppUser> findByEmail(String email);
}
