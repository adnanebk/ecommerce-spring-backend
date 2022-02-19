package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
}
