<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ebaluazio Zatiak</title>
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

  <h2>${ikasgaia.izena} — Ebaluazio zatiak</h2>

  <c:choose>
    <c:when test="${empty zatiak}">
      <div class="card">
        <p style="color:var(--text-muted);">Ez dago zatirik oraindik.</p>
      </div>
    </c:when>
    <c:otherwise>
      <table>
        <tr>
          <th>Ebaluazioa</th>
          <th>Izena</th>
          <th>Pisua</th>
          <th>Ezabatu</th>
          <th>Aldatu</th>
        </tr>
        <c:forEach var="zatia" items="${zatiak}">
          <tr>
            <td>${zatia.ebaluazioZenbakia}. Ebaluazioa</td>
            <td><strong>${zatia.izena}</strong></td>
            <td>${zatia.pisua}%</td>
            <td>
              <form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post" style="margin:0;">
                <input type="hidden" name="_method" value="DELETE"/>
                <input type="hidden" name="id" value="${zatia.id}"/>
                <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Ziur zaude?')">Ezabatu</button>
              </form>
            </td>
            <td>
              <form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post" style="margin:0; display:flex; gap:8px; align-items:center;">
                <input type="hidden" name="_method" value="EDIT"/>
                <input type="hidden" name="id" value="${zatia.id}"/>
                <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
                <input type="hidden" name="ebaluazioZenbakia" value="${zatia.ebaluazioZenbakia}"/>
                <input type="text" name="izena" value="${zatia.izena}" required style="margin:0; width:120px;"/>
                <input type="number" name="pisua" value="${zatia.pisua}" min="1" max="100" step="1" required style="margin:0; width:70px;"/>
                <button type="submit" class="btn btn-warning btn-sm">Aldatu</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>

  <h3>Zati berria gehitu</h3>

  <c:if test="${not empty erroreak}">
    <div class="alert alert-error">
      <c:forEach var="errorea" items="${erroreak}">
        ${errorea}
      </c:forEach>
    </div>
  </c:if>

  <div class="card">
    <form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post">
      <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>

      <label>Ebaluazioa:</label>
      <select name="ebaluazioZenbakia">
        <option value="1">1. Ebaluazioa</option>
        <option value="2">2. Ebaluazioa</option>
        <option value="3">3. Ebaluazioa</option>
      </select>

      <label>Izena (adib: Teoria, Praktika):</label>
      <input type="text" name="izena" required/>

      <label>Pisua %:</label>
      <input type="number" name="pisua" min="1" max="100" step="1" required/>

      <input type="submit" value="Gorde"/>
    </form>
  </div>

  <a href="<%= request.getContextPath() %>/irakasle/dashboard" class="back-link">← Atzera</a>

</div>

</body>
</html>