package com.malina.repositories;

import com.malina.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 24.11.17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
