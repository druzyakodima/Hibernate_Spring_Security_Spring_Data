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

        /*Product product_w = new Product(123L, "Product W", 12234);
        Product product_z = new Product(1234l, "Product Z", 233);
        Product product_x = new Product(12345L, "Product X", 125);
        Product product_y = new Product(123456L, "Product Y", 1251);

        productDao.saveOrUpdate(product_w);
        productDao.saveOrUpdate(product_z);
        productDao.saveOrUpdate(product_x);
        productDao.saveOrUpdate(product_y);*/

        /*Product product_y = new Product(123456L, "Product Y", 3362);
        productDao.saveOrUpdate(product_y);*/


        //productDao.saveOrUpdate(product_h);
        /*Product product_h = new Product(23L,123213L, "Product H", 53621);
        productDao.saveOrUpdate(product_h);*/
        /*productDao.deleteById(15L);
        productDao.deleteById(16L);
        productDao.deleteById(17L);
        productDao.deleteById(18L);*/


        System.out.println(productDao.findOneById(23L));

        System.out.println(productDao.findAll());

    }
}
