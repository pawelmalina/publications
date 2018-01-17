package com.malina.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Created by pawel on 17.01.18.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publication extends PersistentObject{

    private String title;
    @Lob
    private String description;

    @OneToOne
    private Project project;

    @OneToOne
    private UploadedFile file;


}
