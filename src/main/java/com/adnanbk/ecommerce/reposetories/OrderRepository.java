package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.UserOrder;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;


public interface OrderRepository extends CrudRepository<UserOrder, Long> {


    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<UserOrder> findByAppUser_Email(String email);


}
