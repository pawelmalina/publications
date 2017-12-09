package com.malina.controllers;

import com.malina.model.Project;
import com.malina.model.User;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by pawel on 24.11.17.
 */
@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @RequestMapping(path = "/one/{id}", method = RequestMethod.GET)
    public Project findProject(@PathVariable("id") Long id){
        Project project = projectRepository.getOne(id);
        return project;
    }

    @RequestMapping(path = "{project_id}/add-user/{user_id}", method = RequestMethod.POST)
    @ResponseBody
    public boolean addUserToProject(@PathVariable("user_id") String userId, @PathVariable("project_id") String projectId){
        Project project = projectRepository.findById(Long.valueOf(projectId));
        User user = userRepository.getOne(Long.valueOf(userId));

        projectService.addUserToProject(project, user);
        return true;
    }
}
