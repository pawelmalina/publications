package com.malina.bootstrap;

import com.malina.model.Project;
import com.malina.model.User;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.TaskRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

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

    private final UserDataInserter userData;
    private final ProjectDataInserter projectData;

    private final ProjectServiceImpl projectService;

//    meybe unnecessary
//    private List<User> users = new ArrayList<>();
//    private List<Project> projects = new ArrayList<>();
//    private List<Task> tasks = new ArrayList<>();
//    private List<Document> documents = new ArrayList<>();


//    public DevDataInserter(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository, DocumentRepository documentRepository, UserDataInserter userData) {
//        this.userRepository = userRepository;
//        this.projectRepository = projectRepository;
//        this.taskRepository = taskRepository;
//        this.documentRepository = documentRepository;
//        this.userData = userData;
//    }



    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        userData.insertData();
        projectData.insertData();

        addUsersToProjects();
    }

    private void addUsersToProjects() {
        Project project = projectData.getProjects().get(0);
        User user = userData.getUsers().get(0);
        User user2 = userData.getUsers().get(1);

//        projectService.addUserToProject(project, user);
//        projectService.addUserToProject(project, user2);
    }


}
