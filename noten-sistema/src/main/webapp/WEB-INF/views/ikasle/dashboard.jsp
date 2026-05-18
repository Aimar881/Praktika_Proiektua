<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ikasle - Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<header>
    <h1>Noten Sistema</h1>
    <nav>
        <span style="color:#b0aa9f; font-size:0.85rem;">
            <%= ((org.example.noten.model.Erabiltzailea) session.getAttribute("erabiltzailea")).getIzenAbizenak() %>
        </span>
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Irten</a>
    </nav>
</header>

<div class="container">

    <h2>Nire notak</h2>

    <c:choose>
        <c:when test="${empty notak}">
            <div class="card">
                <p style="color:var(--text-muted);">Ez daukazu notarik oraindik.</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:set var="ikasgaiaAktual" value="-1"/>

            <c:forEach var="nota" items="${notak}" varStatus="loop">

                <c:if test="${nota.ebaluazioZatia.ikasgaia.id != ikasgaiaAktual}">
                    <c:if test="${ikasgaiaAktual != '-1'}">
                        </table>
                        <div class="nota-finala">Nota finala: ${notaFinalak[ikasgaiaAktual]}</div>
                    </c:if>
                    <h3>${nota.ebaluazioZatia.ikasgaia.izena}</h3>
                    <table>
                    <tr>
                        <th>Ebaluazioa</th>
                        <th>Zatia</th>
                        <th>Pisua %</th>
                        <th>Nota</th>
                    </tr>
                    <c:set var="ikasgaiaAktual" value="${nota.ebaluazioZatia.ikasgaia.id}"/>
                </c:if>

                <tr>
                    <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebaluazioa</td>
                    <td>${nota.ebaluazioZatia.izena}</td>
                    <td>${nota.ebaluazioZatia.pisua}%</td>
                    <td><strong>${nota.notaZenbakia}</strong></td>
                </tr>

                <c:if test="${loop.last}">
                    </table>
                    <div class="nota-finala">Nota finala: ${notaFinalak[ikasgaiaAktual]}</div>
                </c:if>

            </c:forEach>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>