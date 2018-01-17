package com.malina.model.dto;

import com.malina.model.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by pawel on 15.01.18.
 */
@Getter
@Setter
public class DocumentShortDTO {

    private Long id;
    private String title;
    private NameAndIdDTO project;

    public DocumentShortDTO convertToDTO(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.project = new NameAndIdDTO(document.getProject().getId(),document.getProject().getName());

        return this;
    }
}
