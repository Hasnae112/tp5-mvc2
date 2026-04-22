<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Erreur</title></head>
<body>
    <h2>Une erreur est survenue</h2>
    <p>Message : ${pageContext.exception.message}</p>
    <a href="${pageContext.request.contextPath}/produits">Retour à l'accueil</a>
</body>
</html>
