package com.malina.controllers;

import com.malina.model.Project;
import com.malina.model.dto.ProjectDTO;
import com.malina.model.dto.NameAndIdDTO;
import com.malina.repositories.ProjectRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 24.11.17.
 */
@RestController
@RequestMapping("/project")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @RequestMapping("/all")
    public List<Project> getProjects(){
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
        if(!projectOptional.isPresent()){
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
    public void removeProject(@PathVariable("project_id") String projectId){
        projectRepository.delete(Long.valueOf(projectId));
    }
}
