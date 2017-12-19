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
                .description("System wspomagajacy tworzenie publikacji naukowych - to projek..." + DataUtils.LOREM)
                .build();
        projectRepository.save(project1);
        projects.add(project1);

        Project project2 = Project.builder().title("ANON")
                .name("System generowania obrazow 3D")
                .description("System umożliwia generowanie obrazow..." + DataUtils.LOREM)
                .build();
        projectRepository.save(project2);
        projects.add(project2);

        Project project3 = Project.builder().title("EKNO")
                .name("System monitorowania procesu hartowania stali")
                .description("System umożliwia generowanie obrazow..." + DataUtils.LOREM)
                .build();
        projectRepository.save(project3);
        projects.add(project3);
    }

    public List<Project> getProjects() {
        return projects;
    }

}
