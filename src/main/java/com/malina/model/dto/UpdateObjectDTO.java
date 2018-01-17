package com.malina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pawel on 16.01.18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectDTO {
    private Long id;
    private String title;
    private String description;
}
