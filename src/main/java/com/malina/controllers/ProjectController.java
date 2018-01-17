package com.malina.controllers;

import com.malina.auth.SessionState;
import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.*;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectService;
import com.malina.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by pawel on 24.11.17.
 */
@Log4j
@RestController
@RequestMapping("/project")
@AllArgsConstructor
@CrossOrigin(origins = {"${origin.domain}","*"}, methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET})
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SessionState sessionState;

    // // TODO: 09.01.18 maybe to be removed
//    @RequestMapping("/all")
//    public List<Project> getProjects() {
//        return projectRepository.findAll();
//    }

    @RequestMapping("/all-names")
    public List<NameAndIdDTO> getProjectNames() {
        List<NameAndIdDTO> projectNameDTOs = projectRepository.getProjectsName();
        return projectNameDTOs;
    }

    @RequestMapping(value = "/where-user-is/{user_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<NameAndIdDTO> getProjectNamesForUser(@PathVariable("user_id") Long id) {
        List<Project> projects = projectRepository.getUsersNameFromProject(id);
        List<NameAndIdDTO> projectDTOs = projects.stream().map(project -> new NameAndIdDTO(project.getId(), project.getName())).collect(Collectors.toList());
        return projectDTOs;
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProjectDTO findOneProject(@PathVariable("id") Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project not found");
        }
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.convertToDTO(projectOptional.get());
        return projectDTO;
    }

//    @RequestMapping(path = "/add-user/{project_id}/{user_id}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.OK)
//    public void addUserToProject(@PathVariable("user_id") String userId, @PathVariable("project_id") String projectId) {
////        TODO catch exceptions here or by aspects
//        Project project = projectRepository.findById(Long.valueOf(projectId));
//        User user = userRepository.getOne(Long.valueOf(userId));
//        projectService.addUserToProject(project, user);
//    }

    @RequestMapping(value = "add-users-to-project/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus addUsersToProject(@PathVariable("project_id") Long projectId, @RequestBody Long[] usersIds) {
        Project project = projectService.getById(projectId);
        for (Long id: usersIds) {
            User user = userService.getById(id);
            projectService.addUserToProject(project, user);
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "remove-users-from-project/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus removeUsersFromProject(@PathVariable("project_id") Long projectId, @RequestBody Long usersId) {
        Project project = projectService.getById(projectId);
        User user = userService.getById(usersId);
        projectService.removeUserFromProjec(project, user);
        return HttpStatus.OK;
    }

    @RequestMapping(path = "{project_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeProject(@PathVariable("project_id") String projectId) {
        projectRepository.delete(Long.valueOf(projectId));
    }

    @RequestMapping(path = "all-messages/{project_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<MessageDTO> getMessagesFromProject(@PathVariable("project_id") Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project not found");
        }
        return projectService.getMessagesDTOFromProject(projectOptional.get());
    }

    @RequestMapping(path = "add-message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public HttpStatus addMessageToProject(@RequestBody NewMessageDTO newMessage) {
        Project project = projectService.getById(newMessage.getTargetId());
        User user = sessionState.getCurrentUser();

        Message message = new Message(user, new Date(), newMessage.getContent());
        projectService.addMessageToProject(project, message);
        return HttpStatus.OK;
    }


    @RequestMapping(path = "add", method = RequestMethod.POST)
    public boolean addProject(@RequestBody UpdateObjectDTO dto) {
        Project project = new Project();
        User currentUser = sessionState.getCurrentUser();
        project.setProjectManager(currentUser);
        project.setName(dto.getTitle());
        project.setDescription(dto.getDescription());
        projectService.addUserToProject(project, currentUser);
        projectRepository.save(project);
        return true;
    }

    @RequestMapping(value = "/update-project", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public HttpStatus updateProject(@RequestBody UpdateObjectDTO updateObject) {
        Project project = projectService.getById(updateObject.getId());
        project.setName(updateObject.getTitle());
        project.setDescription(updateObject.getDescription());
        projectRepository.save(project);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/remove/{project_id}", method = RequestMethod.POST)
    public boolean remove(@PathVariable("project_id") Long projectId) {
        Project project = projectService.getById(projectId);
        if(project.getProjectManager().equals(sessionState.getCurrentUser())){
            projectRepository.delete(projectId);
            return false;
        }
        return false;
    }

}
