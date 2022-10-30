package com.example.lesson7_spring_data.service.user_service;

import com.example.lesson7_spring_data.entity.user_entity.User;
import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import com.example.lesson7_spring_data.repository.user_repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<UserRepr> findById(Long id) {
        return userRepository.findById(id)
                .map(UserRepr::new);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(UserRepr userRepr) {
        User user = new User(userRepr);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Page<UserRepr> pages(Integer currentPage, Integer countElementOnPage) {
        return userRepository.findAll(PageRequest.of(currentPage, countElementOnPage)).map(UserRepr::new);
    }

}
