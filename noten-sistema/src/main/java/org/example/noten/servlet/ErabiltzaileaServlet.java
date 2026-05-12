package org.example.noten.servlet;

import org.example.noten.dao.ErabiltzaileaDAO;
import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.dao.IkasleaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tutore/usuarios")
public class ErabiltzaileaServlet extends HttpServlet {

    private ErabiltzaileaDAO erabiltzaileaDAO = new ErabiltzaileaDAO();
    private IkasgaiaDAO ikasgaiaDAO = new IkasgaiaDAO();
    private IkasleaDAO ikasleaDAO = new IkasleaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("ikasgaiak", ikasgaiaDAO.findAll());
        request.setAttribute("ikasleak", ikasleaDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/tutore/usuarios.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getParameter("_method");

        if ("IRAKASLE".equals(method)) {
            String nan          = request.getParameter("nan");
            String izenAbizenak = request.getParameter("izenAbizenak");
            String tlfna        = request.getParameter("tlfna");
            String pasahitza    = request.getParameter("pasahitza");

            if (erabiltzaileaDAO.existeNan(nan)) {
                request.setAttribute("errorea", "NAN hori dagoeneko erregistratuta dago.");
                request.setAttribute("ikasgaiak", ikasgaiaDAO.findAll());
                request.setAttribute("ikasleak", ikasleaDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/tutore/usuarios.jsp")
                        .forward(request, response);
                return;
            }

            erabiltzaileaDAO.saveIrakaslea(nan, izenAbizenak, tlfna, pasahitza);
            response.sendRedirect(request.getContextPath() + "/tutore/usuarios?success=irakasle");
            return;
        }

        if ("IKASLE".equals(method)) {
            String nan          = request.getParameter("nan");
            String izenAbizenak = request.getParameter("izenAbizenak");
            String tlfna        = request.getParameter("tlfna");
            String pasahitza    = request.getParameter("pasahitza");
            String taldea       = request.getParameter("taldea");

            if (erabiltzaileaDAO.existeNan(nan)) {
                request.setAttribute("errorea", "NAN hori dagoeneko erregistratuta dago.");
                request.setAttribute("ikasgaiak", ikasgaiaDAO.findAll());
                request.setAttribute("ikasleak", ikasleaDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/tutore/usuarios.jsp")
                        .forward(request, response);
                return;
            }

            erabiltzaileaDAO.saveIkaslea(nan, izenAbizenak, tlfna, pasahitza, taldea);
            response.sendRedirect(request.getContextPath() + "/tutore/usuarios?success=ikasle");
            return;
        }

        if ("MATRIKULA".equals(method)) {
            String ikasleNan = request.getParameter("ikasleNan");
            int ikasgaiaId   = Integer.parseInt(request.getParameter("ikasgaiaId"));
            erabiltzaileaDAO.saveMatrikula(ikasleNan, ikasgaiaId);
            response.sendRedirect(request.getContextPath() + "/tutore/usuarios?success=matrikula");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/tutore/usuarios");
    }
}