package com.malina.repositories;

import com.malina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 23.11.17.
 */
public interface UserRepository extends JpaRepository<User, Long>{
}
