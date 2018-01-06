package com.malina.bootstrap;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import com.malina.repositories.ProjectRepository;
import com.malina.services.FileServiceImpl;
import com.malina.services.ProjectService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pawel on 19.12.17.
 */
@Slf4j
@Component
public class DocumentDataInserter {

    private final Path pathToFilesDirectory = Paths.get("example.files");

    private final DocumentRepository documentRepository;
    private final FileRepository fileRepository;
    private final FileServiceImpl fileService;
    private final ProjectService projectService;

    private List<Project> projects;
    private List<Path> paths;

    public DocumentDataInserter(DocumentRepository documentRepository, FileRepository fileRepository,
                                ProjectService projectService, FileServiceImpl fileService) throws IOException {
        this.documentRepository = documentRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.projectService = projectService;

        initFilesPaths();
    }

    public void insertData(List<Project> projects) {
        this.projects = projects;

        Project project1 = projects.get(0);
        List<User> users = new ArrayList<>();
        project1.getUsers().iterator().forEachRemaining(users::add);
        User user1 = users.get(0);
        User user2 = users.get(1);

        Document document1 = new Document();
        document1.setTitle("Ramowy plan projektu");
        document1.setDescription("Plan projektu zawiera.... - " + DataUtils.LOREM);
        document1.setCreationDate(new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 5));
        document1.setCreatedBy(user1);
        documentRepository.save(document1);

        UploadedFile uf1 = createUploadedFile(paths.get(4), user1,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 5));
        fileRepository.save(uf1);
        fileService.addUploadedFileToDocument(document1, uf1);


        UploadedFile uf2 = createUploadedFile(paths.get(3), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 4));
        fileRepository.save(uf2);
        fileService.addUploadedFileToDocument(document1, uf2);

        UploadedFile uf3 = createUploadedFile(paths.get(2), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 3));
        fileRepository.save(uf3);
        fileService.addUploadedFileToDocument(document1, uf3);

        Document document2 = new Document();
        document2.setTitle("Rozdział 2");
        document2.setDescription("W rozdziale drugim... - " + DataUtils.LOREM);
        document2.setCreationDate(new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 2));
        document2.setCreatedBy(user2);
        documentRepository.save(document2);

        UploadedFile uf4 = createUploadedFile(paths.get(0), user1,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 2));
        fileRepository.save(uf4);
        fileService.addUploadedFileToDocument(document2, uf4);

        UploadedFile uf5 = createUploadedFile(paths.get(0), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 1));
        fileRepository.save(uf5);
        fileService.addUploadedFileToDocument(document2, uf5);

        projectService.addDocumentToProject(project1, document1);
        projectService.addDocumentToProject(project1, document2);

    }

    private void initFilesPaths() throws IOException {
        Stream<Path> streamPaths = Files.list(pathToFilesDirectory);
        paths = streamPaths.collect(Collectors.toList());
        log.debug("Find " + paths.size() + " files to upload");
    }

    private UploadedFile createUploadedFile(Path path, User user, Date date) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            log.debug("Upload file " + fileName);
            return new UploadedFile(fileName, date, user, bytes);
        } catch (IOException e) {
            log.debug("Uploading file - problem with path");
        } finally {
        }
        return null;
    }
}
