package com.malina.repositories;

import com.malina.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 24.11.17.
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
