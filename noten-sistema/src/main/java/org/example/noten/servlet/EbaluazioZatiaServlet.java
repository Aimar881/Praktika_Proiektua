package org.example.noten.servlet;

import org.example.noten.dao.EbaluazioZatiaDAO;
import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.model.EbaluazioZatia;
import org.example.noten.model.Ikasgaia;

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

    // GET → mostrar partes de una asignatura
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

    // POST → guardar parte nueva
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ikasgaiaId    = Integer.parseInt(request.getParameter("ikasgaiaId"));
        String izena      = request.getParameter("izena");
        double pisua      = Double.parseDouble(request.getParameter("pisua"));
        int ebaluazioZenb = Integer.parseInt(request.getParameter("ebaluazioZenbakia"));

        Ikasgaia ikasgaia = ikasgaiaDAO.findById(ikasgaiaId);

        EbaluazioZatia zatia = new EbaluazioZatia();
        zatia.setIkasgaia(ikasgaia);
        zatia.setIzena(izena);
        zatia.setPisua(pisua);
        zatia.setEbaluazioZenbakia(ebaluazioZenb);

        zatiaDAO.save(zatia);

        response.sendRedirect(request.getContextPath() + "/irakasle/zatiak?ikasgaiaId=" + ikasgaiaId);
    }
}