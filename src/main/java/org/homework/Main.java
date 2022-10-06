package org.homework;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.homework.entity.Product;
import org.homework.product_dao.ProductDao;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductDao productDao = new ProductDao(emFactory);

        /*Product product_w = new Product(19L,123L, "Product W", 12234);
        Product product_z = new Product(20L,1234l, "Product Z", 233);
        Product product_x = new Product(21L,12345L, "Product X", 125);
        Product product_y = new Product(22L,123456L, "Product Y", 1251);

        productDao.saveOrUpdate(product_w);
        productDao.saveOrUpdate(product_z);
        productDao.saveOrUpdate(product_x);
        productDao.saveOrUpdate(product_y);*/

        /*Product product_y = new Product(22L,123456L, "Product Y", 3362);
        productDao.saveOrUpdate(product_y);*/

        /*productDao.deleteById(15L);
        productDao.deleteById(16L);
        productDao.deleteById(17L);
        productDao.deleteById(18L);*/


        System.out.println(productDao.findOneById(10L));

        System.out.println(productDao.findAll());

    }
}
