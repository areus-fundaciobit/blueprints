package org.fundaciobit.blueprint.ejb.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.List;
import java.util.logging.Logger;

public class ItemListener {

   @Inject
   private Logger logger;

   @PersistenceContext
   protected EntityManager entityManager;

   @PrePersist
   public void prePersist(Item item) {
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      List result = entityManager.createNamedQuery("findByNIF", Item.class)
            .setParameter("nif", item.getNif())
            .getResultList();

      if (!result.isEmpty()) {
         String msg = "prePersist: " + item.getNif() + " ja existeix, ID=" + item.getId();
         logger.warning(msg);
         throw new PersistenceException(msg);
      }
   }

   @PreUpdate
   public void preUpdate(Item item) {
      // Sempre guardam el NIF amb la lletra majuscula
      item.setNif(item.getNif().toUpperCase());

      // TODO Comprovar que el NIF que es va guardar no està associat amb un altre id
   }

}
