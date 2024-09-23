package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepo extends CrudRepository<AppUser, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<AppUser> findByEmail(String email);

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
    @Query("UPDATE AppUser us SET us.enabled = :enabled WHERE us.email= :email")
    void enableUser(String email, boolean enabled);

}
