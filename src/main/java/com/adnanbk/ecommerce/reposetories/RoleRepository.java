package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.QueryHint;

@RepositoryRestResource(exported = false)
public interface RoleRepository  extends JpaRepository<Role, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Role findByName(String name);
}
