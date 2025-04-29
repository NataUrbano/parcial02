package com.library.parcial02.service;

import com.library.parcial02.model.RoleEntity;
import com.library.parcial02.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Set<RoleEntity> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return new HashSet<>(roles);
    }

    public void saveRole(RoleEntity role) {
        roleRepository.save(role);
    }

}
