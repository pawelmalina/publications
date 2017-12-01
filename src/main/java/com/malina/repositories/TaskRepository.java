package com.malina.repositories;

import com.malina.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 24.11.17.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
