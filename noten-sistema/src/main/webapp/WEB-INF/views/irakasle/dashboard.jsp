<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Irakasle - Dashboard</title></head>
<body>

<h2>Ongi etorri, <%= ((org.example.noten.model.Erabiltzailea) session.getAttribute("erabiltzailea")).getIzenAbizenak() %>!</h2>

<h3>Nire ikasgaiak</h3>

<c:choose>
  <c:when test="${empty ikasgaiak}">
    <p>Ez daukazu ikasgairik oraindik.</p>
  </c:when>
  <c:otherwise>
    <table border="1">
      <tr>
        <th>Izena</th>
        <th>Ikasturtea</th>
        <th>Zatiak</th>
        <th>Notak</th>
        <th>Ezabatu</th>
      </tr>
      <c:forEach var="ikasgaia" items="${ikasgaiak}">
        <tr>
          <td>${ikasgaia.izena}</td>
          <td>${ikasgaia.ikasturtea.izena}</td>
          <td><a href="<%= request.getContextPath() %>/irakasle/zatiak?ikasgaiaId=${ikasgaia.id}">Zatiak</a></td>
          <td><a href="<%= request.getContextPath() %>/irakasle/notak?ikasgaiaId=${ikasgaia.id}">Notak</a></td>
          <td>
            <form action="<%= request.getContextPath() %>/irakasle/ikasgaia" method="post">
              <input type="hidden" name="_method" value="DELETE"/>
              <input type="hidden" name="id" value="${ikasgaia.id}"/>
              <button type="submit" onclick="return confirm('Ziur zaude?')">Ezabatu</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

<a href="<%= request.getContextPath() %>/irakasle/ikasgaia">Ikasgai berria</a><br/><br/>

<br/>
<a href="<%= request.getContextPath() %>/logout">Irten</a>

</body>
</html>