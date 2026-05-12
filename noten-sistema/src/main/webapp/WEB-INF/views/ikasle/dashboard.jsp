<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Ikasle - Dashboard</title></head>
<body>

<h2>Ongi etorri, <%= ((org.example.noten.model.Erabiltzailea) session.getAttribute("erabiltzailea")).getIzenAbizenak() %>!</h2>

<c:choose>
    <c:when test="${empty notak}">
        <p>Ez daukazu notarik oraindik.</p>
    </c:when>
    <c:otherwise>
        <c:set var="ikasgaiaAktual" value="-1"/>

        <c:forEach var="nota" items="${notak}" varStatus="loop">

            <c:if test="${nota.ebaluazioZatia.ikasgaia.id != ikasgaiaAktual}">
                <%-- Cerrar tabla anterior si no es la primera --%>
                <c:if test="${ikasgaiaAktual != '-1'}">
                    </table>
                    <p><strong>Nota finala: ${notaFinalak[ikasgaiaAktual]}</strong></p>
                    <br/>
                </c:if>
                <%-- Abrir nueva asignatura --%>
                <h3>${nota.ebaluazioZatia.ikasgaia.izena}</h3>
                <table border="1">
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
                <td>${nota.notaZenbakia}</td>
            </tr>

            <%-- Cerrar la última tabla --%>
            <c:if test="${loop.last}">
                </table>
                <p><strong>Nota finala: ${notaFinalak[ikasgaiaAktual]}</strong></p>
            </c:if>

        </c:forEach>
    </c:otherwise>
</c:choose>

<br/>
<a href="<%= request.getContextPath() %>/logout">Irten</a>

</body>
</html>