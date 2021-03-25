package com.example.study.repository;

import com.example.study.StudyApplication;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setAccount("TestUser02");
        user.setEmail("TestUser@gmail.com");
        user.setPhoneNumber("010-2222-2222");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("test2");

        User newUser = userRepository.save(user);
        System.out.println("newUser : " + newUser);
        System.out.println(user);
    }

    @Test
    public void read() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            System.out.println("user : " + selectUser);
        });

    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    public void delete() {

    }
}
