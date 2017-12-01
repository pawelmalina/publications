package com.malina.bootstrap;

import com.malina.model.User;
import com.malina.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 24.11.17.
 */
@Component
public class UserDataInserter {

    private UserRepository userRepository;
    private ArrayList<User> users = new ArrayList<>();

    public UserDataInserter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertData() {

        User user1 = User.builder()
                .firstName("Alan")
                .lastName("Nowak")
                .email("alannowak@example.com")
                .password("pass")
                .phone("856474859")
                .build();
        userRepository.save(user1);
        users.add(user1);

        User user2 = User.builder()
                .firstName("Janek")
                .lastName("Kowalski")
                .email("janekkowalskik@example.com")
                .password("pass")
                .phone("747485859")
                .build();
        userRepository.save(user2);
        users.add(user2);

        User user3 = User.builder()
                .firstName("Marian")
                .lastName("Kozaczek")
                .email("mariankozaczek@example.com")
                .password("pass")
                .phone("1235437568")
                .build();
        userRepository.save(user3);
        users.add(user3);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
