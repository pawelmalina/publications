package com.malina.bootstrap;

import com.malina.model.Document;
import com.malina.model.Project;
import com.malina.model.UploadedFile;
import com.malina.model.User;
import com.malina.repositories.DocumentRepository;
import com.malina.repositories.FileRepository;
import com.malina.services.DocumentService;
import com.malina.services.FileServiceImpl;
import com.malina.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private DocumentService documentService;

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
        documentService.addUploadedFileToDocument(document1, uf1);


        UploadedFile uf2 = createUploadedFile(paths.get(3), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 4));
        fileRepository.save(uf2);
        documentService.addUploadedFileToDocument(document1, uf2);

        UploadedFile uf3 = createUploadedFile(paths.get(2), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 3));
        fileRepository.save(uf3);
        documentService.addUploadedFileToDocument(document1, uf3);

        Document document2 = new Document();
        document2.setTitle("Rozdział 2");
        document2.setDescription("W rozdziale drugim... - " + DataUtils.LOREM);
        document2.setCreationDate(new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 2));
        document2.lock(new Date(new Date().getTime() + DateUtils.MILLIS_PER_DAY * 7), user2);
        document2.setCreatedBy(user2);
        documentRepository.save(document2);

        UploadedFile uf4 = createUploadedFile(paths.get(0), user1,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 2));
        fileRepository.save(uf4);
        documentService.addUploadedFileToDocument(document2, uf4);

        UploadedFile uf5 = createUploadedFile(paths.get(0), user2,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 1));
        fileRepository.save(uf5);
        documentService.addUploadedFileToDocument(document2, uf5);

        projectService.addDocumentToProject(project1, document1);
        projectService.addDocumentToProject(project1, document2);



        Project project2 = projects.get(1);
        List<User> users2 = new ArrayList<>();
        project2.getUsers().iterator().forEachRemaining(users2::add);
        User user3 = users2.get(0);
        User user4 = users2.get(1);

        Document document3 = new Document();
        document3.setTitle("Wstępne załozenie");
        document3.setDescription("Dokument ten zawiera.... - " + DataUtils.LOREM);
        document3.setCreationDate(new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 3));
        document3.setCreatedBy(user3);
        documentRepository.save(document3);

        UploadedFile uf6 = createUploadedFile(paths.get(4), user3,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 5));
        fileRepository.save(uf6);
        documentService.addUploadedFileToDocument(document3, uf6);


        UploadedFile uf7 = createUploadedFile(paths.get(3), user4,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 4));
        fileRepository.save(uf7);
        documentService.addUploadedFileToDocument(document3, uf7);

        UploadedFile uf8 = createUploadedFile(paths.get(2), user3,
                new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 3));
        fileRepository.save(uf8);
        documentService.addUploadedFileToDocument(document3, uf8);

        projectService.addDocumentToProject(project2, document3);

        Document document4 = new Document();
        document4.setTitle("Harmonogram");
        document4.setDescription("Harmonogram ten zawiera.... - " + DataUtils.LOREM);
        document4.setCreationDate(new Date(new Date().getTime() - DateUtils.MILLIS_PER_DAY * 3));
        document4.setCreatedBy(user4);
        documentRepository.save(document4);

        projectService.addDocumentToProject(project2, document4);

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
