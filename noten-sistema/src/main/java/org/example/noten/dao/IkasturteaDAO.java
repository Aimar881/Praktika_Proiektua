package org.example.noten.dao;

import org.example.noten.model.Ikasturtea;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class IkasturteaDAO {

    // Obtener todos los cursos
    public List<Ikasturtea> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Ikasturtea i ORDER BY i.hasiera DESC",
                            Ikasturtea.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Obtener el curso activo
    public Ikasturtea findActivo() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Ikasturtea i WHERE i.aktibo = true",
                            Ikasturtea.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // Guardar curso nuevo
    public void save(Ikasturtea ikasturtea) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ikasturtea);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}