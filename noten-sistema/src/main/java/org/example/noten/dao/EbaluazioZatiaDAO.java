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

    public void delete(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            EbaluazioZatia zatia = em.find(EbaluazioZatia.class, id);
            if (zatia != null) {
                em.remove(zatia);
            }
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

    public double getSumaPisuakSinId(int ikasgaiaId, int ebaluazioZenbakia, int excludeId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Double suma = em.createQuery(
                            "SELECT COALESCE(SUM(e.pisua), 0) FROM EbaluazioZatia e " +
                                    "WHERE e.ikasgaia.id = :ikasgaiaId " +
                                    "AND e.ebaluazioZenbakia = :ebaluazioa " +
                                    "AND e.id != :excludeId",
                            Double.class)
                    .setParameter("ikasgaiaId", ikasgaiaId)
                    .setParameter("ebaluazioa", ebaluazioZenbakia)
                    .setParameter("excludeId", excludeId)
                    .getSingleResult();
            return suma;
        } finally {
            em.close();
        }
    }

    public double getSumaPisuak(int ikasgaiaId, int ebaluazioZenbakia) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Double suma = em.createQuery(
                            "SELECT COALESCE(SUM(e.pisua), 0) FROM EbaluazioZatia e WHERE e.ikasgaia.id = :ikasgaiaId AND e.ebaluazioZenbakia = :ebaluazioa",
                            Double.class)
                    .setParameter("ikasgaiaId", ikasgaiaId)
                    .setParameter("ebaluazioa", ebaluazioZenbakia)
                    .getSingleResult();
            return suma;
        } finally {
            em.close();
        }
    }
}