package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.UserEditDTO;
import com.example.backendpensionat.Models.Role;
import com.example.backendpensionat.Models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    Iterable<User> listAllUsers();

    Optional<User> findUserById(UUID id);

    Set<Role> listAllRoles();

    Set<Role> findAllById(List<UUID> listOfIDs);

    void changeUser(UserEditDTO user);

    void deleteUser(UUID id);

    void addUser(UserEditDTO user);
}
