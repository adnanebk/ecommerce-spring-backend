package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

}
