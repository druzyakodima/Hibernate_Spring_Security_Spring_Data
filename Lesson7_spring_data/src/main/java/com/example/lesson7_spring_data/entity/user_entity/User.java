package com.example.lesson7_spring_data.entity.user_entity;


import com.example.lesson7_spring_data.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String username;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 128, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private Integer age;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(UserRepr userRepr) {
        this.id = userRepr.getId();
        this.username = userRepr.getUsername();
        this.password = userRepr.getPassword();
        this.email = userRepr.getEmail();
        this.age = userRepr.getAge();
        this.roles = userRepr.getRoles();
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
