<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Skill Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS" />
    <link href="${entityEditCSS}" rel="stylesheet">
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