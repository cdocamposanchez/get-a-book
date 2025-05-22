package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.application.dto.request.RecoverRequest;
import com.adi.gab.application.dto.request.RegisterRequest;
import com.adi.gab.application.usecase.user.LoginUseCase;
import com.adi.gab.application.usecase.user.RecoverPasswordUseCase;
import com.adi.gab.application.usecase.user.RegisterUseCase;
import com.adi.gab.application.usecase.user.ValidateTokenUseCase;
import com.adi.gab.domain.valueobject.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;
    private final RecoverPasswordUseCase recoverPasswordUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase, ValidateTokenUseCase validateTokenUseCase, RecoverPasswordUseCase recoverPasswordUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.validateTokenUseCase = validateTokenUseCase;
        this.recoverPasswordUseCase = recoverPasswordUseCase;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDTO<AuthDTO>> login(@RequestBody LoginRequest request) {
        AuthDTO token = loginUseCase.execute(request);

        ResponseDTO<AuthDTO> response = new ResponseDTO<>(
                "User logged in successfully",
                token,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDTO<AuthDTO>> register(@RequestBody RegisterRequest request) {
        AuthDTO token = registerUseCase.execute(request);

        ResponseDTO<AuthDTO> response = new ResponseDTO<>(
                "User registered in successfully",
                token,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<ResponseDTO<Boolean>> validateToken(@RequestParam String token) {
        boolean isValid = validateTokenUseCase.isValid(token);

        ResponseDTO<Boolean> response = new ResponseDTO<>(
                isValid ? "Token válido" : "Token inválido o expirado",
                isValid,
                isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED
        );

        return new ResponseEntity<>(response, isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/token/user-id")
    public ResponseEntity<ResponseDTO<String>> getUserIdFromToken(@RequestParam String token) {
        UserId userId = validateTokenUseCase.getUserId(token);
        ResponseDTO<String> response = new ResponseDTO<>(
                "UserId extraído correctamente",
                userId.value().toString(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/recover/send-code")
    public ResponseEntity<ResponseDTO<String>> sendRecoveryCode(@RequestParam String email) {
        recoverPasswordUseCase.sendRecoveryCode(email);

        ResponseDTO<String> response = new ResponseDTO<>(
                "Recovery code sent successfully to " + email,
                null,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/recover/validate-code")
    public ResponseEntity<ResponseDTO<Boolean>> validateRecoveryCode(@RequestBody RecoverRequest request) {
        boolean isValid = recoverPasswordUseCase.validateRecoveryCode(request.getEmail(), request.getRecoverCode());

        ResponseDTO<Boolean> response = new ResponseDTO<>(
                isValid ? "Recovery code is valid" : "Invalid recovery code",
                isValid,
                isValid ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(response, isValid ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/recover/change-password")
    public ResponseEntity<ResponseDTO<String>> changePassword(@RequestBody RecoverRequest request) {
        recoverPasswordUseCase.changePassword(request);

        ResponseDTO<String> response = new ResponseDTO<>(
                "Password changed successfully",
                null,
                HttpStatus.OK
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
