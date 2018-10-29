<<<<<<< HEAD
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
=======
>>>>>>> 0bfd5f01c0977af70f7aa1acb16eab5b5c7eb4eb
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${skill.name}</title>
<<<<<<< HEAD
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
=======
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
>>>>>>> 0bfd5f01c0977af70f7aa1acb16eab5b5c7eb4eb
</head>
<body><div id=content>
    <div id="c1">
        <p>Name: </p>
    </div>
    <div id="c2">
        <p>${skill.name}</p>
    </div>
    <div id="s">
        <a href="/skills/${skill.name}/edit">Edit skill</a>
        <a href="/skills">Skills List</a>
    </div>
</div>
</body>
</html>