package org.tudai.userservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tudai.userservice.dto.UserDTO;
import org.tudai.userservice.entity.User;
import org.tudai.userservice.repository.UserRepository;
import org.tudai.userservice.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO("username", "John", "Doe", 123456789, "john.doe@example.com");
        User mockUser = new User("username", "John", "Doe", 123456789, "john.doe@example.com");
        mockUser.setId(1L);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        UserDTO savedUser = userService.save(userDTO);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUserName());
        assertEquals("John", savedUser.getName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals(123456789, savedUser.getPhoneNumber());
        assertEquals("john.doe@example.com", savedUser.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = List.of(
                new User("user1", "Alice", "Smith", 111111111, "alice.smith@example.com"),
                new User("user2", "Bob", "Brown", 222222222, "bob.brown@example.com")
        );
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserDTO> userDTOs = userService.getAll();

        assertNotNull(userDTOs);
        assertEquals(2, userDTOs.size());
        assertEquals("user1", userDTOs.get(0).getUserName());
        assertEquals("user2", userDTOs.get(1).getUserName());
    }

    @Test
    void testFindById() {

        User mockUser = new User("user1", "Alice", "Smith", 111111111, "alice.smith@example.com");
        mockUser.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        UserDTO foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUserName());
        assertEquals("Alice", foundUser.getName());
        assertEquals("Smith", foundUser.getLastName());
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User mockUser = new User("user1", "Alice", "Smith", 111111111, "alice.smith@example.com");
        mockUser.setId(1L);
        UserDTO updatedUserDTO = new UserDTO("newUser", "Alice", "Johnson", 123456789, "alice.johnson@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        // Act
        UserDTO result = userService.update(1L, updatedUserDTO);

        // Assert
        assertNotNull(result);
        assertEquals("newUser", result.getUserName());
        assertEquals("Johnson", result.getLastName());
        assertEquals(123456789, result.getPhoneNumber());
    }


    @Test
    void testDeleteUser() {

        Mockito.doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }


}
