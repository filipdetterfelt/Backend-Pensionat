package com.example.backendpensionat.Security;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {

    Role findByName(String name);
}
