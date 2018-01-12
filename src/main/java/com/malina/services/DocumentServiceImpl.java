package com.malina.services;

import com.malina.model.Document;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 09.01.18.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

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
}
