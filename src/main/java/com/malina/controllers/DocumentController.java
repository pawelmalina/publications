package com.malina.controllers;

import com.fasterxml.jackson.databind.node.NumericNode;
import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.model.dto.DocumentDTO;
import com.malina.model.dto.ProjectDTO;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.DocumentService;
import com.malina.services.FileService;
import com.malina.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    private final FileService fileService;
    private final FileRepository fileRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DocumentDTO findOneDocument(@PathVariable("id") Long documentId) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.convertToDTO(documentService.getById(documentId));
        return documentDTO;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HttpStatus uploadFile(HttpServletRequest request,
                                 @RequestParam MultipartFile fileUpload, @RequestParam Long documentId) throws Exception {

        Document document = documentService.getById(documentId);
        User user = userService.getById(1l); // TODO: 09.01.18 Change magic number to user id from session

        UploadedFile uploadedFile = new UploadedFile(fileUpload.getOriginalFilename(), new Date(),
                user, fileUpload.getBytes());

        fileRepository.save(uploadedFile);
        documentService.addUploadedFileToDocument(document, uploadedFile);

        log.debug("Uploaded file: " + fileUpload.getOriginalFilename());
        return HttpStatus.OK;
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

    @RequestMapping(value = "lock", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus lock(@RequestParam Long documentId, @RequestParam Long toDate) {
        User user = userService.getById(1l); //// TODO: 11.01.18 change to user in session
        Document document = documentService.getById(documentId);
        Date date = new Date(toDate);
        documentService.lockDocument(document, date, user);
            return HttpStatus.OK;
    }

    @RequestMapping(value = "unlock", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus unlock(@RequestParam Long documentId) {
        Document document = documentService.getById(documentId);
        User user = userService.getById(1l); //// TODO: 11.01.18 change to user in session
        documentService.unlockDocument(document, user);
        return HttpStatus.OK;
    }

}
