package org.example.noten.dao;

import org.example.noten.model.EbaluazioZatia;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * DAO klasea ebaluazio zatien datuak kudeatzeko.
 * Irakasleek ebaluazio zatiak sortu, aldatu eta ezabatzeko erabiltzen da.
 */
public class EbaluazioZatiaDAO {

    /**
     * Ikasgai baten ebaluazio zati guztiak itzultzen ditu,
     * ebaluazioaren zenbakiaren eta izenaren arabera ordenatuta.
     *
     * @param ikasgaiaId Ikasgaiaren ID zenbakia
     */
    public List<EbaluazioZatia> findByIkasgaia(int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM EbaluazioZatia e WHERE e.ikasgaia.id = :id " +
                                    "ORDER BY e.ebaluazioZenbakia, e.izena",
                            EbaluazioZatia.class)
                    .setParameter("id", ikasgaiaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Ebaluazio zati berri bat datu-basean gordetzen du.
     *
     * @param zatia Gordetzeko ebaluazio zatia
     */
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

    /**
     * Ebaluazio zati bat datu-basetik ezabatzen du ID-aren arabera.
     *
     * @param id Ezabatzeko ebaluazio zatiaren ID zenbakia
     */
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

    /**
     * Ebaluazio zati bat ID-aren arabera bilatzen du.
     *
     * @param id Bilatzeko ebaluazio zatiaren ID zenbakia
     */
    public EbaluazioZatia findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(EbaluazioZatia.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Ebaluazio baten pisuen batura itzultzen du,
     * zati jakin bat kenduta (editatzean erabiltzeko).
     *
     * @param ikasgaiaId      Ikasgaiaren ID zenbakia
     * @param ebaluazioZenbakia Ebaluazioaren zenbakia (1, 2 edo 3)
     * @param excludeId       Kontuan ez hartzeko zatiaren ID zenbakia
     */
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

    /**
     * Ebaluazio baten pisuen batura osoa itzultzen du.
     * Zati berri bat gehitzean %100 ez gainditzeko erabiltzen da.
     *
     * @param ikasgaiaId        Ikasgaiaren ID zenbakia
     * @param ebaluazioZenbakia Ebaluazioaren zenbakia (1, 2 edo 3)
     */
    public double getSumaPisuak(int ikasgaiaId, int ebaluazioZenbakia) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Double suma = em.createQuery(
                            "SELECT COALESCE(SUM(e.pisua), 0) FROM EbaluazioZatia e " +
                                    "WHERE e.ikasgaia.id = :ikasgaiaId " +
                                    "AND e.ebaluazioZenbakia = :ebaluazioa",
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