package org.homework.entity_manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class Execute {

    private final EntityManagerFactory EMFactory;

    public Execute(EntityManagerFactory emFactory) {
        EMFactory = emFactory;
    }

    public <R> R executeEntityManager(Function<EntityManager, R> function) {

        EntityManager em = EMFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void executeInTransaction(Consumer<EntityManager> consumer) {

        EntityManager em = EMFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
