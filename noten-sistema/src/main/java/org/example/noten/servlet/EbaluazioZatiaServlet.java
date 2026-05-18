package org.example.noten.servlet;

import org.example.noten.dao.EbaluazioZatiaDAO;
import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.model.EbaluazioZatia;
import org.example.noten.model.Ikasgaia;
import org.example.noten.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/irakasle/zatiak")
public class EbaluazioZatiaServlet extends HttpServlet {

    private EbaluazioZatiaDAO zatiaDAO = new EbaluazioZatiaDAO();
    private IkasgaiaDAO ikasgaiaDAO = new IkasgaiaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ikasgaiaId = Integer.parseInt(request.getParameter("ikasgaiaId"));
        Ikasgaia ikasgaia = ikasgaiaDAO.findById(ikasgaiaId);

        request.setAttribute("ikasgaia", ikasgaia);
        request.setAttribute("zatiak", zatiaDAO.findByIkasgaia(ikasgaiaId));
        request.getRequestDispatcher("/WEB-INF/views/irakasle/zatiak.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getParameter("_method");

        if ("DELETE".equals(method)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int ikasgaiaId = Integer.parseInt(request.getParameter("ikasgaiaId"));
            zatiaDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/irakasle/zatiak?ikasgaiaId=" + ikasgaiaId);
            return;
        }

        if ("EDIT".equals(method)) {
            int id         = Integer.parseInt(request.getParameter("id"));
            int ikasgaiaId = Integer.parseInt(request.getParameter("ikasgaiaId"));
            String izena   = request.getParameter("izena");
            double pisua   = Double.parseDouble(request.getParameter("pisua"));
            int ebaluazioZenb = Integer.parseInt(request.getParameter("ebaluazioZenbakia"));

            // Suma actual SIN contar el zatia que estamos editando
            double sumaAktual = zatiaDAO.getSumaPisuakSinId(ikasgaiaId, ebaluazioZenb, id);

            if (sumaAktual + pisua > 100) {
                Ikasgaia ikasgaia = ikasgaiaDAO.findById(ikasgaiaId);
                request.setAttribute("erroreak", java.util.Collections.singleton(
                        ebaluazioZenb + ". ebaluazioak dagoeneko %" + sumaAktual + " dauka (zati honek kenduta). Ezin da %" + pisua + " jarri."
                ));
                request.setAttribute("ikasgaia", ikasgaia);
                request.setAttribute("zatiak", zatiaDAO.findByIkasgaia(ikasgaiaId));
                request.getRequestDispatcher("/WEB-INF/views/irakasle/zatiak.jsp")
                        .forward(request, response);
                return;
            }

            EntityManager em = JPAUtil.getEntityManager();
            try {
                em.getTransaction().begin();
                EbaluazioZatia zatia = em.find(EbaluazioZatia.class, id);
                zatia.setIzena(izena);
                zatia.setPisua(pisua);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            response.sendRedirect(request.getContextPath() + "/irakasle/zatiak?ikasgaiaId=" + ikasgaiaId);
            return;
        }

        int ikasgaiaId    = Integer.parseInt(request.getParameter("ikasgaiaId"));
        String izena      = request.getParameter("izena");
        double pisua      = Double.parseDouble(request.getParameter("pisua"));
        int ebaluazioZenb = Integer.parseInt(request.getParameter("ebaluazioZenbakia"));

        Ikasgaia ikasgaia = ikasgaiaDAO.findById(ikasgaiaId);

        String errorea = null;
        if (izena == null || izena.trim().isEmpty()) {
            errorea = "Izena ezin da hutsik egon";
        } else if (pisua < 1 || pisua > 100) {
            errorea = "Pisuak 1 eta 100 artean egon behar du";
        } else {
            double sumaAktual = zatiaDAO.getSumaPisuak(ikasgaiaId, ebaluazioZenb);
            if (sumaAktual + pisua > 100) {
                errorea = ebaluazioZenb + ". ebaluazioak dagoeneko %" + sumaAktual + " dauka. Ezin da %" + pisua + " gehitu (%" + (sumaAktual + pisua) + " izango litzateke)";
            }
        }

        if (errorea != null) {
            request.setAttribute("erroreak", java.util.Collections.singleton(errorea));
            request.setAttribute("ikasgaia", ikasgaia);
            request.setAttribute("zatiak", zatiaDAO.findByIkasgaia(ikasgaiaId));
            request.getRequestDispatcher("/WEB-INF/views/irakasle/zatiak.jsp")
                    .forward(request, response);
            return;
        }

        EbaluazioZatia zatia = new EbaluazioZatia();
        zatia.setIkasgaia(ikasgaia);
        zatia.setIzena(izena);
        zatia.setPisua(pisua);
        zatia.setEbaluazioZenbakia(ebaluazioZenb);

        zatiaDAO.save(zatia);
        response.sendRedirect(request.getContextPath() + "/irakasle/zatiak?ikasgaiaId=" + ikasgaiaId);
    }
}