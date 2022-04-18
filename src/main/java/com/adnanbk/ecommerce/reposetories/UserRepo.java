package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;


@RepositoryRestResource(path = "users")
public interface UserRepo extends CrudRepository<AppUser, Long> {



    boolean existsByEmail(String email);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @RestResource(path = "email")
    AppUser findByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser us SET us.password = :password WHERE us.id= :id")
    void updatePassword(Long id, String password);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser us SET us.imageUrl = :fileName WHERE us.email= :email")
    void updateImage(String email, String fileName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser us SET us.enabled = :enabled WHERE us.id= :id")
    void enableUser(Long id, boolean enabled);


    // Optional<AppUser> findByEmail(String email);

}
