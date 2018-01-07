package com.malina.services;

import com.malina.model.Document;
import com.malina.model.UploadedFile;

/**
 * Created by pawel on 19.12.17.
 */
public interface FileService {

    void addUploadedFileToDocument(Document document, UploadedFile uploadedFile);
}
