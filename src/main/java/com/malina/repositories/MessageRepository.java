package com.malina.repositories;

import com.malina.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pawel on 14.12.17.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {}
