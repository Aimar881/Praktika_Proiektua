<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Erabiltzaileak kudeatu</title>
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

  <h2>Erabiltzaileak kudeatu</h2>

  <c:if test="${not empty errorea}">
    <div class="alert alert-error">${errorea}</div>
  </c:if>
  <c:if test="${param.success == 'irakasle'}">
    <div class="alert alert-success">Irakaslea ongi sortu da.</div>
  </c:if>
  <c:if test="${param.success == 'ikasle'}">
    <div class="alert alert-success">Ikaslea ongi sortu da.</div>
  </c:if>
  <c:if test="${param.success == 'matrikula'}">
    <div class="alert alert-success">Matrikula ongi egin da.</div>
  </c:if>

  <div style="display:grid; grid-template-columns:1fr 1fr; gap:20px;">

    <!-- Crear profesor -->
    <div class="card">
      <h3>Irakasle berria</h3>
      <form action="<%= request.getContextPath() %>/tutore/usuarios" method="post">
        <input type="hidden" name="_method" value="IRAKASLE"/>
        <label>NAN:</label>
        <input type="text" name="nan" maxlength="9" required/>
        <label>Izen-abizenak:</label>
        <input type="text" name="izenAbizenak" required/>
        <label>Telefonoa:</label>
        <input type="text" name="tlfna" maxlength="9"/>
        <label>Pasahitza:</label>
        <input type="password" name="pasahitza" required/>
        <input type="submit" value="Irakaslea sortu" style="width:100%;"/>
      </form>
    </div>

    <!-- Crear alumno -->
    <div class="card">
      <h3>Ikasle berria</h3>
      <form action="<%= request.getContextPath() %>/tutore/usuarios" method="post">
        <input type="hidden" name="_method" value="IKASLE"/>
        <label>NAN:</label>
        <input type="text" name="nan" maxlength="9" required/>
        <label>Izen-abizenak:</label>
        <input type="text" name="izenAbizenak" required/>
        <label>Telefonoa:</label>
        <input type="text" name="tlfna" maxlength="9"/>
        <label>Pasahitza:</label>
        <input type="password" name="pasahitza" required/>
        <label>Taldea:</label>
        <input type="text" name="taldea" maxlength="10" required/>
        <input type="submit" value="Ikaslea sortu" style="width:100%;"/>
      </form>
    </div>

  </div>

  <!-- Matricular alumno -->
  <div class="card">
    <h3>Ikaslea matriculatu</h3>
    <form action="<%= request.getContextPath() %>/tutore/usuarios" method="post" style="display:grid; grid-template-columns:1fr 1fr auto; gap:16px; align-items:flex-end;">
      <input type="hidden" name="_method" value="MATRIKULA"/>
      <div>
        <label>Ikaslea:</label>
        <select name="ikasleNan" style="margin:0;">
          <c:forEach var="ikaslea" items="${ikasleak}">
            <option value="${ikaslea.nan}">${ikaslea.erabiltzailea.izenAbizenak} (${ikaslea.taldea})</option>
          </c:forEach>
        </select>
      </div>
      <div>
        <label>Ikasgaia:</label>
        <select name="ikasgaiaId" style="margin:0;">
          <c:forEach var="ikasgaia" items="${ikasgaiak}">
            <option value="${ikasgaia.id}">${ikasgaia.izena} — ${ikasgaia.ikasturtea.izena}</option>
          </c:forEach>
        </select>
      </div>
      <input type="submit" value="Matriculatu" style="margin:0;"/>
    </form>
  </div>

  <a href="<%= request.getContextPath() %>/tutore/dashboard" class="back-link">← Atzera</a>

</div>

</body>
</html>