package org.example.noten.dao;

import org.example.noten.model.Nota;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class NotaIkasleaDAO {

    public List<Nota> findByIkaslea(String ikasleNan) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM Nota n WHERE n.ikaslea.nan = :nan ORDER BY n.ebaluazioZatia.ikasgaia.izena, n.ebaluazioZatia.ebaluazioZenbakia",
                            Nota.class)
                    .setParameter("nan", ikasleNan)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}