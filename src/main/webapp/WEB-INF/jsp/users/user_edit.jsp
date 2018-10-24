<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Edit</title>
</head>
<body>
<h1>User Edit</h1>
<form action="/users/${user.id}/edit" method="post">
    Email: <input type="email" name="email" value="${user.email}"><br>
    Surname: <input type="text" name="surname" value="${user.surname}"><br>
    Name: <input type="text" name="name" value="${user.name}"><br>
    Password: <input type="password" name="password" value="${user.password}">

    State: <select name="state">
    <c:if test="${user.state==Active}">
        <option selected value="ACTIVE">Active</option>
        <option value="BLOCKED">Blocked</option>
    </c:if>
    <c:if test="${user.state!=Active}">
        <option  value="ACTIVE">Active</option>
        <option selected value="BLOCKED">Blocked</option>
    </c:if>
</select>
    <input type="submit" value="Submit">
</form>
</body>
</html>
