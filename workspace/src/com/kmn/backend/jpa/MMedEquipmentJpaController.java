/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedEquipment;
import com.kmn.backend.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Hermanto
 */
public class MMedEquipmentJpaController {

    public MMedEquipmentJpaController() {
        emf = Persistence.createEntityManagerFactory("workspacePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedEquipment MMedEquipment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedEquipment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedEquipment MMedEquipment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedEquipment = em.merge(MMedEquipment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedEquipment.getId();
                if (findMMedEquipment(id) == null) {
                    throw new NonexistentEntityException("The mMedEquipment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedEquipment MMedEquipment;
            try {
                MMedEquipment = em.getReference(MMedEquipment.class, id);
                MMedEquipment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedEquipment with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedEquipment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedEquipment> findMMedEquipmentEntities() {
        return findMMedEquipmentEntities(true, -1, -1);
    }

    public List<MMedEquipment> findMMedEquipmentEntities(int maxResults, int firstResult) {
        return findMMedEquipmentEntities(false, maxResults, firstResult);
    }

    private List<MMedEquipment> findMMedEquipmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedEquipment as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedEquipment findMMedEquipment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedEquipment.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedEquipmentCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedEquipment as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
