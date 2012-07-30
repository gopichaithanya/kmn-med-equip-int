/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedDoctor;
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
public class MMedDoctorJpaController {
    private EntityManagerFactory emf = null;

    public MMedDoctorJpaController() {
        //emf = Persistence.createEntityManagerFactory("workspacePU");
        emf = DBManagerFactory.getInstance().getEntityManager();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedDoctor MMedDoctor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedDoctor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedDoctor MMedDoctor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedDoctor = em.merge(MMedDoctor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedDoctor.getId();
                if (findMMedDoctor(id) == null) {
                    throw new NonexistentEntityException("The mMedDoctor with id " + id + " no longer exists.");
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
            MMedDoctor MMedDoctor;
            try {
                MMedDoctor = em.getReference(MMedDoctor.class, id);
                MMedDoctor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedDoctor with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedDoctor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedDoctor> findMMedDoctorEntities() {
        return findMMedDoctorEntities(true, -1, -1);
    }

    public List<MMedDoctor> findMMedDoctorEntities(int maxResults, int firstResult) {
        return findMMedDoctorEntities(false, maxResults, firstResult);
    }

    private List<MMedDoctor> findMMedDoctorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedDoctor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedDoctor findMMedDoctor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedDoctor.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedDoctorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedDoctor as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
