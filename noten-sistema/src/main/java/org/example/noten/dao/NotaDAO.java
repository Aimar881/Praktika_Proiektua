package org.example.noten.dao;

import org.example.noten.model.Nota;
import org.example.noten.model.NotaId;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class NotaDAO {

    public List<Nota> findByIkasgaia(int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM Nota n WHERE n.ebaluazioZatia.ikasgaia.id = :id",
                            Nota.class)
                    .setParameter("id", ikasgaiaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Nota findById(String ikasleNan, int zatiId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Nota.class, new NotaId(ikasleNan, zatiId));
        } finally {
            em.close();
        }
    }

    public void saveOrUpdate(Nota nota) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(nota);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}