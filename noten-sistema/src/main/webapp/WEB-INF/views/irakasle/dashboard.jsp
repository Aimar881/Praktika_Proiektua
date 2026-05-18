<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Irakasle - Dashboard</title>
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

  <h2>Nire ikasgaiak</h2>

  <c:choose>
    <c:when test="${empty ikasgaiak}">
      <div class="card">
        <p style="color:var(--text-muted);">Ez daukazu ikasgairik oraindik.</p>
      </div>
    </c:when>
    <c:otherwise>
      <table>
        <tr>
          <th>Izena</th>
          <th>Ikasturtea</th>
          <th>Zatiak</th>
          <th>Notak</th>
          <th>Ezabatu</th>
        </tr>
        <c:forEach var="ikasgaia" items="${ikasgaiak}">
          <tr>
            <td><strong>${ikasgaia.izena}</strong></td>
            <td>${ikasgaia.ikasturtea.izena}</td>
            <td>
              <a href="<%= request.getContextPath() %>/irakasle/zatiak?ikasgaiaId=${ikasgaia.id}" class="btn btn-secondary btn-sm">Zatiak</a>
            </td>
            <td>
              <a href="<%= request.getContextPath() %>/irakasle/notak?ikasgaiaId=${ikasgaia.id}" class="btn btn-primary btn-sm">Notak</a>
            </td>
            <td>
              <form action="<%= request.getContextPath() %>/irakasle/ikasgaia" method="post" style="margin:0;">
                <input type="hidden" name="_method" value="DELETE"/>
                <input type="hidden" name="id" value="${ikasgaia.id}"/>
                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Ziur zaude?')">Ezabatu</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>

  <a href="<%= request.getContextPath() %>/irakasle/ikasgaia" class="btn btn-primary">+ Ikasgai berria</a>

</div>

</body>
</html>