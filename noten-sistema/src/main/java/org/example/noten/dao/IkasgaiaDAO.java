package org.example.noten.dao;

import org.example.noten.model.Ikasgaia;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class IkasgaiaDAO {

    // Obtener todas las asignaturas de un profesor
    public List<Ikasgaia> findByIrakasle(String irakasleNan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Ikasgaia i WHERE i.irakaslea.nan = :nan",
                            Ikasgaia.class)
                    .setParameter("nan", irakasleNan)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Ikasgaia> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Ikasgaia i", Ikasgaia.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Guardar una asignatura nueva
    public void save(Ikasgaia ikasgaia) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ikasgaia);
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
            Ikasgaia ikasgaia = em.find(Ikasgaia.class, id);
            if (ikasgaia != null) {
                em.remove(ikasgaia);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Buscar por id
    public Ikasgaia findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Ikasgaia.class, id);
        } finally {
            em.close();
        }
    }
}