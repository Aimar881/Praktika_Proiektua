package org.example.noten.dao;

import org.example.noten.model.Ikasgaia;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * DAO klasea ikasgaien datuak kudeatzeko.
 * Irakasleek beren ikasgaiak sortu, bilatu eta ezabatzeko erabiltzen da.
 */
public class IkasgaiaDAO {

    /**
     * Irakasle bati dagozkion ikasgai guztiak itzultzen ditu.
     *
     * @param irakasleNan Irakaslearen NAN zenbakia
     */
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

    /**
     * Datu-baseko ikasgai guztiak itzultzen ditu.
     * Tutoreek matrikulazioan erabiltzeko.
     */
    public List<Ikasgaia> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Ikasgaia i", Ikasgaia.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Ikasgai berri bat datu-basean gordetzen du.
     *
     * @param ikasgaia Gordetzeko ikasgaia
     */
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

    /**
     * Ikasgai bat datu-basetik ezabatzen du ID-aren arabera.
     * Ikasgaiari lotutako zatiak eta notak ere ezabatzen dira (CASCADE).
     *
     * @param id Ezabatzeko ikasgaiaren ID zenbakia
     */
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

    /**
     * Ikasgai bat ID-aren arabera bilatzen du.
     *
     * @param id Bilatzeko ikasgaiaren ID zenbakia
     */
    public Ikasgaia findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Ikasgaia.class, id);
        } finally {
            em.close();
        }
    }
}