package com.malina.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.malina.model.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pawel on 14.12.17.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String title;
    private String name;
    private String description;

    private NameAndIdDTO projectMenager;

    private Set<UserWithEmailDTO> users = new HashSet<>();
    private Set<Long> tasks = new HashSet<>();
    private Set<NameAndIdDTO> documents = new HashSet<>();
    private List<MessageDTO> messages = new ArrayList<>();

    public ProjectDTO convertToDTO(Project project) {
        this.setId(project.getId());

        this.setName(project.getName());
        this.setTitle(project.getTitle());
        this.setDescription(project.getDescription());
        this.setProjectMenager(new NameAndIdDTO(project.getProjectManager().getId(), project.getProjectManager().getFullName()));
        this.setDocuments(project.getDocuments().stream().map(document -> new NameAndIdDTO(document.getId(), document.getTitle())).collect(Collectors.toSet()));
        this.setUsers(project.getUsers().stream().map(user -> {
            return new UserWithEmailDTO(user.getId(), user.getFullName(), user.getEmail());
        }).collect(Collectors.toSet()));
        this.setMessages(project.getMessages().stream().map(message -> {
           return  new MessageDTO(message.getId(), message.getAuthor().getFullName(), message.getContent(), message.getDate().getTime());
        }).collect(Collectors.toList()));
        return this;
    }
}
