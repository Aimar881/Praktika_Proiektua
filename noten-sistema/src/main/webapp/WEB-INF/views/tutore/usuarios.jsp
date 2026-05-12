<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Erabiltzaileak kudeatu</title></head>
<body>

<h2>Erabiltzaileak kudeatu</h2>

<c:if test="${not empty errorea}">
  <p style="color:red;">${errorea}</p>
</c:if>
<c:if test="${param.success == 'irakasle'}">
  <p style="color:green;">Irakaslea ongi sortu da.</p>
</c:if>
<c:if test="${param.success == 'ikasle'}">
  <p style="color:green;">Ikaslea ongi sortu da.</p>
</c:if>
<c:if test="${param.success == 'matrikula'}">
  <p style="color:green;">Matrikula ongi egin da.</p>
</c:if>

<!-- Crear profesor -->
<h3>Irakasle berria</h3>
<form action="<%= request.getContextPath() %>/tutore/usuarios" method="post">
  <input type="hidden" name="_method" value="IRAKASLE"/>
  <label>NAN:</label><br/>
  <input type="text" name="nan" maxlength="9" required/><br/><br/>
  <label>Izen-abizenak:</label><br/>
  <input type="text" name="izenAbizenak" required/><br/><br/>
  <label>Telefonoa:</label><br/>
  <input type="text" name="tlfna" maxlength="9"/><br/><br/>
  <label>Pasahitza:</label><br/>
  <input type="password" name="pasahitza" required/><br/><br/>
  <input type="submit" value="Irakaslea sortu"/>
</form>

<br/>

<!-- Crear alumno -->
<h3>Ikasle berria</h3>
<form action="<%= request.getContextPath() %>/tutore/usuarios" method="post">
  <input type="hidden" name="_method" value="IKASLE"/>
  <label>NAN:</label><br/>
  <input type="text" name="nan" maxlength="9" required/><br/><br/>
  <label>Izen-abizenak:</label><br/>
  <input type="text" name="izenAbizenak" required/><br/><br/>
  <label>Telefonoa:</label><br/>
  <input type="text" name="tlfna" maxlength="9"/><br/><br/>
  <label>Pasahitza:</label><br/>
  <input type="password" name="pasahitza" required/><br/><br/>
  <label>Taldea:</label><br/>
  <input type="text" name="taldea" maxlength="10" required/><br/><br/>
  <input type="submit" value="Ikaslea sortu"/>
</form>

<br/>

<!-- Matricular alumno -->
<h3>Ikaslea matriculatu</h3>
<form action="<%= request.getContextPath() %>/tutore/usuarios" method="post">
  <input type="hidden" name="_method" value="MATRIKULA"/>
  <label>Ikaslea:</label><br/>
  <select name="ikasleNan">
    <c:forEach var="ikaslea" items="${ikasleak}">
      <option value="${ikaslea.nan}">${ikaslea.erabiltzailea.izenAbizenak} (${ikaslea.taldea})</option>
    </c:forEach>
  </select><br/><br/>
  <label>Ikasgaia:</label><br/>
  <select name="ikasgaiaId">
    <c:forEach var="ikasgaia" items="${ikasgaiak}">
      <option value="${ikasgaia.id}">${ikasgaia.izena} — ${ikasgaia.ikasturtea.izena}</option>
    </c:forEach>
  </select><br/><br/>
  <input type="submit" value="Matriculatu"/>
</form>

<br/>
<a href="<%= request.getContextPath() %>/tutore/dashboard">Atzera</a>

</body>
</html>