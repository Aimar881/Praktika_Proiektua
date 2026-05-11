package org.example.noten.dao;

import org.example.noten.model.Ikaslea;
import org.example.noten.model.Nota;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class BuletinaDAO {

    // Todos los alumnos
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

    // Notas de un alumno concreto
    public List<Nota> findNotakByIkaslea(String ikasleNan) {
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