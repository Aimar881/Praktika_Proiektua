<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Noten Sistema</title>
</head>
<body>

<h2>Sartu / Iniciar sesión</h2>

<form action="login" method="post">
    <label>NAN:</label>
    <input type="text" name="nan" required/><br/><br/>

    <label>Pasahitza:</label>
    <input type="password" name="pasahitza" required/><br/><br/>

    <input type="submit" value="Sartu"/>
</form>

<% if (request.getAttribute("errorMezua") != null) { %>
<p style="color:red;"><%= request.getAttribute("errorMezua") %></p>
<% } %>

</body>
</html>