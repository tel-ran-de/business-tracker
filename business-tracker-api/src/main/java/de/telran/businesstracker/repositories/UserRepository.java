package de.telran.businesstracker.repositories;


import de.telran.businesstracker.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(Long userId);
}
