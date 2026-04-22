<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Produits</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .btn { padding: 5px 10px; text-decoration: none; border-radius: 3px; }
        .modifier  { background-color: #4CAF50; color: white; }
        .supprimer { background-color: #f44336; color: white; }
        .logout    { background-color: #555; color: white; padding: 10px; text-decoration: none; }
        .admin-only { background-color: #f9f9f9; padding: 15px; border: 1px solid #ddd; margin: 10px 0; }
    </style>
</head>
<body>

<h2>Gestion des Produits (MVC2 + Hibernate)</h2>
<p>Connecté en tant que : <strong>${sessionScope.user}</strong> (rôle : ${sessionScope.role})</p>
<a href="${pageContext.request.contextPath}/login?action=logout" class="logout">Déconnexion</a>
<hr/>

<!-- SECTION ADMIN UNIQUEMENT (Ajouter / Modifier) -->
<c:if test="${sessionScope.role == 'ADMIN'}">
    <div class="admin-only">
        <h3>${produitEdit != null ? 'Modifier un produit' : 'Ajouter un produit'}</h3>
        <form action="${pageContext.request.contextPath}/produits" method="post">
            <c:choose>
                <c:when test="${produitEdit != null}">
                    <input type="hidden" name="action"    value="update"/>
                    <input type="hidden" name="idProduit" value="${produitEdit.idProduit}"/>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="action" value="add"/>
                </c:otherwise>
            </c:choose>

            Nom :         <input type="text" name="nom"         value="${produitEdit.nom}"         required/><br/><br/>
            Description : <input type="text" name="description" value="${produitEdit.description}" required/><br/><br/>
            Prix :        <input type="text" name="prix"        value="${produitEdit.prix}"        required/><br/><br/>
            <input type="submit" value="${produitEdit != null ? 'Modifier' : 'Ajouter'}"/>
        </form>
    </div>
</c:if>

<hr/>

<!-- RECHERCHE (accessible à tous) -->
<h3>Rechercher un produit par ID</h3>
<form action="${pageContext.request.contextPath}/produits" method="get">
    <input type="hidden" name="action" value="search"/>
    ID : <input type="text" name="idProduit"/>
    <input type="submit" value="Rechercher"/>
</form>

<hr/>

<!-- TABLEAU DES PRODUITS -->
<h3>Liste des produits</h3>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Prix</th>
            <c:if test="${sessionScope.role == 'ADMIN'}"><th>Actions</th></c:if>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listeProduits}" var="p">
            <tr>
                <td>${p.idProduit}</td>
                <td>${p.nom}</td>
                <td>${p.description}</td>
                <td>${p.prix}</td>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <td>
                        <a href="${pageContext.request.contextPath}/produits?action=edit&id=${p.idProduit}"
                           class="btn modifier">Modifier</a>
                        <a href="${pageContext.request.contextPath}/produits?action=delete&id=${p.idProduit}"
                           class="btn supprimer"
                           onclick="return confirm('Supprimer ce produit ?')">Supprimer</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
