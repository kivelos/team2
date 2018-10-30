<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${vacancy.position}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
    <div id="c1">
        <p>Position: </p>
        <p>Salary From: </p>
        <p>Salary To: </p>
        <p>State: </p>
        <p>Experience: </p>
        <p>Developer: </p>
    </div>

    <div id="c2">
        <p>${vacancy.position}</p>
        <p>${vacancy.salaryInDollarsFrom}</p>
        <p>${vacancy.salaryInDollarsTo}</p>
        <p>${vacancy.vacancyState}</p>
        <p>${vacancy.experienceYearsRequire} years</p>
        <p><a href="/users/${vacancy.developer.id}">${vacancy.developer.surname} ${vacancy.developer.name}</a></p>
    </div>

    <div>
        <a href="/vacancies/${vacancy.id}/edit">Edit vacancy</a>
        <a href="/vacancies">Vacancies List</a>
    </div>
</div>
</body>
</html>
