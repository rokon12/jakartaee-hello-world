package org.eclipse.jakarta.model;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.jakarta.model.entity.Coffee;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class CafeRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager em;

    public Coffee create(Coffee coffee) {
        logger.info("Creating coffee " + coffee.getName());
        em.persist(coffee);

        return coffee;
    }

    public List<Coffee> findAll() {
        logger.info("Getting all coffee");
        return em.createQuery("SELECT c FROM Coffee c", Coffee.class).getResultList();
    }

    public Optional<Coffee> findById(Long id) {
        logger.info("Getting coffee by id " + id);
        return Optional.ofNullable(em.find(Coffee.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting coffee by id " + id);
        var coffee = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coffee Id:" + id));
        em.remove(coffee);
    }

    public Coffee update(Coffee coffee) {
        logger.info("Updating coffee " + coffee.getName());
       return em.merge(coffee);
    }
}
