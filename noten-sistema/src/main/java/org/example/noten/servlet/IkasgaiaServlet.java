package org.example.noten.servlet;

import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.dao.IkasturteaDAO;
import org.example.noten.model.Erabiltzailea;
import org.example.noten.model.Ikasgaia;
import org.example.noten.model.Irakaslea;
import org.example.noten.model.Ikasturtea;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/irakasle/ikasgaia")
public class IkasgaiaServlet extends HttpServlet {

    private IkasgaiaDAO ikasgaiaDAO = new IkasgaiaDAO();
    private IkasturteaDAO ikasturteaDAO = new IkasturteaDAO();

    // GET → mostrar formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("ikasturteak", ikasturteaDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/irakasle/ikasgaia_berria.jsp")
                .forward(request, response);
    }

    // POST → guardar asignatura
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String izena = request.getParameter("izena");
        int ikasturteId = Integer.parseInt(request.getParameter("ikasturte_id"));

        HttpSession session = request.getSession();
        Erabiltzailea erabiltzailea = (Erabiltzailea) session.getAttribute("erabiltzailea");

        EntityManager em = JPAUtil.getEntityManager();
        try {
            Irakaslea irakaslea = em.find(Irakaslea.class, erabiltzailea.getNan());
            Ikasturtea ikasturtea = em.find(Ikasturtea.class, ikasturteId);

            Ikasgaia ikasgaia = new Ikasgaia();
            ikasgaia.setIzena(izena);
            ikasgaia.setIrakaslea(irakaslea);
            ikasgaia.setIkasturtea(ikasturtea);

            em.getTransaction().begin();
            em.persist(ikasgaia);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/irakasle/dashboard");
    }
}