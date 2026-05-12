<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Ebaluazio Zatiak</title></head>
<body>

<h2>${ikasgaia.izena} — Ebaluazio zatiak</h2>

<!-- Lista de partes existentes -->
<c:choose>
  <c:when test="${empty zatiak}">
    <p>Ez dago zatirik oraindik.</p>
  </c:when>
  <c:otherwise>
    <table border="1">
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
          <td>${zatia.izena}</td>
          <td>${zatia.pisua}%</td>
          <td>
            <form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post">
              <input type="hidden" name="_method" value="DELETE"/>
              <input type="hidden" name="id" value="${zatia.id}"/>
              <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
              <button type="submit" onclick="return confirm('Ziur zaude?')">Ezabatu</button>
            </form>
          </td>
          <td>
            <form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post">
              <input type="hidden" name="_method" value="EDIT"/>
              <input type="hidden" name="id" value="${zatia.id}"/>
              <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>
              <input type="hidden" name="ebaluazioZenbakia" value="${zatia.ebaluazioZenbakia}"/>
              <input type="text" name="izena" value="${zatia.izena}" required/>
              <input type="number" name="pisua" value="${zatia.pisua}" min="1" max="100" step="1" required/>
              <button type="submit">Aldatu</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

<br/>
<h3>Zati berria gehitu</h3>

<c:if test="${not empty erroreak}">
  <ul style="color:red;">
    <c:forEach var="errorea" items="${erroreak}">
      <li>${errorea}</li>
    </c:forEach>
  </ul>
</c:if>

<form action="<%= request.getContextPath() %>/irakasle/zatiak" method="post">
  <input type="hidden" name="ikasgaiaId" value="${ikasgaia.id}"/>

  <label>Ebaluazioa:</label><br/>
  <select name="ebaluazioZenbakia">
    <option value="1">1. Ebaluazioa</option>
    <option value="2">2. Ebaluazioa</option>
    <option value="3">3. Ebaluazioa</option>
  </select><br/><br/>

  <label>Izena (adib: Teoria, Praktika):</label><br/>
  <input type="text" name="izena" required/><br/><br/>

  <label>Pisua %:</label><br/>
  <input type="number" name="pisua" min="1" max="100" step="1" required/><br/><br/>

  <input type="submit" value="Gorde"/>
</form>

<br/>
<a href="<%= request.getContextPath() %>/irakasle/dashboard">Atzera</a>

</body>
</html>