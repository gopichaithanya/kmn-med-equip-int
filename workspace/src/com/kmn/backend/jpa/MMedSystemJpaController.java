/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedSystem;
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
public class MMedSystemJpaController {

    public MMedSystemJpaController() {
        //emf = Persistence.createEntityManagerFactory("workspacePU");
        emf = DBManagerFactory.getInstance().getEntityManager();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedSystem MMedSystem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedSystem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedSystem MMedSystem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedSystem = em.merge(MMedSystem);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedSystem.getId();
                if (findMMedSystem(id) == null) {
                    throw new NonexistentEntityException("The mMedSystem with id " + id + " no longer exists.");
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
            MMedSystem MMedSystem;
            try {
                MMedSystem = em.getReference(MMedSystem.class, id);
                MMedSystem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedSystem with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedSystem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedSystem> findMMedSystemEntities() {
        return findMMedSystemEntities(true, -1, -1);
    }

    public List<MMedSystem> findMMedSystemEntities(int maxResults, int firstResult) {
        return findMMedSystemEntities(false, maxResults, firstResult);
    }

    private List<MMedSystem> findMMedSystemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedSystem as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedSystem findMMedSystem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedSystem.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedSystemCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedSystem as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
