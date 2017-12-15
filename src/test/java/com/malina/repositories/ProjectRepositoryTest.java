package com.malina.repositories;

import com.malina.bootstrap.DevDataInserter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by pawel on 15.12.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired
    DevDataInserter devDataInserter;

    @Autowired
    ProjectRepository projectRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getMessagesFromProjectTest() {

    }
}