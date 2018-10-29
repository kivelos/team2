<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${skill.name}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
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