package com.adi.gab.domain.model;

import com.adi.gab.domain.exception.UserExceptions;
import com.adi.gab.domain.types.UserRole;
import com.adi.gab.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private UserId id;
    private String firstName;
    private String lastNames;
    private String email;
    private String password;
    private UserRole role;

    public User create(String firstName, String lastName, String email, String password) {
        if (firstName == null || firstName.isEmpty()) throw new UserExceptions.NullUserArgumentException("FirstName");
        if (lastName == null || lastName.isEmpty()) throw new UserExceptions.NullUserArgumentException("LastName");
        if (email == null || email.isEmpty()) throw new UserExceptions.NullUserArgumentException("Email");

        return User.builder()
                .id(UserId.generate())
                .firstName(firstName)
                .lastNames(lastName)
                .email(email)
                .password(password)
                .build();
    }

    public User update(User oldUser, User newUser) {
        return User.builder()
                .id(oldUser.getId())
                .firstName(
                        (newUser.getFirstName() != null && !newUser.getFirstName().isEmpty() &&
                                !newUser.getFirstName().equals(oldUser.getFirstName()))
                                ? newUser.getFirstName()
                                : oldUser.getFirstName()
                )
                .lastNames(
                        (newUser.getLastNames() != null && !newUser.getLastNames().isEmpty() &&
                                !newUser.getLastNames().equals(oldUser.getLastNames()))
                                ? newUser.getLastNames()
                                : oldUser.getLastNames()
                )
                .email(
                        (newUser.getEmail() != null && !newUser.getEmail().isEmpty() &&
                                !newUser.getEmail().equals(oldUser.getEmail()))
                                ? newUser.getEmail()
                                : oldUser.getEmail()
                )
                .password(
                        (newUser.getPassword() != null && !newUser.getPassword().isEmpty() &&
                                !newUser.getPassword().equals(oldUser.getPassword()))
                                ? newUser.getPassword()
                                : oldUser.getPassword()
                )
                .build();
    }
}
