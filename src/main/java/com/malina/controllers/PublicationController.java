package com.malina.controllers;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.Publication;
import com.malina.model.UploadedFile;
import com.malina.model.dto.ProjectDTO;
import com.malina.model.dto.PublicationDTO;
import com.malina.model.dto.UpdateObjectDTO;
import com.malina.repositories.PublicationRepository;
import com.malina.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pawel on 17.01.18.
 */
@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = {"${origin.domain}", "*"})
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationRepository publicationRepository;
    private final DocumentService documentService;


    @RequestMapping(value = "/all")
    @ResponseBody
    public List<PublicationDTO> getPublications(){
        List<Publication> publications = publicationRepository.findAll();
        return publications.stream().map(pub -> new PublicationDTO().convertPublicationToDTO(pub))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/new")
    @ResponseBody
    public boolean newPublication(@RequestBody UpdateObjectDTO dto){
        Document document = documentService.getById(dto.getId());
        UploadedFile file = document.getCurrentVersion();
        Project project = document.getProject();

        Publication publication = Publication.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
//                .project(project)
                .file(file)
                .build();
        publicationRepository.save(publication);
        return true;
    }




}
