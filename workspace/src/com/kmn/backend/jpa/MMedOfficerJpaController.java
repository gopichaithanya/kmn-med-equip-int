/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedOfficer;
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
public class MMedOfficerJpaController {

    public MMedOfficerJpaController() {
        emf = Persistence.createEntityManagerFactory("workspacePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedOfficer MMedOfficer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedOfficer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedOfficer MMedOfficer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedOfficer = em.merge(MMedOfficer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedOfficer.getId();
                if (findMMedOfficer(id) == null) {
                    throw new NonexistentEntityException("The mMedOfficer with id " + id + " no longer exists.");
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
            MMedOfficer MMedOfficer;
            try {
                MMedOfficer = em.getReference(MMedOfficer.class, id);
                MMedOfficer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedOfficer with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedOfficer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedOfficer> findMMedOfficerEntities() {
        return findMMedOfficerEntities(true, -1, -1);
    }

    public List<MMedOfficer> findMMedOfficerEntities(int maxResults, int firstResult) {
        return findMMedOfficerEntities(false, maxResults, firstResult);
    }

    private List<MMedOfficer> findMMedOfficerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedOfficer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedOfficer findMMedOfficer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedOfficer.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedOfficerCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedOfficer as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
