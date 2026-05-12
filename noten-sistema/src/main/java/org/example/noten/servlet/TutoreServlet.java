package org.example.noten.servlet;

import org.example.noten.dao.BuletinaDAO;
import org.example.noten.model.Ikaslea;
import org.example.noten.model.Nota;
import org.example.noten.service.NotaKalkulagailua;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/tutore/dashboard")
public class TutoreServlet extends HttpServlet {

    private BuletinaDAO buletinaDAO = new BuletinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Ikaslea> ikasleak = buletinaDAO.findAllIkasleak();
        request.setAttribute("ikasleak", ikasleak);

        String ikasleNan = request.getParameter("ikasleNan");
        if (ikasleNan != null && !ikasleNan.isEmpty()) {
            List<Nota> notak = buletinaDAO.findNotakByIkaslea(ikasleNan);

            Map<String, Double> ebaluazioNotak = NotaKalkulagailua.kalkulatuEbaluazioNotak(notak);
            Map<Integer, Double> notaFinalak   = NotaKalkulagailua.kalkulatuNotaFinalak(ebaluazioNotak);

            request.setAttribute("notak", notak);
            request.setAttribute("ebaluazioNotak", ebaluazioNotak);
            request.setAttribute("notaFinalak", notaFinalak);
            request.setAttribute("ikasleNanSelektatua", ikasleNan);
        }

        request.getRequestDispatcher("/WEB-INF/views/tutore/dashboard.jsp")
                .forward(request, response);
    }
}