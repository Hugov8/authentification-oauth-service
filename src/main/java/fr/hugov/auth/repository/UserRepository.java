package fr.hugov.auth.repository;

import fr.hugov.auth.model.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    
}
