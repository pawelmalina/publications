package com.malina.controllers;

import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.model.User;
import com.malina.model.dto.MessageDTO;
import com.malina.model.dto.NewMessageDTO;
import com.malina.model.dto.ProjectDTO;
import com.malina.model.dto.NameAndIdDTO;
import com.malina.repositories.MessageRepository;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 24.11.17.
 */
@Log4j
@RestController
@RequestMapping("/project")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @RequestMapping("/all")
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @RequestMapping("/all-names")
    public List<NameAndIdDTO> getProjectNames() {
        List<NameAndIdDTO> projectNameDTOs = projectRepository.getProjectsName();
        return projectNameDTOs;
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
        Optional<Project> projectOptional = projectRepository.findById(newMessage.getTargetId());
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project not found");
        }

        Optional<User> userOptional = userRepository.findById(newMessage.getUserId());
        if (!projectOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Message message = new Message(userOptional.get(), new Date(), newMessage.getContent());

        projectService.addMessageToProject(projectOptional.get(), message);

        return HttpStatus.OK;
    }
}
