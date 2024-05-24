package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.UserEditDTO;
import com.example.backendpensionat.Models.Role;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Repos.RoleRepo;
import com.example.backendpensionat.Repos.UserRepo;
import com.example.backendpensionat.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public Iterable<User> listAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserById(UUID id){
        return userRepo.findById(id);
    }

    @Override
    public Set<Role> listAllRoles(){
        return new HashSet<>(roleRepo.findAll());
    }

    @Override
    public Set<Role> findAllById(List<UUID> listOfIDs) {
        return new HashSet<>(roleRepo.findAllById(listOfIDs));
    }

    @Override
    public void changeUser(UserEditDTO user) {
        Optional<User> userFromDB = userRepo.findById(user.getId());
        Set<Role> roles = findAllById(user.getRoleIds());

        if(userFromDB.isPresent()){
            String password = user.getPassword() == null ? userFromDB.get().getPassword() : hashPassword(user.getPassword());
            userFromDB.get().setUsername(user.getUsername());
            userFromDB.get().setPassword(password);
            userFromDB.get().getRoles().addAll(roles);
            userRepo.save(userFromDB.get());
        }
    }

    private String hashPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
