package com.malina.services;

import com.malina.model.Document;
import com.malina.model.Message;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.model.dto.MessageDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by pawel on 09.01.18.
 */
public interface DocumentService {

    Document getById(Long id);

    void addUploadedFileToDocument(Document document, UploadedFile uploadedFile);

    void lockDocument(Document document, Date toDate, User user);

    void unlockDocument(Document document, User user);

    void addMessageToDocument(Document document, Message message);

    List<MessageDTO> getMessagesDTOFromDocument(Document document);
}
