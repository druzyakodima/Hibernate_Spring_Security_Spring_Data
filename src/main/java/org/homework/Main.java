package org.homework;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.homework.dao.ProductDao;
import org.homework.entity.Product;
import org.homework.entity_manager.Execute;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Execute execute = new Execute(emFactory);
        ProductDao productDao = new ProductDao(execute);

        System.out.println(productDao.findOneById(29L));

        Product product_f = new Product(29L, 21L, "Product F", 11);
        productDao.update(product_f);

        System.out.println(productDao.findOneById(29L));
        System.out.println(productDao.findAll());

    }
}
