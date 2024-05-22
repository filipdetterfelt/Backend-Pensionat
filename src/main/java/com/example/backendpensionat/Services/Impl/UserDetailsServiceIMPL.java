package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Security.ConcreteUserDetails;
import com.example.backendpensionat.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceIMPL implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new ConcreteUserDetails(user);
    }

    public Iterable<User> listAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findUserById(UUID id){
        return userRepo.findById(id);
    }

    public void changeUser(User user) {
        User u = userRepo.getUserByUsername(user.getUsername());
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setRoles(u.getRoles());
        userRepo.save(user);
    }
}
