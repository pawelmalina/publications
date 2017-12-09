package com.malina.services;

import com.malina.model.Project;
import com.malina.model.User;

import java.util.List;

/**
 * Created by pawel on 09.12.17.
 */
public interface ProjectService {

    void addUserToProject(Project project, User user);
}
