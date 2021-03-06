package com.malina.services;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 19.12.17.
 */
@Service
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;

    public FileServiceImpl(FileRepository fileRepository, DocumentRepository documentRepository) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
    }


}
