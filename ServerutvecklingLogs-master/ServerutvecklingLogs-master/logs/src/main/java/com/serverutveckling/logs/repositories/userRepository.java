package com.serverutveckling.logs.repositories;


import com.serverutveckling.logs.objects.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * A repository for fetching users from a database
 */
public interface userRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUUID(String UUID);
    Optional<Users> findByUsername(String username);
}
