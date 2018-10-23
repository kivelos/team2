<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.email}</title>
</head>
<body>
Email: ${user.email}<br>
Surname: ${user.surname}<br>
Name: ${user.name}<br>
Password: ${user.password}<br>
State: ${user.state}<br>


<a href="/users/${user.id}/edit">Edit user</a>
<a href="/users">Users List</a>
</body>
</html>
