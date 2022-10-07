package org.homework.product_dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.homework.entity.Product;

import java.util.List;


@Getter
public class ProductDao {
    private final EntityManagerFactory EMFactory;

    public ProductDao(EntityManagerFactory EMFactory) {
        this.EMFactory = EMFactory;
    }


    public Product findOneById(Long id) {

        EntityManager em = EMFactory.createEntityManager();
        em.getTransaction().begin();

        Product product = em.find(Product.class, id);
        em.getTransaction().commit();

        em.close();
        if (product == null) {
            System.out.println("Нет такого продукта!");
        }

        return product;
    }


    public List<Product> findAll() {

        EntityManager em = EMFactory.createEntityManager();
        em.getTransaction().begin();

        List<Product> from_products = em.createQuery("from Product ", Product.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return from_products;
    }

    public void deleteById(Long id) {

        EntityManager em = EMFactory.createEntityManager();
        em.getTransaction().begin();

        if (em.find(Product.class, id) != null) {
            em.remove(em.find(Product.class, id));
        } else {
            System.out.println("Нет такого продкукта!");
        }

        em.getTransaction().commit();
        em.close();

    }

    public Product saveOrUpdate(Product product) {

        EntityManager em = EMFactory.createEntityManager();
        em.getTransaction().begin();

        List<Product> productList = em.createQuery("from Product ", Product.class).getResultList();

        if (productList.toString().matches("(.*)" + product.getTitle() + "(.*)")) {

            Product product_change = em.find(Product.class,product.getId());

            product_change.setTitle(product.getTitle());
            product_change.setPrice(product.getPrice());
            product_change.setProduct_id(product.getProduct_id());

        } else {
            em.persist(product);
        }

        em.getTransaction().commit();
        em.close();

        return product;
    }

}
