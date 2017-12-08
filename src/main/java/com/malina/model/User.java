package com.malina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by pawel on 23.11.17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends PersistentObject {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;

    @JsonIgnore
    private String password;

}
