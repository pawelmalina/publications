package com.malina.bootstrap;

import com.malina.model.Message;
import com.malina.model.Project;
import com.malina.repositories.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 24.11.17.
 */
@Component
public class ProjectDataInserter {

    private final ProjectRepository projectRepository;
    private List<Project> projects = new ArrayList<>();

    public ProjectDataInserter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void insertData(){
        Project project1 = Project.builder().title("SWTPN")
                .name("System wspomagajacy tworzenie publikacji naukowych")
                .description("System wspomagajacy tworzenie publikacji naukowych - to projek..." + lorem)
                .build();
        projectRepository.save(project1);
        projects.add(project1);

        Project project2 = Project.builder().title("ANON")
                .name("System generowania obrazow 3D")
                .description("System umożliwia generowanie obrazow..." + lorem)
                .build();
        projectRepository.save(project2);
        projects.add(project2);

        Project project3 = Project.builder().title("EKNO")
                .name("System monitorowania procesu hartowania stali")
                .description("System umożliwia generowanie obrazow..." + lorem)
                .build();
        projectRepository.save(project3);
        projects.add(project3);
    }

    public List<Project> getProjects() {
        return projects;
    }

    private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
            " Pellentesque erat odio, venenatis nec tortor quis, consectetur ultricies massa." +
            " Vivamus vulputate facilisis tempus. Suspendisse sed maximus nibh. " +
            "Sed pretium velit sit amet tellus tincidunt commodo. Nunc non lorem eros. " +
            "In hac habitasse platea dictumst. Sed tempus libero sed faucibus porttitor. " +
            "Vestibulum viverra, lacus eget suscipit scelerisque, erat leo hendrerit dolor, " +
            "a mollis ipsum diam et ipsum. Nunc placerat auctor ligula in vehicula. " +
            "Nam nec neque lacinia, lacinia ex et, facilisis libero. Nunc egestas venenatis tortor, " +
            "ac facilisis urna lobortis vel. Aliquam ac leo quis nisi tristique aliquet. Suspendisse " +
            "ut facilisis velit. Cras felis lacus, ornare condimentum viverra vitae," +
            " hendrerit vitae mauris. Mauris ac velit turpis.";
}
