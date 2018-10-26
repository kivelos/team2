<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<<<<<<< HEAD
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
=======
>>>>>>> 0bfd5f01c0977af70f7aa1acb16eab5b5c7eb4eb
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Skill Edit</title>
<<<<<<< HEAD
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS" />
    <link href="${entityEditCSS}" rel="stylesheet">
=======
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
>>>>>>> 0bfd5f01c0977af70f7aa1acb16eab5b5c7eb4eb
</head>
<body>
<div id="content">
    <h1>SKILL EDIT</h1>
    <form action="/skills/${skill.name}/edit" method="post">
        Name    : <input type="text" name="name" value="${skill.name}" class="s"><br>

        <br>
        <input type="submit" value="Submit" id="submit">
    </form>
</div>
<c:if test="${mistake!=null}">
    ${mistake} <br/>
</c:if>
</body>
</html>