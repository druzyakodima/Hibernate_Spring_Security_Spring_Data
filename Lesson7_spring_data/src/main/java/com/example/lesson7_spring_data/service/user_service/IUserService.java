package com.example.lesson7_spring_data.service.user_service;

import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserRepr> findAll();

    Optional<UserRepr> findById(Long id);

    void delete(Long id);

    void save(UserRepr userRepr);

    Page<UserRepr> pages(Integer currentPage, Integer countElementOnPage);
}
