/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.MMedRoom;
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
public class MMedRoomJpaController {

    public MMedRoomJpaController() {
        emf = Persistence.createEntityManagerFactory("workspacePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MMedRoom MMedRoom) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(MMedRoom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MMedRoom MMedRoom) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MMedRoom = em.merge(MMedRoom);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = MMedRoom.getId();
                if (findMMedRoom(id) == null) {
                    throw new NonexistentEntityException("The mMedRoom with id " + id + " no longer exists.");
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
            MMedRoom MMedRoom;
            try {
                MMedRoom = em.getReference(MMedRoom.class, id);
                MMedRoom.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The MMedRoom with id " + id + " no longer exists.", enfe);
            }
            em.remove(MMedRoom);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MMedRoom> findMMedRoomEntities() {
        return findMMedRoomEntities(true, -1, -1);
    }

    public List<MMedRoom> findMMedRoomEntities(int maxResults, int firstResult) {
        return findMMedRoomEntities(false, maxResults, firstResult);
    }

    private List<MMedRoom> findMMedRoomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from MMedRoom as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MMedRoom findMMedRoom(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MMedRoom.class, id);
        } finally {
            em.close();
        }
    }

    public int getMMedRoomCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from MMedRoom as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
