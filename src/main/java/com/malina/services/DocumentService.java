package com.malina.services;

import com.malina.model.Document;
import com.malina.model.UploadedFile;
import com.malina.model.User;

import java.util.Date;

/**
 * Created by pawel on 09.01.18.
 */
public interface DocumentService {

    Document getById(Long id);

    void addUploadedFileToDocument(Document document, UploadedFile uploadedFile);

    void lockDocument(Document document, Date toDate, User user);

    void unlockDocument(Document document, User user);
}
