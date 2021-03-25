package com.example.study.repository;

import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = new Item();
        item.setName("노트북");
        item.setPrice(100000);
        item.setTitle("저렴이의 왕");
        item.setContent("삼성노트북");

        Item newItem = itemRepository.save(item);
        System.out.println(newItem);
    }

    @Test
    public void read() {
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        item.ifPresent(i -> {
            System.out.println(i);
        });
    }
}
