package org.example.noten.dao;
import org.example.noten.model.Ikasgaia;

import org.example.noten.model.Erabiltzailea;
import org.example.noten.model.Irakaslea;
import org.example.noten.model.Ikaslea;
import org.example.noten.model.Matrikula;
import org.example.noten.model.MatrikulaId;
import org.example.noten.util.JPAUtil;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ErabiltzaileaDAO {

    public boolean existeNan(String nan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM Erabiltzailea e WHERE e.nan = :nan", Long.class)
                    .setParameter("nan", nan)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public void saveIrakaslea(String nan, String izenAbizenak, String tlfna, String pasahitza) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Erabiltzailea e = new Erabiltzailea();
            e.setNan(nan);
            e.setIzenAbizenak(izenAbizenak);
            e.setTlfnZenbakia(tlfna);
            e.setPasahitza(BCrypt.hashpw(pasahitza, BCrypt.gensalt(12)));
            e.setRola(Erabiltzailea.Rola.irakasle);
            e.setAktibo(true);
            em.persist(e);

            Irakaslea i = new Irakaslea();
            i.setNan(nan);
            i.setErabiltzailea(e);
            em.persist(i);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void saveIkaslea(String nan, String izenAbizenak, String tlfna, String pasahitza, String taldea) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Erabiltzailea e = new Erabiltzailea();
            e.setNan(nan);
            e.setIzenAbizenak(izenAbizenak);
            e.setTlfnZenbakia(tlfna);
            e.setPasahitza(BCrypt.hashpw(pasahitza, BCrypt.gensalt(12)));
            e.setRola(Erabiltzailea.Rola.ikasle);
            e.setAktibo(true);
            em.persist(e);

            Ikaslea ik = new Ikaslea();
            ik.setNan(nan);
            ik.setErabiltzailea(e);
            ik.setTaldea(taldea);
            em.persist(ik);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void saveMatrikula(String ikasleNan, int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Matrikula m = new Matrikula();
            m.setId(new MatrikulaId(ikasleNan, ikasgaiaId));
            m.setIkaslea(em.find(Ikaslea.class, ikasleNan));
            m.setIkasgaia(em.find(Ikasgaia.class, ikasgaiaId));
            m.setMatrikulaData(LocalDate.now());
            em.persist(m);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}