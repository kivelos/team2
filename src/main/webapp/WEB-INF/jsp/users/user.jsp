<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.email}</title>
    <style>
        body{
            font: 12pt comic-sans;
        }
        #content {
            height: 250px;
            width: 300px;
            margin: auto;
            margin-top:  100px;
            box-shadow: 0 0 20px blue;
            border: 1px solid green;
            padding-top: 10px;
        }
        #c1{
            width: 30px;
            margin: 10px 0 0 40px;
            position: relative;
            float: left;
        }
        #c2{
            width: 100px;
            margin-top: 10px;
            float: right;
            margin-right: 50px;
        }
        #s{
            width: 300px;
            margin-top: 210px;
            text-align: center;
        }
        a{
            margin-right: 20px;
        }
    </style>
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