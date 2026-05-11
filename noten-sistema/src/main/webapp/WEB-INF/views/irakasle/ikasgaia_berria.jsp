<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Ikasgai berria</title></head>
<body>

<h2>Ikasgai berria sortu</h2>

<form action="<%= request.getContextPath() %>/irakasle/ikasgaia" method="post">

  <label>Ikasgaiaren izena:</label><br/>
  <input type="text" name="izena" required/><br/><br/>

  <label>Ikasturtea:</label><br/>
  <select name="ikasturte_id" required>
    <c:forEach var="ikasturtea" items="${ikasturteak}">
      <option value="${ikasturtea.id}">${ikasturtea.izena}</option>
    </c:forEach>
  </select><br/><br/>

  <input type="submit" value="Gorde"/>
</form>

<br/>
<a href="<%= request.getContextPath() %>/irakasle/dashboard">Atzera</a>

</body>
</html>