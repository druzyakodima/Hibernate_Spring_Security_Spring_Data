package org.homework.dao;

import org.homework.entity.Buyer;
import org.homework.entity_manager.Execute;

import java.util.List;

public class BuyersDao {

    private final Execute execute;
    public BuyersDao(Execute execute) {
        this.execute = execute;
    }

    public void insert(Buyer buyer) {
        if (buyer != null) {
            execute.executeInTransaction(em -> em.persist(buyer));
        } else {
            System.out.println("Не удалось добавить покупателя");
        }
    }

    public void update(Buyer buyer) {

        if (buyer != null) {
            execute.executeInTransaction(em -> em.merge(buyer));
        } else {
            System.out.println("Нет такого покупателя");
        }
    }

    public void deleteById(Long id) {

        execute.executeInTransaction(em -> {

            Buyer buyer = em.find(Buyer.class, id);
            if (buyer != null) {
                em.remove(buyer);
            }
            else {
                System.out.println("Нет такого покупателя!");
            }
        });
    }

    public List<Buyer> findAll() {
        return execute.executeEntityManager(em -> em.createQuery("from Buyer", Buyer.class).getResultList());
    }

    public Buyer findOneById(Long id) {
        return execute.executeEntityManager(em -> em.find(Buyer.class, id));
    }

    public void getProductByIdBuyers(Long id) {
        Buyer buyer = execute.executeEntityManager(em -> em.find(Buyer.class, id));
        buyer.getProductList().forEach(System.out::println);
    }
}
