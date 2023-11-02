package com.sv.userapi.controller.v1;

import com.sv.userapi.domain.dto.UserDTO;
import com.sv.userapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/api/v1")
@Tag(name = "Test", description = "test to emulate login for user")

public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/tests/login")
    public String login(String email, String password){
        return userService.login(email, password);
    }

}
