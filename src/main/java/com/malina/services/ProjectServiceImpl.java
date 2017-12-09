package com.malina.services;

import com.malina.model.Project;
import com.malina.model.User;
import com.malina.repositories.ProjectRepository;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 09.12.17.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void addUserToProject(Project project, User user) {
//        TODO replace by Query
        List<User> users = new ArrayList<>();
        project.getUsers().iterator().forEachRemaining(users::add);
        users.add(user);
        project.setUsers(users);
        projectRepository.save(project);
    }
}
