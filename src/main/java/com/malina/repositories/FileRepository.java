package com.malina.repositories;

import com.malina.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 17.12.17.
 */
public interface FileRepository extends JpaRepository<UploadedFile, Long> {

    UploadedFile findById(Long id);
}
