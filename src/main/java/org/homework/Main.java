package org.homework;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.homework.dao.BuyersDao;
import org.homework.dao.ProductDao;
import org.homework.entity_manager.Execute;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Execute execute = new Execute(emFactory);

        BuyersDao buyersDao = new BuyersDao(execute);
        ProductDao productDao = new ProductDao(execute);


        // По id покупателя узнать список купленных им товаров
        buyersDao.getProductByIdBuyers(1L);
        System.out.println("-------------------------");
        // По id товара узнавать список покупателей этого товара
        productDao.findBuyerByIdProduct(9L);
    }
}
