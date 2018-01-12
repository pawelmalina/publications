package com.malina.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel on 24.11.17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document extends PersistentObject{

    private String title;
    @Lob
    private String description;
    private boolean blocked = false;
    private Date creationDate;
    private Date blockedFromDate;
    private Date blockedToDate;

    @OneToOne
    private User createdBy;

    @OneToOne
    private UploadedFile currentVersion;

    @Singular
    @OneToMany
    private List<UploadedFile> historicalFiles = new ArrayList<>();

    @OneToOne
    private User blockedByUser;

    @Transient
    public void lock(Date blockedToDate, User user) {
        this.blocked = true;
        this.blockedFromDate = new Date();
        this.blockedToDate = blockedToDate;
        this.blockedByUser = user;
    }
}
