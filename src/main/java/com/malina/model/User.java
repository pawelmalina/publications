package com.malina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pawel on 23.11.17.
 */
@Entity
@EqualsAndHashCode(exclude = {"projects"})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends PersistentObject {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

    @JsonIgnore
    private String password;

    @Transient
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
