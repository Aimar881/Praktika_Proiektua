package org.example.noten.servlet;

import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.model.Erabiltzailea;
import org.example.noten.model.Ikasgaia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/irakasle/dashboard")
public class IrakasleServlet extends HttpServlet {

    private IkasgaiaDAO ikasgaiaDAO = new IkasgaiaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Erabiltzailea erabiltzailea = (Erabiltzailea) session.getAttribute("erabiltzailea");

        List<Ikasgaia> ikasgaiak = ikasgaiaDAO.findByIrakasle(erabiltzailea.getNan());
        request.setAttribute("ikasgaiak", ikasgaiak);

        request.getRequestDispatcher("/WEB-INF/views/irakasle/dashboard.jsp")
                .forward(request, response);
    }
}