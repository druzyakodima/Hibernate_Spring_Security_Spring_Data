package com.example.lesson7_spring_data.entity;

import com.example.lesson7_spring_data.entity.user_entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true,nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany
    private List<User> users;

    public Role() {
    }


}
