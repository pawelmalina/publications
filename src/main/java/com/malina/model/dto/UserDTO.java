package com.malina.model.dto;

import com.malina.auth.Role;
import com.malina.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Created by pawel on 13.01.18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Role role;

    public UserDTO convertToDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.role = user.getRole();

        return this;
    }
}
