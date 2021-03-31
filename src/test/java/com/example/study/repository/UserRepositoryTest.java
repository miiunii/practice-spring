package com.example.study.repository;

import com.example.study.StudyApplication;
import com.example.study.model.entity.User;
import jdk.vm.ci.meta.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String account = "Test01";
        String password = "Test01";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-2222";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        User newUser = userRepository.save(user);
        assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("------------------주문묶음--------------------");
            System.out.println("총금 : " + orderGroup.getTotalPrice());
            System.out.println("수령인 : " + orderGroup.getRevName());
            System.out.println("수령지 : " + orderGroup.getRevAddress());
            System.out.println("총수량 : " + orderGroup.getTotalQuantity());

            System.out.println("-------------------주문상세-------------------");
            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println("주문상태 : " + orderDetail.getStatus());
                System.out.println("주문도착예정일 : " + orderDetail.getArrivalDate());

                System.out.println("아이템이름 : " + orderDetail.getItem().getName());
                System.out.println("콜센터 : " + orderDetail.getItem().getPartner().getCallCenter());

                System.out.println("카테고리 타이틀 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
            });

        });

        System.out.println(user);

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
    @Transactional // 테스트만 해보고 값은 원래대로 돌려줌
    public void delete() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(2L);

        if(deleteUser.isPresent()) {
            System.out.println("데이터 존재 : " + deleteUser.get());
        } else {
            System.out.println("데이터 삭제 데이터 없음 ");
        }
    }
}
