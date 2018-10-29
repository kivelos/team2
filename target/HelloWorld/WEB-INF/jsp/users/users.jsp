<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Users List</h1>
Filtering
<form action="/users/filtering" method="post">
    Email: <input type="email" name="email"><br>
    Password: <input type="password" name="password"><br>
    Surname: <input type="text" name="surname"><br>
    Name: <input type="text" name="name"><br>
    State:
    <select name="state">
        <option value="">Choose state</option>
        <option value="BLOCKED">BLOCKED</option>
        <option value="ACTIVE">ACTIVE</option>
    </select>
    <input type="submit" value="Submit">
</form>
<table border="4">
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
Page ${page}
<a href="/users/page/${page - 1}">Previous</a>
<a href="/users/page/${page + 1}">Next</a>

<br><br>
Add new user<br>
<form action="/users" method="post">
    Email: <input type="email" name="email"><br>
    Password: <input type="password" name="password"><br>
    Name: <input type="text" name="name"><br>
    Surname: <input type="text" name="surname"><br>
    State: <select name="state">
    <option value="Blocked">BLOCKED</option>
    <option selected value="Active">ACTIVE</option></select>
    <input type="submit" value="Submit">
</form>
${mistake}
<a href="..">Homepage</a>
</body>
</html>