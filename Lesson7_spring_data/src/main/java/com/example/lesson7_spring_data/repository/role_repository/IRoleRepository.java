package com.example.lesson7_spring_data.repository.role_repository;

import com.example.lesson7_spring_data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

}
