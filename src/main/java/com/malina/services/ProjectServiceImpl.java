package com.malina.services;

import com.malina.model.Document;
import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.MessageDTO;
import com.malina.model.dto.NameAndIdDTO;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.MessageRepository;
import com.malina.repositories.ProjectRepository;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pawel on 09.12.17.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Project getById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project not found");
        }

        return projectOptional.get();
    }

    @Override
    public void addUserToProject(Project project, User user) {
        Set<User> users = new HashSet<>();
        project.getUsers().iterator().forEachRemaining(users::add);
        users.add(user);
        project.setUsers(users);
        projectRepository.save(project);
    }

    @Override
    public void addMessageToProject(Project project, Message message) {
        List<Message> messages = new ArrayList<>();
        project.getMessages().iterator().forEachRemaining(messages::add);
        messages.add(message);
        project.setMessages(messages);
        messageRepository.save(message);
        projectRepository.save(project);
    }

    @Override
    public void addDocumentToProject(Project project, Document document) {
        Set<Document> documents = new HashSet<>();
        project.getDocuments().iterator().forEachRemaining(documents::add);
        documents.add(document);
        project.setDocuments(documents);
        documentRepository.save(documents);
        projectRepository.save(project);
    }

    @Override
    public List<MessageDTO> getMessagesDTOFromProject(Project project) {
        return project.getMessages().stream().sorted((m1, m2) ->
                Long.compare(m2.getDate().getTime(), m1.getDate().getTime())
        ).map((message) -> {
            return new MessageDTO(message.getId(), message.getAuthor().getFullName(),
                    message.getContent(), message.getDate().getTime());
        }).collect(Collectors.toList());
    }
}
