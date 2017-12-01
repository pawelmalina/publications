package com.malina.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

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
    private String description;
    private boolean blocked = false;
    private Date creationDate;
    private Date lastModificationDate;
    private Date blockedFromDate;
    private Date blockedToDate;

    @OneToOne
    private User blockedByUser;
}
