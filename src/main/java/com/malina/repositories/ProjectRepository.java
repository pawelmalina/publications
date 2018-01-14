package com.malina.repositories;

import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.dto.MessageDTO;
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

//    @Query(value = "SELECT new com.malina.model.dto.NameAndIdDTO(u.id, u.name) FROM Project u where in (SELECT PROJECT_ID FROM PROJECT_USER where user_id=5)" , nativeQuery = true)
    @Query(value = "SELECT * FROM PROJECT WHERE ID in (" +
            "SELECT PROJECT_ID FROM PROJECT_USER where user_id=?1" +
            ")" , nativeQuery = true)
    List<Project> getUsersNameFromProject(Long id);

//    @Query("SELECT new com.malina.model.dto.NameAndIdDTO(u.id, u.name) FROM Project u")
//    List<NameAndIdDTO> getDocumentNameFromProject();

}
