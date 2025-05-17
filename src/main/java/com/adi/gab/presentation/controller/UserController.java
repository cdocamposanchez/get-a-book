package com.adi.gab.presentation.controller;

import com.adi.gab.application.dto.PaginationRequest;
import com.adi.gab.application.dto.UserDTO;
import com.adi.gab.application.dto.request.LoginRequest;
import com.adi.gab.application.dto.ResponseDTO;
import com.adi.gab.application.dto.AuthDTO;
import com.adi.gab.application.dto.request.RegisterRequest;
import com.adi.gab.application.usecase.user.DeleteUserUseCase;
import com.adi.gab.application.usecase.user.GetUsersUseCase;
import com.adi.gab.application.usecase.user.LoginUseCase;
import com.adi.gab.application.usecase.user.RegisterUseCase;
import com.adi.gab.application.usecase.user.UpdateFavoriteUseCase;
import com.adi.gab.application.usecase.user.UpdateUserUseCase;
import com.adi.gab.domain.valueobject.BookId;
import com.adi.gab.domain.valueobject.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateFavoriteUseCase updateFavoriteUseCase;

    public UserController(LoginUseCase loginUseCase,
                          RegisterUseCase registerUserUseCase,
                          GetUsersUseCase getUsersUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase, UpdateFavoriteUseCase updateFavoriteUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUserUseCase;
        this.getUsersUseCase = getUsersUseCase;

        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateFavoriteUseCase = updateFavoriteUseCase;
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

    @PutMapping
    public ResponseEntity<ResponseDTO<UserDTO>> update(
            @RequestBody UserDTO userDTO
    ) {
        UserDTO updatedUser = updateUserUseCase.execute(userDTO);
        ResponseDTO<UserDTO> response = new ResponseDTO<>(
                "User successfully updated",
                updatedUser,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-favorite")
    public ResponseEntity<ResponseDTO<String>> updateFavorites(
            @RequestParam UUID bookId
    ) {
        String message = updateFavoriteUseCase.execute(BookId.of(bookId));
        ResponseDTO<String> response = new ResponseDTO<>(
                "User favorites successfully updated",
                "Book with ID: " + bookId + " Has been " + message,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<UserDTO>> delete(@RequestParam UUID userId) {
        deleteUserUseCase.execute(UserId.of(userId));
        ResponseDTO<UserDTO> response = new ResponseDTO<>(
                "User successfully deleted with id: " + userId,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationRequest pagination = new PaginationRequest(page, size);
        List<UserDTO> users = getUsersUseCase.execute(pagination);
        ResponseDTO<List<UserDTO>> response = new ResponseDTO<>(
                "Users retrieved successfully",
                users,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getById(@PathVariable UUID id) {
        UserDTO user = getUsersUseCase.getByUserId(UserId.of(id));
        ResponseDTO<UserDTO> response = new ResponseDTO<>(
                "User retrieved with ID: "+ id +" successfully",
                user,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
