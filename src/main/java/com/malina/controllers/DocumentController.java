package com.malina.controllers;

import com.fasterxml.jackson.databind.node.NumericNode;
import com.malina.auth.SessionState;
import com.malina.model.*;
import com.malina.model.dto.*;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import com.malina.repositories.UserRepository;
import com.malina.services.DocumentService;
import com.malina.services.FileService;
import com.malina.services.ProjectService;
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
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by pawel on 24.11.17.
 */
@RestController
@Log4j
@RequestMapping("document")
@CrossOrigin(origins = {"${origin.domain}", "*"})
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    private final FileService fileService;
    private final FileRepository fileRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    private final ProjectService projectService;

    private final SessionState sessionState;

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public DocumentDTO findOneDocument(@PathVariable("userId") Long documentId) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.convertToDTO(documentService.getById(documentId));
        return documentDTO;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HttpStatus uploadFile(HttpServletRequest request,
                                 @RequestParam MultipartFile fileUpload, @RequestParam Long documentId) throws Exception {

        Document document = documentService.getById(documentId);
        User user = sessionState.getCurrentUser();

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
        User user = sessionState.getCurrentUser();
        Document document = documentService.getById(documentId);
        Date date = new Date(toDate);
        documentService.lockDocument(document, date, user);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "unlock", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus unlock(@RequestParam Long documentId) {
        Document document = documentService.getById(documentId);
        User user = sessionState.getCurrentUser();
        documentService.unlockDocument(document, user);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/assigned-with-user/{user_id}")
    @ResponseBody
    public List<DocumentShortDTO> getDocumentAssignedWithUser(@PathVariable("user_id") Long userId) {
        List<Document> documents = documentRepository.getDocumentAssignedWithUser(userId);
        List<DocumentShortDTO> collect = documents.stream().map(document -> new DocumentShortDTO().convertToDTO(document)).collect(Collectors.toList());
        return collect;
    }

    @RequestMapping(value = "/add-document/{project_id}", method = RequestMethod.POST)
    public HttpStatus addDocument(@RequestBody UpdateObjectDTO updateObject, @PathVariable("project_id") Long projectId) {
        Project project = projectService.getById(projectId);

        Document document = new Document();
        document.setTitle(updateObject.getTitle());
        document.setDescription(updateObject.getDescription());
        document.setCreatedBy(sessionState.getCurrentUser());

        documentRepository.save(document);
        projectService.addDocumentToProject(project, document);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/update-document", method = RequestMethod.POST)
    public HttpStatus updateDocument(@RequestBody UpdateObjectDTO updateObject) {
        Document document = documentService.getById(updateObject.getId());
        document.setTitle(updateObject.getTitle());
        document.setDescription(updateObject.getDescription());
        documentRepository.save(document);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/remove/{document_id}", method = RequestMethod.POST)
    public boolean remove(@PathVariable("document_id") Long documentId) {
        if (checkUserIsOwner(documentId)) {
            documentRepository.delete(documentId);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/check-user-is-owner/{document_id}", method = RequestMethod.GET)
    public boolean checkOwner(@PathVariable("document_id") Long documentId) {
        return checkUserIsOwner(documentId);
    }

    @RequestMapping(value = "/check-user-is-manager-of-project/{document_id}", method = RequestMethod.GET)
    public boolean checkManagerOfProject(@PathVariable("document_id") Long documentId) {
        Document document = documentService.getById(documentId);
        User projectManager = document.getProject().getProjectManager();

        return projectManager.equals(sessionState.getCurrentUser());
    }

    @RequestMapping(path = "all-messages/{document_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<MessageDTO> getMessagesFromDocument(@PathVariable("document_id") Long documentId) {
        Document document = documentService.getById(documentId);

        return documentService.getMessagesDTOFromDocument(document);
    }

    @RequestMapping(path = "add-message", method = RequestMethod.POST, produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public HttpStatus addMessageToDocument(@RequestBody NewMessageDTO newMessage) {
        Document document = documentService.getById(newMessage.getTargetId());
        User user = sessionState.getCurrentUser();

        Message message = new Message(user, new Date(), newMessage.getContent());
        documentService.addMessageToDocument(document, message);
        return HttpStatus.OK;
    }


    private boolean checkUserIsOwner(Long documentId) {
        Document document = documentService.getById(documentId);
        User currentLoggedUser = sessionState.getCurrentUser();

        return currentLoggedUser.equals(document.getCreatedBy());
    }
}
