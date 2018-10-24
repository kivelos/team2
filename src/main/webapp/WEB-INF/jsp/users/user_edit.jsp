<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Edit</title>
    <style>
        body{
            font: 12pt comic-sans;
        }
        #content {
            height: 300px;
            width: 500px;
            margin: auto;
            margin-top:  100px;
            box-shadow: 0 0 20px greenyellow;
            border: 1px solid green;
            padding-top: 10px;
            background-color: white;
        }
        h1{
            text-align: center;
            margin-left: -10px;
            color: green;
        }
        form{
            margin: 20px 0 0 90px;
        }
        input {
            margin: 5px;
            width: 60%;
            height: 20px;
            margin-left: 10px;
            position: relative;
            right: 10px;
            border: 1px solid gray;
        }

        #submit{
            margin: 20px 60px;
            height: 30px;
            align-self: center;
            background-color: greenyellow;
            font: 12pt comic-sans;

        }
        .s{
            margin-left:27px;
        }
        #p{
            width: 59%;
        }


    </style>
</head>
<body>
<div id="content">
    <h1>USER EDIT</h1>
    <form action="/users/${user.id}/edit" method="post">
        Email   :   <input type="email" name="email" value="${user.email}" class="s"><br>
        Surname : <input type="text" name="surname" value="${user.surname}"><br>
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