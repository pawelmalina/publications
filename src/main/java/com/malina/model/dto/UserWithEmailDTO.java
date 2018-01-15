package com.malina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pawel on 15.01.18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithEmailDTO {

    Long id;
    String name;
    String mail;
}
