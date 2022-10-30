package com.example.lesson7_spring_data.service.cart_service;


import java.util.List;

public interface ICartService {

    void addProductForUser(long productId,long userId, int qty);

    void  removeProductForUser(long productId, long useId, int qty);

    void removeAllForUser(long userId);

    List<LineItem> findAllItems(long userId);
}
