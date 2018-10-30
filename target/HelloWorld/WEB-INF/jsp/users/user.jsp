<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.email}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
</head>
<body><div id=content>
    <div id="c1">
        <p>Email: </p>
        <p>Surname:</p>
        <p>Name: </p>
        <p>Password: </p>
        <p>State: </p>
    </div>
    <div id="c2">
        <p> ${user.email}</p>
        <p>${user.surname}</p>
        <p>${user.name}</p>
        <p> ${user.password}</p>
        <p> ${user.state}</p>
    </div>
    <div id="s">
        <a href="/users/${user.id}/edit">Edit user</a>
        <a href="/users">Users List</a>
    </div>
</div>
</body>
</html>