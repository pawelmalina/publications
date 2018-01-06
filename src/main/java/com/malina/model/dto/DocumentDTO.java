package com.malina.model.dto;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 06.01.18.
 */
@Getter
@Setter
@NoArgsConstructor
public class DocumentDTO {

    private String title;
    private String description;
    private boolean blocked = false;
    private Long creationDate;
    private Long blockedFromDate;
    private Long blockedToDate;

    private String createdBy;
    private String blockedBy;

//    @OneToOne
//    private UploadedFile currentVersion;

//    @Singular
//    @OneToMany
//    private List<UploadedFile> historicalFiles = new ArrayList<>();

//    @OneToOne
//    private User blockedByUser;

//    @Transient
//    public void lockFile(Date blockedToDate) {
//        this.blocked = true;
//        this.blockedFromDate = new Date();
//        this.blockedToDate = blockedToDate;
//    }

    public DocumentDTO convertToDTO(Document document){
        this.title = document.getTitle();
        this.description = document.getDescription();
        this.blocked  = document.isBlocked();
        this.creationDate = document.getCreationDate().getTime();
        this.createdBy = document.getCreatedBy().getFullName();

        if(this.blocked){
            this.blockedFromDate = document.getBlockedFromDate().getTime();
            this.blockedToDate = document.getBlockedToDate().getTime();
            this.blockedBy = document.getBlockedByUser().getFullName();
        }
        return this;
    }

}
