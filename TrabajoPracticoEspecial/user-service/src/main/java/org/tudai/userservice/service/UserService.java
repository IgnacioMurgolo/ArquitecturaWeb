package org.tudai.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.userservice.dto.AccountDTO;
import org.tudai.userservice.dto.UserDTO;
import org.tudai.userservice.entity.User;
import org.tudai.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO save(UserDTO user) {
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setEmail(user.getEmail());

        newUser = userRepository.save(newUser);
        return new UserDTO(newUser);
    }

    @Transactional
    public List<UserDTO> getAll() {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDTO dto = convertToDTO(user);
            result.add(dto);
        }
        return result;
    }

    @Transactional
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + id));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO user) {
        User userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + id));
        userUpdate.setId(id);
        userUpdate.setUserName(user.getUserName());
        userUpdate.setName(user.getName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setPhoneNumber(user.getPhoneNumber());
        userUpdate.setEmail(user.getEmail());
        userUpdate = userRepository.save(userUpdate);
        return convertToDTO(userUpdate);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user);
    }

}
