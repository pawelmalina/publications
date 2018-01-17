package com.malina.repositories;

import com.malina.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 24.11.17.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findById(Long id);

    @Query(value ="SELECT * FROM DOCUMENT Where PROJECT_ID in (SELECT PROJECT_ID FROM PROJECT_USER where  USER_ID = ?1)", nativeQuery = true)
    List<Document> getDocumentAssignedWithUser(Long userId);
}

//SELECT * FROM DOCUMENT Where ID in (SELECT DOCUMENTS_ID FROM PROJECT_DOCUMENTS WHERE PROJECT_ID in (SELECT PROJECT_ID FROM PROJECT_USER where  USER_ID = 1))
