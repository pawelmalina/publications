package com.malina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pawel on 16.12.17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewMessageDTO {

    private String content;
    private Long targetId;
    private Long userId;

}
