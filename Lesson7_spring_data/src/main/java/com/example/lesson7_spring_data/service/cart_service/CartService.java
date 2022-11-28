package com.example.lesson7_spring_data.service.cart_service;

import com.example.lesson7_spring_data.entity.product_entity.ProductRepr;
import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.product_service.ProductService;
import com.example.lesson7_spring_data.service.user_service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
@Service
public class CartService implements ICartService {

    private final ProductService productService;

    private final UserService userService;

    private Map<Long, Map<LineItem, Integer>> mapLineItem = new ConcurrentHashMap<>();
    private Map<LineItem, Integer> lineItemForUser;

    @Autowired
    public CartService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public void addProductForUser(long productId, long userId, int qty) {
        lineItemForUser = mapLineItem.computeIfAbsent(userId, k -> new HashMap<>());

        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        UserRepr userRepr = userService.findById(userId).orElseThrow(NotFoundException::new);

        LineItem key = new LineItem(productRepr, userRepr, qty);

        lineItemForUser.merge(key,qty, Integer::sum);

    }

    @Override
    public void removeProductForUser(long productId, long useId, int qty) {

        Map<LineItem, Integer> lineItemForUser = mapLineItem.getOrDefault(useId, new HashMap<>());
        if (lineItemForUser == null) {
            return;
        }

        LineItem key = new LineItem(productId, useId);
        Integer count = lineItemForUser.get(key);
        if (count != null) {
            if (count <= qty) {
                lineItemForUser.remove(key);
            } else {
                lineItemForUser.put(key, count - qty);
            }
        }
    }

    @Override
    public void removeAllForUser(long userId) {
        mapLineItem.remove(userId);
    }

    @Override
    public List<LineItem> findAllItems(long userId) {
        return new ArrayList<>(mapLineItem.get(userId).keySet());
    }
}
