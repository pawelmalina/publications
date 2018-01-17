package com.malina.services;

import com.malina.model.*;
import com.malina.model.dto.MessageDTO;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by pawel on 09.01.18.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Document getById(Long id) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (!documentOptional.isPresent()) {
            throw new RuntimeException("Document not found");
        }

        return documentOptional.get();
    }

    @Override
    public void addUploadedFileToDocument(Document document, UploadedFile uploadedFile) {
        List<UploadedFile> historicalFiles = new ArrayList<>();
        document.getHistoricalFiles().iterator().forEachRemaining(historicalFiles::add);
        if(document.getCurrentVersion() != null) {
            historicalFiles.add(document.getCurrentVersion());
        }
        document.setCurrentVersion(uploadedFile);
        document.setHistoricalFiles(historicalFiles);
        documentRepository.save(document);
    }

    @Override
    public void lockDocument(Document document, Date toDate, User user) {
        document.lock(toDate,user);
        documentRepository.save(document);
    }

    @Override
    public void unlockDocument(Document document, User user) {
        document.setBlocked(false);
        documentRepository.save(document);
    }

    @Override
    public List<MessageDTO> getMessagesDTOFromDocument(Document document) {
        return document.getMessages().stream().sorted((m1, m2) ->
                Long.compare(m2.getDate().getTime(), m1.getDate().getTime())
        ).map((message) -> {
            return new MessageDTO(message.getId(), message.getAuthor().getFullName(),
                    message.getContent(), message.getDate().getTime());
        }).collect(Collectors.toList());
    }

    @Override
    public void addMessageToDocument(Document document, Message message) {
        List<Message> messages = new ArrayList<>();
        document.getMessages().iterator().forEachRemaining(messages::add);
        messages.add(message);
        document.setMessages(messages);
        messageRepository.save(message);
        documentRepository.save(document);
    }


}
