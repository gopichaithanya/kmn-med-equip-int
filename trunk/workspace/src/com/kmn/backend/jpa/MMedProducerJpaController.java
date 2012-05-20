/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedProducer;
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
public class MMedProducerJpaController {

    public MMedProducerJpaController() {
        emf = Persistence.createEntityManagerFactory("workspacePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedProducer MMedProducer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedProducer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedProducer MMedProducer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedProducer = em.merge(MMedProducer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedProducer.getId();
                if (findMMedProducer(id) == null) {
                    throw new NonexistentEntityException("The mMedProducer with id " + id + " no longer exists.");
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
            MMedProducer MMedProducer;
            try {
                MMedProducer = em.getReference(MMedProducer.class, id);
                MMedProducer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedProducer with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedProducer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedProducer> findMMedProducerEntities() {
        return findMMedProducerEntities(true, -1, -1);
    }

    public List<MMedProducer> findMMedProducerEntities(int maxResults, int firstResult) {
        return findMMedProducerEntities(false, maxResults, firstResult);
    }

    private List<MMedProducer> findMMedProducerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedProducer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedProducer findMMedProducer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedProducer.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedProducerCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedProducer as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
