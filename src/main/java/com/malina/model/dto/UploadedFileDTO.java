package com.malina.model.dto;

import com.malina.model.UploadedFile;
import com.malina.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by pawel on 10.01.18.
 */
@Getter
@Setter
public class UploadedFileDTO {

    private Long id;
    private Long uploadedDate;
    private NameAndIdDTO uploadedBy;

    public UploadedFileDTO convertUploadedFileToDTO(UploadedFile file){
        this.id = file.getId();
        this.uploadedDate = file.getUploadedDate().getTime();
        User user = file.getUploadedBy();
        this.uploadedBy = new NameAndIdDTO(user.getId(),user.getFullName());
        return this;
    }


}
