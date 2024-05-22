package com.example.backendpensionat.Security;

import com.example.backendpensionat.Models.Role;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Repos.RoleRepo;
import com.example.backendpensionat.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    public void Seed(){
        if (roleRepo.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepo.findByName("Reception") == null) {
            addRole("Reception");
        }
        if(userRepo.getUserByUsername("admin@koriander.se") == null){
            addUser("admin@koriander.se","Admin");
        }
        if(userRepo.getUserByUsername("reception@koriander.se") == null){
            addUser("reception@koriander.se","Reception");
        }
    }

    private void addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Hejsan123#");
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        userRepo.save(user);
    }

    private void addRole(String name) {
        Role role = new Role();
        roleRepo.save(Role.builder().name(name).build());
    }

}