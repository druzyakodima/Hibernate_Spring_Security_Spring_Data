package org.homework.dao;

import lombok.Getter;
import org.homework.entity.Buyer;
import org.homework.entity.Product;
import org.homework.entity_manager.Execute;

import java.util.List;

@Getter
public class ProductDao {

    private final Execute execute;

    public ProductDao(Execute execute) {
        this.execute = execute;
    }


    public Product findOneById(Long id) {
        return execute.executeEntityManager(em -> em.find(Product.class, id));
    }


    public List<Product> findAll() {
        return execute.executeEntityManager(em -> em.createQuery("from Product", Product.class).getResultList());
    }

    public void deleteById(Long id) {

        execute.executeInTransaction(em -> {
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
            }
            else {
                System.out.println("Нет такого продукта!");
            }
        });
    }

    public void update(Product product) {

        if (product != null) {
            execute.executeInTransaction(em -> em.merge(product));
        } else {
            System.out.println("Нет такого продукта");
        }
    }

    public void insert(Product product) {
        if (product != null) {
            Product new_product = product;
            execute.executeInTransaction(em -> em.persist(new_product));
        } else {
            System.out.println("Не удалось добавить продукт");
        }
    }

    public void findBuyerByIdProduct(Long id) {
        Product product = execute.executeEntityManager(em -> em.find(Product.class, id));
        product.getBuyer().forEach(System.out::println);
    }
}
