<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Notak</title></head>
<body>

<h2>${ikasgaia.izena} — Notak</h2>

<c:choose>
  <c:when test="${empty ikasleak}">
    <p>Ez dago ikaslerik matriculatuta.</p>
  </c:when>
  <c:otherwise>

    <h3>Nota berria sartu</h3>
    <form action="<%= request.getContextPath() %>/irakasle/notak" method="post">
      <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>

      <label>Ikaslea:</label><br/>
      <select name="ikasleNan">
        <c:forEach var="ikaslea" items="${ikasleak}">
          <option value="${ikaslea.nan}">${ikaslea.erabiltzailea.izenAbizenak}</option>
        </c:forEach>
      </select><br/><br/>

      <label>Zatia:</label><br/>
      <select name="zatiId">
        <c:forEach var="zatia" items="${zatiak}">
          <option value="${zatia.id}">${zatia.ebaluazioZenbakia}. Ebal - ${zatia.izena}</option>
        </c:forEach>
      </select><br/><br/>

      <label>Nota (0-10):</label><br/>
      <input type="number" name="nota" min="0" max="10" step="0.01" required/><br/><br/>

      <input type="submit" value="Gorde"/>
    </form>

    <br/>
    <h3>Notak</h3>
    <c:choose>
      <c:when test="${empty notak}">
        <p>Ez dago notarik oraindik.</p>
      </c:when>
      <c:otherwise>
        <table border="1">
          <tr>
            <th>Ikaslea</th>
            <th>Zatia</th>
            <th>Nota</th>
          </tr>
          <c:forEach var="nota" items="${notak}">
            <tr>
              <td>${nota.ikaslea.erabiltzailea.izenAbizenak}</td>
              <td>${nota.ebaluazioZatia.ebaluazioZenbakia}. Ebal - ${nota.ebaluazioZatia.izena}</td>
              <td>${nota.notaZenbakia}</td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>

  </c:otherwise>
</c:choose>

<br/>
<a href="<%= request.getContextPath() %>/irakasle/dashboard">Atzera</a>

</body>
</html>