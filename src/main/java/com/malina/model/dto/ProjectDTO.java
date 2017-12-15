package com.malina.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.malina.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

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


    private Set<NameAndIdDTO> users = new HashSet<>();
    private Set<Long> tasks = new HashSet<>();
    private Set<Long> documents = new HashSet<>();
    private List<MessageDTO> messages = new ArrayList<>();

    public ProjectDTO convertToDTO(Project project) {

        this.setId(project.getId());

        this.setName(project.getName());
        this.setTitle(project.getTitle());
        this.setDescription(project.getDescription());
        this.setDocuments(project.getDocuments().stream().map(PersistentObject::getId).collect(Collectors.toSet()));
        this.setTasks(project.getTasks().stream().map(PersistentObject::getId).collect(Collectors.toSet()));
        this.setUsers(project.getUsers().stream().map(user -> {
            return new NameAndIdDTO(user.getId(), user.getFirstName() + " " + user.getLastName());
        }).collect(Collectors.toSet()));
        this.setMessages(project.getMessages().stream().map(message -> {
           return  new MessageDTO(message.getId(), message.getAuthor().getFullName(), message.getContent(), message.getDate().getTime());
        }).collect(Collectors.toList()));
        return this;
    }
}