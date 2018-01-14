package com.malina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by pawel on 12.01.18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginUserDTO {

    private String email;
    private String password;

}
