package org.homework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "buyers")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> productList;

    public Buyer() {
        productList = new ArrayList<>();
    }

    public Buyer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Buyer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Buyers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
