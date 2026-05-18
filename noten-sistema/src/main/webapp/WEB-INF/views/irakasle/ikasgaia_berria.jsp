<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ikasgai berria</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<header>
  <h1>Noten Sistema</h1>
  <nav>
    <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Irten</a>
  </nav>
</header>

<div class="container-sm">

  <h2>Ikasgai berria sortu</h2>

  <div class="card">
    <form action="<%= request.getContextPath() %>/irakasle/ikasgaia" method="post">

      <label>Ikasgaiaren izena:</label>
      <input type="text" name="izena" required/>

      <label>Ikasturtea:</label>
      <select name="ikasturte_id" required>
        <c:forEach var="ikasturtea" items="${ikasturteak}">
          <option value="${ikasturtea.id}">${ikasturtea.izena}</option>
        </c:forEach>
      </select>

      <input type="submit" value="Gorde" style="width:100%;"/>
    </form>
  </div>

  <a href="<%= request.getContextPath() %>/irakasle/dashboard" class="back-link">← Atzera</a>

</div>

</body>
</html>