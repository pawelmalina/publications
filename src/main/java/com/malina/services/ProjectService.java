package com.malina.services;

import com.malina.model.Document;
import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.MessageDTO;
import com.malina.model.dto.NameAndIdDTO;

import java.util.List;

/**
 * Created by pawel on 09.12.17.
 */
public interface ProjectService {

    Project getById(Long id);

    void addUserToProject(Project project, User user);

    void addMessageToProject(Project project, Message message);

    void addDocumentToProject(Project project, Document document);

    List<MessageDTO> getMessagesDTOFromProject(Project project);
}
