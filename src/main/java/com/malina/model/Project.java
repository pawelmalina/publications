package com.malina.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pawel on 24.11.17.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends PersistentObject {

    private String title;
    private String name;

    @Lob
    private String description;

    @Singular
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<User> users = new HashSet<>();


    @OneToMany
    @Singular
    private Set<Task> tasks = new HashSet<>();

    @OneToMany
    @Singular
    private Set<Document> documents = new HashSet<>();

    @OneToMany
    @Singular
    private Set<Message> messages = new HashSet<>();
}
