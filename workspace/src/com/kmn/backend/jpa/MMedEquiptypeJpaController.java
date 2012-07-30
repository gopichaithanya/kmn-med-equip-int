/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedEquiptype;
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
public class MMedEquiptypeJpaController {

    public MMedEquiptypeJpaController() {
        //emf = Persistence.createEntityManagerFactory("workspacePU");
        emf = DBManagerFactory.getInstance().getEntityManager();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedEquiptype MMedEquiptype) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedEquiptype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedEquiptype MMedEquiptype) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedEquiptype = em.merge(MMedEquiptype);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedEquiptype.getId();
                if (findMMedEquiptype(id) == null) {
                    throw new NonexistentEntityException("The mMedEquiptype with id " + id + " no longer exists.");
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
            MMedEquiptype MMedEquiptype;
            try {
                MMedEquiptype = em.getReference(MMedEquiptype.class, id);
                MMedEquiptype.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedEquiptype with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedEquiptype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedEquiptype> findMMedEquiptypeEntities() {
        return findMMedEquiptypeEntities(true, -1, -1);
    }

    public List<MMedEquiptype> findMMedEquiptypeEntities(int maxResults, int firstResult) {
        return findMMedEquiptypeEntities(false, maxResults, firstResult);
    }

    private List<MMedEquiptype> findMMedEquiptypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedEquiptype as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedEquiptype findMMedEquiptype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedEquiptype.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedEquiptypeCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedEquiptype as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
