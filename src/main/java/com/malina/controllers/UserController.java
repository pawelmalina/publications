package com.malina.controllers;

import com.malina.auth.SessionState;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.NameAndIdDTO;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pawel on 23.11.17.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"${origin.domain}","*"})
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SessionState sessionState;


    @RequestMapping(value = "/users-not-assigned-to-project/{project_id}", method = RequestMethod.GET)
    public List<NameAndIdDTO> getUsersNotAssignedToProject(@PathVariable("project_id") Long projectId){
        List<NameAndIdDTO> nameAndIdDTOs = userRepository.getNotAssignedUserInProject(projectId)
                .stream().map(user -> new NameAndIdDTO(user.getId(),
                        user.getFullName())).collect(Collectors.toList());
        return nameAndIdDTOs;
    }

    @RequestMapping(value = "/user-is-manager-of-that-project/{project_id}", method = RequestMethod.GET)
    public boolean checkUserIsManagerOfThatProject(@PathVariable("project_id") Long projectId){
        if(!sessionState.isAuthenticated()){
            return false;
        }
        Project project = projectService.getById(projectId);
        User projectManager = project.getProjectManager();
        return sessionState.getCurrentUser().getId().equals(projectManager.getId());
    }

}
