<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notak</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<header>
  <h1>Noten Sistema</h1>
  <nav>
    <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Irten</a>
  </nav>
</header>

<div class="container">

  <h2>${ikasgaia.izena} — Notak</h2>

  <c:choose>
    <c:when test="${empty ikasleak}">
      <div class="card">
        <p style="color:var(--text-muted);">Ez dago ikaslerik matriculatuta.</p>
      </div>
    </c:when>
    <c:otherwise>

      <div class="card">
        <h3>Nota berria sartu</h3>
        <form action="<%= request.getContextPath() %>/irakasle/notak" method="post">
          <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>

          <label>Ikaslea:</label>
          <select name="ikasleNan">
            <c:forEach var="ikaslea" items="${ikasleak}">
              <option value="${ikaslea.nan}">${ikaslea.erabiltzailea.izenAbizenak}</option>
            </c:forEach>
          </select>

          <label>Zatia:</label>
          <select name="zatiId">
            <c:forEach var="zatia" items="${zatiak}">
              <option value="${zatia.id}">${zatia.ebaluazioZenbakia}. Ebal - ${zatia.izena}</option>
            </c:forEach>
          </select>

          <label>Nota (0-10):</label>
          <input type="number" name="nota" min="0" max="10" step="0.01" required/>

          <input type="submit" value="Gorde"/>
        </form>
      </div>

      <h3>Notak</h3>
      <c:choose>
        <c:when test="${empty notak}">
          <div class="card">
            <p style="color:var(--text-muted);">Ez dago notarik oraindik.</p>
          </div>
        </c:when>
        <c:otherwise>
          <table>
            <tr>
              <th>Ikaslea</th>
              <th>Zatia</th>
              <th>Nota</th>
              <th>Ezabatu</th>
              <th>Aldatu</th>
            </tr>
            <c:forEach var="nota" items="${notak}">
              <tr>
                <td><strong>${nota.ikaslea.erabiltzailea.izenAbizenak}</strong></td>
                <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebal — ${nota.ebaluazioZatia.izena}</td>
                <td>${nota.notaZenbakia}</td>
                <td>
                  <form action="<%= request.getContextPath() %>/irakasle/notak" method="post" style="margin:0;">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <input type="hidden" name="ikasleNan" value="${nota.ikaslea.nan}"/>
                    <input type="hidden" name="zatiId" value="${nota.ebaluazioZatia.id}"/>
                    <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Ziur zaude?')">Ezabatu</button>
                  </form>
                </td>
                <td>
                  <form action="<%= request.getContextPath() %>/irakasle/notak" method="post" style="margin:0; display:flex; gap:8px; align-items:center;">
                    <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
                    <input type="hidden" name="ikasleNan" value="${nota.ikaslea.nan}"/>
                    <input type="hidden" name="zatiId" value="${nota.ebaluazioZatia.id}"/>
                    <input type="number" name="nota" value="${nota.notaZenbakia}" min="0" max="10" step="0.01" required style="margin:0; width:80px;"/>
                    <button type="submit" class="btn btn-warning btn-sm">Aldatu</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </table>
        </c:otherwise>
      </c:choose>

    </c:otherwise>
  </c:choose>

  <a href="<%= request.getContextPath() %>/irakasle/dashboard" class="back-link">← Atzera</a>

</div>

</body>
</html>