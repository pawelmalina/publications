package com.malina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by pawel on 14.12.17.
 */
@Getter
@Setter
@AllArgsConstructor
public class NameAndIdDTO {
    private Long id;
    private String name;
}
