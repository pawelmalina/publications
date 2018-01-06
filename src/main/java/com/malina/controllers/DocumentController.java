package com.malina.controllers;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.model.dto.DocumentDTO;
import com.malina.model.dto.ProjectDTO;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private final FileRepository fileRepository;

    @Autowired
    public DocumentController(DocumentRepository documentRepository, FileRepository fileRepository) {
        this.documentRepository = documentRepository;
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

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request,
                                   @RequestParam CommonsMultipartFile fileUpload) throws Exception {

        log.debug("Uploaded file: " + fileUpload.getOriginalFilename());

//        if (fileUpload != null && fileUpload.length > 0) {
//
//        }

        return "Success";
    }


    @RequestMapping(value = "/download/{file_id}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public
    @ResponseBody
    HttpEntity<byte[]> downloadDocument(@PathVariable("file_id") Long fileId) throws IOException {
        UploadedFile file = fileRepository.findById(fileId);
        byte[] document = file.getData();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set("Content-Disposition", "inline; filename=" + file.getFileName());
        header.setContentLength(document.length);
        return new HttpEntity<byte[]>(document, header);
    }
}
