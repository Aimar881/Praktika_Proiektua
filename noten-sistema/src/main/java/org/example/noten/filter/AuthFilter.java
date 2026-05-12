package org.example.noten.filter;

import org.example.noten.model.Erabiltzailea;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Rutas públicas
        boolean esPublica = uri.endsWith("/login") || uri.endsWith("/");

        HttpSession session = req.getSession(false);
        Erabiltzailea erabiltzailea = (session != null)
                ? (Erabiltzailea) session.getAttribute("erabiltzailea")
                : null;

        if (esPublica) {
            chain.doFilter(request, response);
            return;
        }

        if (erabiltzailea == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String rola = erabiltzailea.getRola().toString();

        // Comprobación de rol según la ruta
        if (uri.contains("/irakasle/") && !rola.equals("irakasle")) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/ikasle/") && !rola.equals("ikasle")) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/tutore/") && !rola.equals("tutore")) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}