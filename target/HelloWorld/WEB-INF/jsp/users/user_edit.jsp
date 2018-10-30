<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS" />
    <link href="${entityEditCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
    <h1>USER EDIT</h1>
    <form action="/users/${user.id}/edit" method="post">
        Email   :   <input type="email" name="email" value="${user.email}" class="s"><br>
        Surname : <input type=" text" name="surname" value="${user.surname}"><br>
        Name    : <input type="text" name="name" value="${user.name}" class="s"><br>
        Password : <input type="password" name="password" value="${user.password}" id="p">
        <br>

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
        <br>
        <input type="submit" value="Submit" id="submit">
    </form>
</div>
</body>
</html>