<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Skills</title>
</head>
<body>
<h1>Skills List</h1>
Filtering
<form action="/skills/filtering" method="post">
    Name: <input type="text" name="name"><br>
    <input type="submit" value="Submit">
</form>
<table border="4">
    <tr>
        <td>№</td>
        <td>Name<br>
            <a href="/skills?sort=asc&field=name">a</a>/
            <a href="/skills?sort=desc&field=name">d</a>
        </td>
    </tr>
    <c:forEach items="${skills_list}" var="skill" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/skills/${skill.name}">${skill.name}</a></td>
        </tr>
    </c:forEach>
</table>
<form action="/skills/page/${page+1}" method="post">
    <input type="submit" value="Next">
</form>
<form action="/skills/page/${page-1}" method="post">
    <input type="submit" value="Previous">
</form>
<form>
    <p><a href="/"><input type="button" value="FIRST PAGE"></a></p>
</form>
<br><br><br>
Add new skill<br>
<form action="/skills" method="post">
    Name: <input type="text" name="name"><br>
    <input type="submit" value="Submit">
</form>
<c:if test="${mistake!=null}">
    ${mistake} <br/>
</c:if>
</body>
</html>
