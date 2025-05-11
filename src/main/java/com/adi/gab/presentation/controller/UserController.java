package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.dto.TokenDTO;
import com.adi.gab.application.dto.request.RegisterRequest;
import com.adi.gab.application.usecase.user.LoginUseCase;
import com.adi.gab.application.usecase.user.RegisterUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public UserController(LoginUseCase loginUseCase, RegisterUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUserUseCase;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDTO<TokenDTO>> login(@RequestBody LoginRequest request) {
        TokenDTO token = loginUseCase.execute(request);

        ResponseDTO<TokenDTO> response = new ResponseDTO<>(
                "User logged in successfully",
                token,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDTO<TokenDTO>> register(@RequestBody RegisterRequest request) {
        TokenDTO token = registerUseCase.execute(request);

        ResponseDTO<TokenDTO> response = new ResponseDTO<>(
                "User registered in successfully",
                token,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
