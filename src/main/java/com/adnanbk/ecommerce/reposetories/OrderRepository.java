package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.dto.OrderProjection;
import com.adnanbk.ecommerce.models.UserOrder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.annotation.security.RolesAllowed;
import java.util.List;


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
