package com.malina.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 24.11.17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends PersistentObject {

    private String title;
    private String name;

    @Lob
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    private List<User> users = new ArrayList<>();

    @OneToMany
    @Singular
    private List<Task> tasks = new ArrayList<>();

    @OneToMany
    @Singular
    private List<Document> documents = new ArrayList<>();
}
