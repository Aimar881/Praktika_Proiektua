package org.example.noten.servlet;

import org.example.noten.model.Erabiltzailea;
import org.example.noten.util.JPAUtil;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // GET → mostrar la página de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // POST → procesar el formulario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nan       = request.getParameter("nan");
        String pasahitza = request.getParameter("pasahitza");

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Buscar usuario por NAN
            TypedQuery<Erabiltzailea> query = em.createQuery(
                    "SELECT e FROM Erabiltzailea e WHERE e.nan = :nan",
                    Erabiltzailea.class
            );
            query.setParameter("nan", nan);
            Erabiltzailea erabiltzailea = query.getSingleResult();

            // Comprobar contraseña con BCrypt
            if (BCrypt.checkpw(pasahitza, erabiltzailea.getPasahitza())) {
                // Contraseña correcta → guardar en sesión
                HttpSession session = request.getSession();
                session.setAttribute("erabiltzailea", erabiltzailea);
                session.setAttribute("rola", erabiltzailea.getRola().toString());

                // Redirigir según rol
                switch (erabiltzailea.getRola()) {
                    case irakasle:
                        response.sendRedirect(request.getContextPath() + "/irakasle/dashboard");
                        break;
                    case ikasle:
                        response.sendRedirect(request.getContextPath() + "/ikasle/dashboard");
                        break;
                    case tutore:
                        response.sendRedirect(request.getContextPath() + "/tutore/dashboard");
                        break;
                }
            } else {
                // Contraseña incorrecta
                request.setAttribute("errorMezua", "NAN edo pasahitza okerra da.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch (NoResultException e) {
            // Usuario no encontrado
            request.setAttribute("errorMezua", "NAN edo pasahitza okerra da.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}