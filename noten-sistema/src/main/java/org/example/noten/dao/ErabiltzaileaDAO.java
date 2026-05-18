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

/**
 * DAO klasea erabiltzaileak kudeatzeko.
 * Tutoreek irakasle eta ikasle berriak sortzeko
 * eta ikasleak ikasgaietan matrikulatzeko erabiltzen da.
 */
public class ErabiltzaileaDAO {

    /**
     * NAN bat datu-basean dagoen egiaztaten du.
     *
     * @param nan Egiaztatzeko NAN zenbakia
     * @return true baldin eta NAN hori dagoeneko erregistratuta badago
     */
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

    /**
     * Irakasle berri bat sortzen du datu-basean.
     * Pasahitza BCrypt-ekin enkriptatzen du.
     *
     * @param nan           Irakaslearen NAN zenbakia
     * @param izenAbizenak  Irakaslearen izen-abizenak
     * @param tlfna         Irakaslearen telefono zenbakia
     * @param pasahitza     Irakaslearen pasahitza (enkriptatu gabe)
     */
    public void saveIrakaslea(String nan, String izenAbizenak, String tlfna, String pasahitza) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Oinarrizko erabiltzaile bat sortzen du irakasle rolarekin
            Erabiltzailea e = new Erabiltzailea();
            e.setNan(nan);
            e.setIzenAbizenak(izenAbizenak);
            e.setTlfnZenbakia(tlfna);
            e.setPasahitza(BCrypt.hashpw(pasahitza, BCrypt.gensalt(12)));
            e.setRola(Erabiltzailea.Rola.irakasle);
            e.setAktibo(true);
            em.persist(e);

            // Irakasle espezializazioa sortzen du
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

    /**
     * Ikasle berri bat sortzen du datu-basean.
     * Pasahitza BCrypt-ekin enkriptatzen du.
     *
     * @param nan           Ikaslearen NAN zenbakia
     * @param izenAbizenak  Ikaslearen izen-abizenak
     * @param tlfna         Ikaslearen telefono zenbakia
     * @param pasahitza     Ikaslearen pasahitza (enkriptatu gabe)
     * @param taldea        Ikaslearen taldea (adib: DAW2A)
     */
    public void saveIkaslea(String nan, String izenAbizenak, String tlfna, String pasahitza, String taldea) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Oinarrizko erabiltzaile bat sortzen du ikasle rolarekin
            Erabiltzailea e = new Erabiltzailea();
            e.setNan(nan);
            e.setIzenAbizenak(izenAbizenak);
            e.setTlfnZenbakia(tlfna);
            e.setPasahitza(BCrypt.hashpw(pasahitza, BCrypt.gensalt(12)));
            e.setRola(Erabiltzailea.Rola.ikasle);
            e.setAktibo(true);
            em.persist(e);

            // Ikasle espezializazioa sortzen du taldearekin
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

    /**
     * Ikasle bat ikasgai batean matrikulatzen du.
     *
     * @param ikasleNan  Ikaslearen NAN zenbakia
     * @param ikasgaiaId Ikasgaiaren ID zenbakia
     */
    public void saveMatrikula(String ikasleNan, int ikasgaiaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Matrikula berria sortzen du gaur egungo datarekin
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