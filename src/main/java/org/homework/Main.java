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

        ProductDao productDao =  new ProductDao(emFactory);

        /*Product product_a = new Product("Product A", 12234);
        Product product_b = new Product( "Product B", 233);
        Product product_c = new Product( "Product C", 125);
        Product product_d = new Product( "Product D", 1251);

        productDao.saveOrUpdate(product_b);
        productDao.saveOrUpdate(product_c);
        productDao.saveOrUpdate(product_a);
        productDao.saveOrUpdate(product_d);*/

        /*productDao.deleteById(4L);
        productDao.deleteById(5L);
        productDao.deleteById(6L);*/


        System.out.println(productDao.findOneById(5L));

        System.out.println(productDao.findAll());

      // EntityManager em = emFactory.createEntityManager();


       // INSERT
       /*em.getTransaction().begin();

        Product product_a = new Product(1L, "Product A", 234);
        Product product_b = new Product(2L, "Product B", 233);
        Product product_c = new Product(3L, "Product C", 125);

        em.merge(product_c);

        em.getTransaction().commit();

        em.close();*/

        // SELECT
        /*System.out.println("Product with id 2");
        Product product = em.find(Product.class, 2L);
        System.out.println(product);

        System.out.println("All products");
        List<Product> from_product = em.createQuery("from Product", Product.class)
                .getResultList();
        System.out.println(from_product);

        System.out.println("Product with title Product A");
        Object product_any = em.createQuery("from Product pr where pr.title = :title")
                .setParameter("title", "Product A")
                .getSingleResult();
        System.out.println(product_any);*/


        //UPDATE
        /*Product product = em.find(Product.class, 3L);

        em.getTransaction().begin();

        product.setPrice(1234);

        em.getTransaction().commit();

        em.close();*/

        //DELETE
        /*em.getTransaction().begin();

        em.remove(em.find(Product.class, 2L));

        em.getTransaction().commit();

        em.close();*/
    }
}
