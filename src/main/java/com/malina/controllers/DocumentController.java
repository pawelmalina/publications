package com.malina.controllers;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.model.dto.DocumentDTO;
import com.malina.model.dto.ProjectDTO;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.FileService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by pawel on 24.11.17.
 */
@RestController
@Log4j
@RequestMapping("document")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public DocumentController(DocumentRepository documentRepository, FileService fileService, FileRepository fileRepository) {
        this.documentRepository = documentRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DocumentDTO findOneDocument(@PathVariable("id") Long id) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (!documentOptional.isPresent()) {
            throw new RuntimeException("Document not found");
        }

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.convertToDTO(documentOptional.get());
        return documentDTO;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request,
                                   @RequestParam MultipartFile fileUpload, @RequestParam Long documentId) throws Exception {

        Optional<Document> documentOptional = documentRepository.findById(documentId);
        if (!documentOptional.isPresent()) {
            throw new RuntimeException("Document not found");
        }

        Optional<User> userOptional = userRepository.findById(1l);
        if(!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        UploadedFile uploadedFile = new UploadedFile(fileUpload.getOriginalFilename(), new Date(),
                userOptional.get(), fileUpload.getBytes());

        fileRepository.save(uploadedFile);
        fileService.addUploadedFileToDocument(documentOptional.get(), uploadedFile);

        log.debug("Uploaded file: " + fileUpload.getOriginalFilename());
        return "Success";
    }


    @RequestMapping(value = "/download/{file_id}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> downloadDocument(@PathVariable("file_id") Long fileId) throws IOException {
        UploadedFile file = fileRepository.findById(fileId);
        byte[] document = file.getData();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set("Content-Disposition", "inline; filename=" + file.getFileName());
        header.setContentLength(document.length);
        return new HttpEntity<byte[]>(document, header);
    }
}
