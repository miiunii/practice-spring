package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> Userapi response return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();


        User newUser = userRepository.save(user);

        String checkEmail = user.getEmail();
        Optional<User> optional = userRepository.findByEmail(checkEmail);

        return optional
                .map(email -> {
                    return Header.ERROR("409 이메일이 이미 존재합니다");
                })
                .orElseGet(() -> {
                    return response(newUser);
                });

        // 3. 생성된 데이터 -> userApiResponse return
        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repository getOne, getByID
        Optional<User> optional = userRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> {
                    return response(user);
                })
                .orElseGet(() -> {
                    return Header.ERROR("데이터 없음");
                });
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. data
        UserApiRequest userApiRequest = request.getData();
        // 2. id -> user 데이터를 찾고
        Optional<User> optional = userRepository.findById(userApiRequest.getId());
        return optional.map(user -> {
            // 3. update
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());

            return user;

        })
                // 4. userApiResponse
                .map(user -> {
                    return userRepository.save(user);
                }) // update
                .map(updateUser -> {
                    return response(updateUser);
                }) // userApiResponse
                .orElseGet(() -> {
                    return Header.ERROR("데이터 없음");
                });
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);
        // 2. repository -> delete
        optional.map(user -> {
            userRepository.delete(user);
            return Header.OK();
        })
                .orElseGet(() -> {
                    return Header.ERROR("데이터없음");
                });
        return null;
    }

    private Header<UserApiResponse> response (User user) {
        // user -> userApiResponse
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword()) // todo 암호화, 길이
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unRegisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data return
        return Header.OK(userApiResponse);
    }
}
