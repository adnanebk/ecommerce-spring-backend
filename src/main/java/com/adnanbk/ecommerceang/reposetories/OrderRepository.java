package com.adnanbk.ecommerceang.reposetories;

import com.adnanbk.ecommerceang.dto.OrderProjection;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.models.UserOrder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource(excerptProjection = OrderProjection.class)
@ApiImplicitParams(@ApiImplicitParam(name = "authorization",
        value = "Bearer jwt-token", paramType = "header"))
public interface OrderRepository extends CrudRepository<UserOrder, Integer> {

    @Override
    @RolesAllowed("ROLE_ADMIN")
    Iterable<UserOrder> findAll();


    @RestResource(path = "byUserName")
    @Cacheable(value = "orderCache", key = "#userName")
    List<UserOrder> findByAppUser_UserName(String userName);

    @Override
    @CacheEvict(value = "orderCache", key = "#s.appUser.userName")
    <S extends UserOrder> S save(S s);
}
