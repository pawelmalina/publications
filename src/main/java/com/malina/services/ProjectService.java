package com.malina.services;

import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.MessageDTO;

import java.util.List;

/**
 * Created by pawel on 09.12.17.
 */
public interface ProjectService {

    void addUserToProject(Project project, User user);

    void addMessageToProject(Project project, Message message);

    List<MessageDTO> getMessagesDTOFromProject(Project project);
}
