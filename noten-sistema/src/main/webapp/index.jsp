<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Noten Sistema</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="container">
    <div class="card" style="max-width: 400px; margin: 80px auto;">
        <h2>Sartu / Iniciar sesión</h2>

        <% if (request.getAttribute("errorMezua") != null) { %>
        <div class="error"><%= request.getAttribute("errorMezua") %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post">
            <label>NAN:</label>
            <input type="text" name="nan" required/>

            <label>Pasahitza:</label>
            <input type="password" name="pasahitza" required/>

            <input type="submit" value="Sartu" style="width:100%;"/>
        </form>
    </div>
</div>

</body>
</html>