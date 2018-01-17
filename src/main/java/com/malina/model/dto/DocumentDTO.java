package com.malina.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.malina.model.Document;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pawel on 06.01.18.
 */
@Getter
@Setter
@NoArgsConstructor
public class DocumentDTO {

    private Long id;
    private String title;
    private String description;
    private boolean blocked = false;
    private Long creationDate;
    private Long blockedFromDate;
    private Long blockedToDate;

    private String createdBy;
    private NameAndIdDTO blockedBy;

    private NameAndIdDTO project;


    private UploadedFileDTO currentVersion;
    private List<UploadedFileDTO> historicalFiles = new ArrayList<>();

    public DocumentDTO convertToDTO(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.description = document.getDescription();
        this.blocked = document.isBlocked();
        this.creationDate = document.getCreationDate().getTime();
        this.createdBy = document.getCreatedBy().getFullName();
        this.project = new NameAndIdDTO(document.getProject().getId(),document.getProject().getName());


        if(document.getCurrentVersion() != null) {
            this.currentVersion = new UploadedFileDTO().convertUploadedFileToDTO(document.getCurrentVersion());
        }
        List<UploadedFileDTO> uploadedFileDTOs = document.getHistoricalFiles().stream()
                .sorted((m1, m2) -> Long.compare(m2.getUploadedDate().getTime(), m1.getUploadedDate().getTime()))
                .map(file -> {
                    return new UploadedFileDTO().convertUploadedFileToDTO(file);
                }).collect(Collectors.toList());

        this.historicalFiles = uploadedFileDTOs;

        if (this.blocked) {

            if(document.getBlockedToDate().getTime() < new Date().getTime()){
                this.blocked = false;
                return this;
            }

            this.blockedFromDate = document.getBlockedFromDate().getTime();
            this.blockedToDate = document.getBlockedToDate().getTime();
            this.blockedBy = new NameAndIdDTO(document.getBlockedByUser().getId(), document.getBlockedByUser().getFullName());
        }
        return this;
    }

}
