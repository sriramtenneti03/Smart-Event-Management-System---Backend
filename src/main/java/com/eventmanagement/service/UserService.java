package com.eventmanagement.service;

import com.eventmanagement.dto.CreateUserDTO;
import com.eventmanagement.dto.UserDTO;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for User operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setFullName(createUserDTO.getFullName());
        user.setPassword(createUserDTO.getPassword()); // In production, should be hashed
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setCity(createUserDTO.getCity());
        user.setCountry(createUserDTO.getCountry());
        user.setInterests(createUserDTO.getInterests());
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return convertToDTO(user);
    }

    public List<UserDTO> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<UserDTO> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByCity(String city) {
        return userRepository.findByCity(city).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, CreateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setFullName(updateUserDTO.getFullName());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        user.setCity(updateUserDTO.getCity());
        user.setCountry(updateUserDTO.getCountry());
        user.setInterests(updateUserDTO.getInterests());
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setIsActive(false);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.getBio(),
            user.getProfileImageUrl(),
            user.getCity(),
            user.getCountry(),
            user.getInterests(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getIsActive()
        );
    }
}
