package com.malina.bootstrap;

import com.malina.auth.AuthUtils;
import com.malina.auth.Role;
import com.malina.model.User;
import com.malina.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
                .password(getExamplePassword())
                .phone("856474859")
                .role(Role.MENAGER)
                .build();
        userRepository.save(user1);
        users.add(user1);

        User user2 = User.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jankowalski@example.com")
                .password(getExamplePassword())
                .phone("747485859")
                .role(Role.MENAGER)
                .build();
        userRepository.save(user2);
        users.add(user2);

        User user3 = User.builder()
                .firstName("Marian")
                .lastName("Kozaczek")
                .email("mariankozaczek@example.com")
                .password(getExamplePassword())
                .phone("1235437568")
                .role(Role.MENAGER)
                .build();
        userRepository.save(user3);
        users.add(user3);

        User user4 = User.builder()
                .firstName("Piotr")
                .lastName("Kasprzyk")
                .email("piotrkasprzyk@example.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user4);
        users.add(user4);

        User user5 = User.builder()
                .firstName("Antoni")
                .lastName("Ulman")
                .email("antoniulman@example.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user5);
        users.add(user5);

        User user6 = User.builder()
                .firstName("Katarzyna")
                .lastName("Kas")
                .email("katarzynakas@example.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user6);
        users.add(user6);

        User user7 = User.builder()
                .firstName("Anna")
                .lastName("Borut")
                .email("annaborut@example.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user7);
        users.add(user7);

        User user8 = User.builder()
                .firstName("Edward")
                .lastName("Borgosz")
                .email("edwardborgosz@gmail.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user8);
        users.add(user8);

        User user9 = User.builder()
                .firstName("Barbara")
                .lastName("Bylica")
                .email("barbarabylica@gmail.com")
                .password(getExamplePassword())
                .phone("23123123123")
                .build();
        userRepository.save(user9);
        users.add(user9);

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    private String getExamplePassword()  {
        return AuthUtils.Sha256(AuthUtils.Sha256("pass"));
    }
}
