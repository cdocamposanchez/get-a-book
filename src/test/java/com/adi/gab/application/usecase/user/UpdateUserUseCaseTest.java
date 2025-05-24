//package com.adi.gab.application.usecase.user;
//import com.adi.gab.application.dto.UserDTO;
//import com.adi.gab.application.exception.NotFoundException;
//import com.adi.gab.application.usecase.user.UpdateUserUseCase;
//import com.adi.gab.domain.valueobject.UserId;
//import com.adi.gab.domain.types.UserRole;
//import com.adi.gab.infrastructure.persistance.entity.UserEntity;
//import com.adi.gab.infrastructure.persistance.repository.UserRepository;
//import com.adi.gab.infrastructure.security.AuthenticatedUserProvider;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//public class UpdateUserUseCaseTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private AuthenticatedUserProvider authenticatedUserProvider;
//
//    @InjectMocks
//    private UpdateUserUseCase updateUserUseCase;
//
//    private UUID authenticatedUserId;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        authenticatedUserId = UUID.randomUUID();
//        when(authenticatedUserProvider.getAuthenticatedUserId()).thenReturn(new UserId(authenticatedUserId));
//    }
//
//    @Test
//    void shouldUpdateUserSuccessfully() {
//        UserEntity existingUserEntity = UserEntity.builder()
//                .id(authenticatedUserId)
//                .firstName("OldFirstName")
//                .lastNames("OldLastNames")
//                .email("old@example.com")
//                .password("oldpassword")
//                .role(UserRole.CLIENT)
//                .favorites(new ArrayList<>()) // <-- ¡IMPORTANTE: Inicializar la lista aquí!
//                .build();
//
//        UserDTO updatedUserDTO = UserDTO.builder()
//                .firstName("NewFirstName")
//                .lastNames("NewLastNames")
//                .email("new@example.com")
//                .role(UserRole.ADMIN)
//                .favorites(new ArrayList<>()) // <-- ¡IMPORTANTE: Inicializar la lista aquí!
//                .build();
//
//        UserEntity savedUserEntity = UserEntity.builder()
//                .id(authenticatedUserId)
//                .firstName(updatedUserDTO.getFirstName())
//                .lastNames(updatedUserDTO.getLastNames())
//                .email(updatedUserDTO.getEmail())
//                .password(existingUserEntity.getPassword())
//                .role(updatedUserDTO.getRole())
//                .favorites(new ArrayList<>())
//                .build();
//
//
//        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(existingUserEntity));
//        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);
//
//        // Act
//        UserDTO result = updateUserUseCase.execute(updatedUserDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(authenticatedUserId, result.getId());
//        assertEquals(updatedUserDTO.getFirstName(), result.getFirstName());
//        assertEquals(updatedUserDTO.getLastNames(), result.getLastNames());
//        assertEquals(updatedUserDTO.getEmail(), result.getEmail());
//        assertEquals(updatedUserDTO.getRole(), result.getRole());
//
//        verify(authenticatedUserProvider).getAuthenticatedUserId();
//        verify(userRepository).findById(authenticatedUserId);
//        verify(userRepository).save(any(UserEntity.class));
//    }
//
//    @Test
//    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
//        // Arrange
//        UserDTO userDTO = UserDTO.builder()
//                .id(UUID.randomUUID())
//                .firstName("Any")
//                .lastNames("User")
//                .email("any@example.com")
//                .favorites(new ArrayList<>())
//                .build();
//
//        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            updateUserUseCase.execute(userDTO);
//        });
//
//        assertTrue(exception.getMessage().contains("Order not found with ID: " + userDTO.getId()));
//
//        verify(authenticatedUserProvider).getAuthenticatedUserId();
//        verify(userRepository).findById(authenticatedUserId);
//        verify(userRepository, never()).save(any(UserEntity.class));
//    }
//}
