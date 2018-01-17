package com.malina.model.dto;

import com.malina.model.Publication;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pawel on 17.01.18.
 */
@Getter
@Setter
@NoArgsConstructor
public class PublicationDTO {

    private Long id;
    private String title;
    private String description;

    private NameAndIdDTO project;
    private NameAndIdDTO file;

    public PublicationDTO convertPublicationToDTO(Publication publication){
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.description = publication.getDescription();
//        this.project = new NameAndIdDTO(publication.getProject().getId(), publication.getProject().getName());
        this.file = new NameAndIdDTO(publication.getFile().getId(), publication.getFile().getFileName());
        return this;
    }

}
