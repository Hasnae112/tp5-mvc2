<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Gestion des Produits</title>
    <style>
        body { font-family: Arial; margin: 50px; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Authentification</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Login :</label>
        <input type="text" name="login" required/><br/><br/>
        <label>Mot de passe :</label>
        <input type="password" name="password" required/><br/><br/>
        <input type="submit" value="Se connecter"/>
    </form>
    <p class="error">${error}</p>
</body>
</html>
