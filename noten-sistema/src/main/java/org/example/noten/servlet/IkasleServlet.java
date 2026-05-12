package org.example.noten.servlet;

import org.example.noten.dao.NotaIkasleaDAO;
import org.example.noten.model.Erabiltzailea;
import org.example.noten.model.Nota;
import org.example.noten.service.NotaKalkulagailua;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/ikasle/dashboard")
public class IkasleServlet extends HttpServlet {

    private NotaIkasleaDAO notaDAO = new NotaIkasleaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Erabiltzailea erabiltzailea = (Erabiltzailea) session.getAttribute("erabiltzailea");

        List<Nota> notak = notaDAO.findByIkaslea(erabiltzailea.getNan());

        // Calcular notas ponderadas por evaluación y nota final
        Map<String, Double> ebaluazioNotak = NotaKalkulagailua.kalkulatuEbaluazioNotak(notak);
        Map<Integer, Double> notaFinalak   = NotaKalkulagailua.kalkulatuNotaFinalak(ebaluazioNotak);

        request.setAttribute("notak", notak);
        request.setAttribute("ebaluazioNotak", ebaluazioNotak);
        request.setAttribute("notaFinalak", notaFinalak);

        request.getRequestDispatcher("/WEB-INF/views/ikasle/dashboard.jsp")
                .forward(request, response);
    }
}