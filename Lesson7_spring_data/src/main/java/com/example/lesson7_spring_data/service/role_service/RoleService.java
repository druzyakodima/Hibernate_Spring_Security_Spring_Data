package com.example.lesson7_spring_data.service.role_service;

import com.example.lesson7_spring_data.entity.Role;
import com.example.lesson7_spring_data.repository.role_repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService implements IRoleService {

    private IRoleRepository roleRepository;

    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleService() {
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
