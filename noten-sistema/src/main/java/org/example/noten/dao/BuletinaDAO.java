package org.example.noten.dao;

import org.example.noten.model.Ikaslea;
import org.example.noten.model.Nota;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * DAO klasea buletinen datuak eskuratzeko.
 * Tutoreek ikasleen notak ikusteko eta buletina sortzeko erabiltzen da.
 */
public class BuletinaDAO {

    /**
     * Datu-baseko ikasle guztiak itzultzen ditu,
     * izen-abizenen arabera ordenatuta.
     */
    public List<Ikaslea> findAllIkasleak() {
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

    /**
     * Ikasle baten nota guztiak itzultzen ditu,
     * ikasgaiaren izenaren eta ebaluazioaren arabera ordenatuta.
     *
     * @param ikasleNan Ikaslearen NAN zenbakia
     */
    public List<Nota> findNotakByIkaslea(String ikasleNan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM Nota n WHERE n.ikaslea.nan = :nan " +
                                    "ORDER BY n.ebaluazioZatia.ikasgaia.izena, n.ebaluazioZatia.ebaluazioZenbakia",
                            Nota.class)
                    .setParameter("nan", ikasleNan)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}