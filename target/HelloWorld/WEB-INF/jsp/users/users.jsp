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
        <td>Email</td>
        <td>Name</td>
        <td>Surname</td>
        <td>State</td>
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
    ${mistake}  <br/>
</c:if>
</body>
</html>
