package com.malina.bootstrap;

import com.malina.model.Document;
import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.repositories.*;
import com.malina.services.ProjectServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by pawel on 23.11.17.
 */
@Component
@AllArgsConstructor
public class DevDataInserter implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final DocumentRepository documentRepository;
    private final MessageRepository messageRepository;
    private final FileRepository fileRepository;

    private final UserDataInserter userData;
    private final ProjectDataInserter projectData;
    private final DocumentDataInserter documentData;

    private final ProjectServiceImpl projectService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /* The order is important */
        userData.insertData();
        projectData.insertData();
        addUsersToProjects();

        documentData.insertData(projectData.getProjects());
    }

    private void addUsersToProjects() {
        Project project1 = projectData.getProjects().get(0);
        Project project2 = projectData.getProjects().get(1);
        Project project3 = projectData.getProjects().get(2);
        User user1 = userData.getUsers().get(0);
        User user2 = userData.getUsers().get(1);
        User user3 = userData.getUsers().get(2);
        User user4 = userData.getUsers().get(3);
        User user5 = userData.getUsers().get(4);
        User user6 = userData.getUsers().get(5);
        User user7 = userData.getUsers().get(6);
        User user8 = userData.getUsers().get(7);
        User user9 = userData.getUsers().get(8);

        project1.setProjectManager(user1);
        project2.setProjectManager(user2);
        project3.setProjectManager(user3);

        projectService.addUserToProject(project1, user1);
        projectService.addUserToProject(project2, user2);
        projectService.addUserToProject(project3, user3);

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        Message message1 = new Message(user1, new Date(1513282620000l), "Ten projekt jest potrzebny.");
        Message message2 = new Message(user2, new Date(1513283620000l), "Sugeruję  zmianę nazwy tego projektu.");
        Message message3 = new Message(user1, new Date(1513284620000l), "To może coś zaproponujesz?");
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        projectService.addMessageToProject(project1, message1);
        projectService.addMessageToProject(project1, message2);
        projectService.addMessageToProject(project1, message3);

        projectService.addUserToProject(project1, user5);
        projectService.addUserToProject(project1, user6);
        projectService.addUserToProject(project1, user4);
        projectService.addUserToProject(project1, user1);
        projectService.addUserToProject(project1, user2);
        projectService.addUserToProject(project1, user3);

        projectService.addUserToProject(project2, user7);
        projectService.addUserToProject(project2, user8);
        projectService.addUserToProject(project2, user9);
        projectService.addUserToProject(project2, user2);
        projectService.addUserToProject(project2, user1);

        projectService.addUserToProject(project3, user6);
        projectService.addUserToProject(project3, user5);
        projectService.addUserToProject(project3, user8);
        projectService.addUserToProject(project3, user3);
        projectService.addUserToProject(project3, user1);
    }

}
