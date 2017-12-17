package com.malina.repositories;

import com.malina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by pawel on 23.11.17.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findById(Long id);
}
