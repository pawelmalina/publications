package com.malina.repositories;

import com.malina.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 17.01.18.
 */

public interface PublicationRepository extends JpaRepository<Publication, Long>{
}
