package com.malina.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by pawel on 17.12.17.
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile extends PersistentObject{

    private String fileName;
    private Date uploadedDate;

    @OneToOne
    private User uploadedBy;

    @Lob
    private byte[] data;

    public UploadedFile(String fileName, User uploadedBy, byte[] data) {
        this.fileName = fileName;
        this.uploadedBy = uploadedBy;
        this.data = data;
        this.uploadedDate = new Date();
    }
}
