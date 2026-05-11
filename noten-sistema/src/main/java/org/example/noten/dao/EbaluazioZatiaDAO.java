package org.example.noten.dao;

import org.example.noten.model.EbaluazioZatia;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class EbaluazioZatiaDAO {

    public List<EbaluazioZatia> findByIkasgaia(int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM EbaluazioZatia e WHERE e.ikasgaia.id = :id ORDER BY e.ebaluazioZenbakia, e.izena",
                            EbaluazioZatia.class)
                    .setParameter("id", ikasgaiaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void save(EbaluazioZatia zatia) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(zatia);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public EbaluazioZatia findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(EbaluazioZatia.class, id);
        } finally {
            em.close();
        }
    }
}