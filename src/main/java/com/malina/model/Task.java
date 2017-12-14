package com.malina.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pawel on 24.11.17.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task extends PersistentObject{

    private String title;
    private String description;
    private Date creationDate;
    private Date fromDate;
    private Date toDate;

    @OneToMany
    @Singular
    private Set<Document> documents = new HashSet<>();
}
