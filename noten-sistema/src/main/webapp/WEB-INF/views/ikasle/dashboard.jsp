<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Ikasle - Dashboard</title></head>
<body>

<h2>Ongi etorri, <%= ((org.example.noten.model.Erabiltzailea) session.getAttribute("erabiltzailea")).getIzenAbizenak() %>!</h2>

<h3>Nire notak</h3>

<c:choose>
    <c:when test="${empty notak}">
        <p>Ez daukazu notarik oraindik.</p>
    </c:when>
    <c:otherwise>
        <table border="1">
            <tr>
                <th>Ikasgaia</th>
                <th>Ebaluazioa</th>
                <th>Zatia</th>
                <th>Nota</th>
            </tr>
            <c:forEach var="nota" items="${notak}">
                <tr>
                    <td>${nota.ebaluazioZatia.ikasgaia.izena}</td>
                    <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebaluazioa</td>
                    <td>${nota.ebaluazioZatia.izena}</td>
                    <td>${nota.notaZenbakia}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<br/>
<a href="<%= request.getContextPath() %>/logout">Irten</a>

</body>
</html>