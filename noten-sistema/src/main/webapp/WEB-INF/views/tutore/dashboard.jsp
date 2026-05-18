<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tutore - Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<header>
    <h1>Noten Sistema</h1>
    <nav>
        <a href="<%= request.getContextPath() %>/tutore/usuarios" class="btn">Erabiltzaileak</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Irten</a>
    </nav>
</header>

<div class="container">

    <h2>Tutore panela</h2>

    <div class="card">
        <form action="<%= request.getContextPath() %>/tutore/dashboard" method="get" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;">
                <label>Ikaslea aukeratu:</label>
                <select name="ikasleNan" style="margin:0;">
                    <option value="">-- Aukeratu --</option>
                    <c:forEach var="ikaslea" items="${ikasleak}">
                        <option value="${ikaslea.nan}"
                                <c:if test="${ikaslea.nan == ikasleNanSelektatua}">selected</c:if>>
                                ${ikaslea.erabiltzailea.izenAbizenak} (${ikaslea.taldea})
                        </option>
                    </c:forEach>
                </select>
            </div>
            <input type="submit" value="Ikusi" style="margin:0;"/>
        </form>
    </div>

    <c:if test="${not empty notak}">

        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;" class="no-print">
            <h2 style="margin:0;">Buletina — ${notak[0].ikaslea.erabiltzailea.izenAbizenak}</h2>
            <a href="javascript:window.print()" class="btn btn-secondary">🖨️ Inprimatu</a>
        </div>

        <c:set var="ikasgaiaAktual" value=""/>

        <c:forEach var="nota" items="${notak}" varStatus="loop">

            <c:if test="${nota.ebaluazioZatia.ikasgaia.izena != ikasgaiaAktual}">
                <c:if test="${ikasgaiaAktual != ''}">
                    </table>
                    <div class="nota-finala">Nota finala: ${notaFinalak[nota.ebaluazioZatia.ikasgaia.id]}</div>
                </c:if>
                <h3>${nota.ebaluazioZatia.ikasgaia.izena}</h3>
                <table>
                <tr>
                    <th>Ebaluazioa</th>
                    <th>Zatia</th>
                    <th>Pisua %</th>
                    <th>Nota</th>
                </tr>
                <c:set var="ikasgaiaAktual" value="${nota.ebaluazioZatia.ikasgaia.izena}"/>
            </c:if>

            <tr>
                <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebaluazioa</td>
                <td>${nota.ebaluazioZatia.izena}</td>
                <td>${nota.ebaluazioZatia.pisua}%</td>
                <td><strong>${nota.notaZenbakia}</strong></td>
            </tr>

            <c:if test="${loop.last}">
                </table>
                <div class="nota-finala">Nota finala: ${notaFinalak[notak[notak.size()-1].ebaluazioZatia.ikasgaia.id]}</div>
            </c:if>

        </c:forEach>

    </c:if>

</div>

</body>
</html>