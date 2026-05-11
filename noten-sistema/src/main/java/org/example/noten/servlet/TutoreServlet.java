package org.example.noten.servlet;

import org.example.noten.dao.BuletinaDAO;
import org.example.noten.model.Ikaslea;
import org.example.noten.model.Nota;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tutore/dashboard")
public class TutoreServlet extends HttpServlet {

    private BuletinaDAO buletinaDAO = new BuletinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Ikaslea> ikasleak = buletinaDAO.findAllIkasleak();
        request.setAttribute("ikasleak", ikasleak);

        // Si se selecciona un alumno concreto
        String ikasleNan = request.getParameter("ikasleNan");
        if (ikasleNan != null && !ikasleNan.isEmpty()) {
            List<Nota> notak = buletinaDAO.findNotakByIkaslea(ikasleNan);
            request.setAttribute("notak", notak);
            request.setAttribute("ikasleNanSelektatua", ikasleNan);
        }

        request.getRequestDispatcher("/WEB-INF/views/tutore/dashboard.jsp")
                .forward(request, response);
    }
}