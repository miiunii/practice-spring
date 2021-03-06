package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j // api loging
@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse>{

    @Autowired
    private UserApiLogicService userApiLogicService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> userApiRequest) {
        log.info("{}", userApiRequest);
        return userApiLogicService.create(userApiRequest);
    }

    @Override
    @GetMapping("{id}")
    public Header<UserApiResponse> read(@PathVariable Long id) {
        log.info("read : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> userApiRequest) {

        return userApiLogicService.update(userApiRequest);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return userApiLogicService.delete(id);
    }
}
