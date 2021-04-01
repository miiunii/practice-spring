package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminUserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("AdminUser02");
        adminUser.setPassword("AdminUser02");
        adminUser.setStatus("REGISTERED");
        adminUser.setRole("SUPER");
//        adminUser.setLastLoginAt();
//        adminUser.setPasswordUpdatedAt();
//        adminUser.setLoginFailCount();
//        adminUser.setRegisteredAt();
//        adminUser.setUnregisteredAt();
//        adminUser.setCreatedAt(LocalDateTime.now());
//        adminUser.setCreatedBy("AdminServer");
//        adminUser.setUpdatedAt();
//        adminUser.setUpdatedBy();
        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        assertNotNull(newAdminUser);
    }
}
