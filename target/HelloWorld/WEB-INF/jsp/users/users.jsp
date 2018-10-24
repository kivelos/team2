<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Users List</h1>
<table>
    <tr>
        <td>№</td>
        <td>Email<br>
            <a href="/users?sort=asc&field=email">a</a>/
            <a href="/users?sort=desc&field=email">d</a>
        </td>
        <td>Name<br>
            <a href="/users?sort=asc&field=name">a</a>/
            <a href="/users?sort=desc&field=name">d</a>
        </td>
        <td>Surname<br>
            <a href="/users?sort=asc&field=surname">a</a>/
            <a href="/users?sort=desc&field=surname">d</a>
        </td>
        <td>State<br>
            <a href="/users?sort=asc&field=user_state">a</a>/
            <a href="/users?sort=desc&field=user_state">d</a>
        </td>
    </tr>
    <c:forEach items="${users_list}" var="user" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/users/${user.id}">${user.email}</a></td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.state}</td>
        </tr>
    </c:forEach>
</table>
<form action="/users/page/${page+1}" method="post">
    <input type="submit" value="Next">
</form>
<form action="/users/page/${page-1}" method="post">
    <input type="submit" value="Previous">
</form>
<%--<>--%>
<%--<select name="sort">--%>
    <%--<option>E-mail</option>--%>
    <%--<option>Name</option>--%>
    <%--<option>Surname</option>--%>
    <%--<option>State</option>--%>
<%--</select>--%>

<br><br><br>
Add new user<br>
<form action="/users" method="post">
    Email: <input type="email" name="email"><br>
    Password: <input type="password" name="password"><br>
    Name: <input type="text" name="name"><br>
    Surname: <input type="text" name="surname"><br>
    State: <select name="state">
    <option value="Blocked">Blocked</option>
    <option selected value="Active">Active</option></select>
    <input type="submit" value="Submit">
</form>
<c:if test="${mistake!=null}">
    ${mistake} <br/>
</c:if>
</body>
</html>
