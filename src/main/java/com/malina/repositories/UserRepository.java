package com.malina.repositories;

import com.malina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 23.11.17.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findById(Long id);

    boolean existsByEmailAndPassword(String email, String password);

    User getByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM USER WHERE ID not in (" +
            "SELECT USER_ID FROM PROJECT_USER where  PROJECT_ID = ?1)", nativeQuery = true)
    List<User> getNotAssignedUserInProject(Long projectId);


}




//        SELECT * FROM DOCUMENT Where ID in (SELECT DOCUMENTS_ID FROM PROJECT_DOCUMENTS WHERE PROJECT_ID in (
//        SELECT PROJECT_ID FROM PROJECT_USER where  USER_ID = 6))