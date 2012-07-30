/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.jpa;

import com.kmn.backend.entity.SecUser;
import com.kmn.backend.jpa.exceptions.NonexistentEntityException;
import com.kmn.backend.jpa.exceptions.PreexistingEntityException;
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
public class SecUserJpaController {

    public SecUserJpaController() {
        //emf = Persistence.createEntityManagerFactory("workspacePU");
        emf = DBManagerFactory.getInstance().getEntityManager();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SecUser secUser) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(secUser);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecUser(secUser.getUsrId()) != null) {
                throw new PreexistingEntityException("SecUser " + secUser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SecUser secUser) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            secUser = em.merge(secUser);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secUser.getUsrId();
                if (findSecUser(id) == null) {
                    throw new NonexistentEntityException("The secUser with id " + id + " no longer exists.");
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
            SecUser secUser;
            try {
                secUser = em.getReference(SecUser.class, id);
                secUser.getUsrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secUser with id " + id + " no longer exists.", enfe);
            }
            em.remove(secUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SecUser> findSecUserEntities() {
        return findSecUserEntities(true, -1, -1);
    }

    public List<SecUser> findSecUserEntities(int maxResults, int firstResult) {
        return findSecUserEntities(false, maxResults, firstResult);
    }

    private List<SecUser> findSecUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SecUser as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SecUser findByUserName(String username) {
        EntityManager em = getEntityManager();
        SecUser sec = null;
        try {
            Query q = em.createQuery("select object(o) from SecUser as o where usr_loginname=:username");
            q.setParameter("username", username);
            if(q.getResultList().size() > 0) sec = (SecUser) ((List) q.getResultList()).get(0);
        }
        finally {
            em.close();
        }
        return sec;
    }

    public SecUser findSecUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SecUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecUserCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SecUser as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
