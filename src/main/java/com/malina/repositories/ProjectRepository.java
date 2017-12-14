package com.malina.repositories;

import com.malina.model.Project;
import com.malina.model.dto.NameAndIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 24.11.17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findById(Long id);

    @Query("SELECT new com.malina.model.dto.NameAndIdDTO(u.id, u.name) FROM Project u")
    List<NameAndIdDTO> getProjectsName();


    @Query("SELECT new com.malina.model.dto.NameAndIdDTO(u.id, u.name) FROM Project u")
    List<NameAndIdDTO> getUsersNameFromProject();

    @Query("SELECT new com.malina.model.dto.MessageDTO(u.id, u.) FROM Project u ORDER BY date DESC")
    List<NameAndIdDTO> getProjectMessages();
}
