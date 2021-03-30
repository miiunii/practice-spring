package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryRepositoryTest extends StudyApplicationTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        try {
            String type = "Computer";
            String title = "컴퓨터";
            LocalDateTime createdAt = LocalDateTime.now();
            String createdBy = "AdminServer";

            Category category = new Category();
            category.setType(type);
            category.setTitle(title);
            category.setCreatedAt(createdAt);
            category.setCreatedBy(createdBy);

            Category newCategory = categoryRepository.save(category);
            assertNotNull(newCategory);
            assertEquals(newCategory.getType(), type);
            assertEquals(newCategory.getTitle(), title);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void read() {
        Optional<Category> optionalCategory = categoryRepository.findByType("Computer");

        // select * from category where type = "Computer"

        optionalCategory.ifPresent(c -> {
            System.out.println(c.getId());
            System.out.println(c.getTitle());
            System.out.println(c.getCreatedAt());
            System.out.println(c.getCreatedBy());
        });
    }
}
