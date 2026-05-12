package org.example.noten.dao;

import org.example.noten.model.Ikaslea;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class IkasleaDAO {

    public List<Ikaslea> findByIkasgaia(int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT m.ikaslea FROM Matrikula m WHERE m.ikasgaia.id = :id",
                            Ikaslea.class)
                    .setParameter("id", ikasgaiaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Ikaslea> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Ikaslea i ORDER BY i.erabiltzailea.izenAbizenak",
                            Ikaslea.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Ikaslea findByNan(String nan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Ikaslea.class, nan);
        } finally {
            em.close();
        }
    }
}