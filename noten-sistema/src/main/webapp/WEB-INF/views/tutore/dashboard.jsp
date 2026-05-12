<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Tutore - Dashboard</title></head>
<body>

<h2>Tutore panela</h2>

<form action="<%= request.getContextPath() %>/tutore/dashboard" method="get">
    <label>Ikaslea aukeratu:</label>
    <select name="ikasleNan">
        <option value="">-- Aukeratu --</option>
        <c:forEach var="ikaslea" items="${ikasleak}">
            <option value="${ikaslea.nan}"
                    <c:if test="${ikaslea.nan == ikasleNanSelektatua}">selected</c:if>>
                    ${ikaslea.erabiltzailea.izenAbizenak} (${ikaslea.taldea})
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="Ikusi"/>
</form>

<br/>

<c:if test="${not empty notak}">
    <h3>Buletina — ${notak[0].ikaslea.erabiltzailea.izenAbizenak}</h3>

    <c:set var="ikasgaiaAktual" value=""/>

    <c:forEach var="nota" items="${notak}">

        <c:if test="${nota.ebaluazioZatia.ikasgaia.izena != ikasgaiaAktual}">
            <c:if test="${ikasgaiaAktual != ''}">
                </table>
                <p><strong>Nota finala: ${notaFinalak[nota.ebaluazioZatia.ikasgaia.id]}</strong></p>
                <br/>
            </c:if>
            <h4>${nota.ebaluazioZatia.ikasgaia.izena}</h4>
            <table border="1">
            <tr>
                <th>Ebaluazioa</th>
                <th>Zatia</th>
                <th>Pisua</th>
                <th>Nota</th>
            </tr>
            <c:set var="ikasgaiaAktual" value="${nota.ebaluazioZatia.ikasgaia.izena}"/>
        </c:if>

        <tr>
            <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebaluazioa</td>
            <td>${nota.ebaluazioZatia.izena}</td>
            <td>${nota.ebaluazioZatia.pisua}</td>
            <td>${nota.notaZenbakia}</td>
        </tr>

    </c:forEach>

    </table>
    <p><strong>Nota finala: ${notaFinalak[notak[notak.size()-1].ebaluazioZatia.ikasgaia.id]}</strong></p>

    <br/>
    <a href="javascript:window.print()">🖨️ Inprimatu</a>
</c:if>

<a href="<%= request.getContextPath() %>/tutore/usuarios">Erabiltzaileak kudeatu</a><br/><br/>

<br/>
<a href="<%= request.getContextPath() %>/logout">Irten</a>

</body>
</html>