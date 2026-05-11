package org.example.noten.servlet;

import org.example.noten.dao.EbaluazioZatiaDAO;
import org.example.noten.dao.IkasgaiaDAO;
import org.example.noten.dao.IkasleaDAO;
import org.example.noten.dao.NotaDAO;
import org.example.noten.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/irakasle/notak")
public class NotaServlet extends HttpServlet {

    private NotaDAO notaDAO = new NotaDAO();
    private IkasgaiaDAO ikasgaiaDAO = new IkasgaiaDAO();
    private IkasleaDAO ikasleaDAO = new IkasleaDAO();
    private EbaluazioZatiaDAO zatiaDAO = new EbaluazioZatiaDAO();

    // GET → mostrar formulario de notas
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ikasgaiaId = Integer.parseInt(request.getParameter("ikasgaiaId"));

        Ikasgaia ikasgaia = ikasgaiaDAO.findById(ikasgaiaId);
        List<Ikaslea> ikasleak = ikasleaDAO.findByIkasgaia(ikasgaiaId);
        List<EbaluazioZatia> zatiak = zatiaDAO.findByIkasgaia(ikasgaiaId);
        List<Nota> notak = notaDAO.findByIkasgaia(ikasgaiaId);

        request.setAttribute("ikasgaia", ikasgaia);
        request.setAttribute("ikasleak", ikasleak);
        request.setAttribute("zatiak", zatiak);
        request.setAttribute("notak", notak);

        request.getRequestDispatcher("/WEB-INF/views/irakasle/notak.jsp")
                .forward(request, response);
    }

    // POST → guardar nota
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int ikasgaiaId  = Integer.parseInt(request.getParameter("ikasgaiaId"));
        String ikasleNan = request.getParameter("ikasleNan");
        int zatiId      = Integer.parseInt(request.getParameter("zatiId"));
        double nota     = Double.parseDouble(request.getParameter("nota"));

        Ikaslea ikaslea = ikasleaDAO.findByNan(ikasleNan);
        EbaluazioZatia zatia = zatiaDAO.findById(zatiId);

        Nota notaObj = new Nota();
        notaObj.setId(new NotaId(ikasleNan, zatiId));
        notaObj.setIkaslea(ikaslea);
        notaObj.setEbaluazioZatia(zatia);
        notaObj.setNotaZenbakia(nota);

        notaDAO.saveOrUpdate(notaObj);

        response.sendRedirect(request.getContextPath() + "/irakasle/notak?ikasgaiaId=" + ikasgaiaId);
    }
}