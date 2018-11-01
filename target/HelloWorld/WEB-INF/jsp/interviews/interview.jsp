<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${interview.planDate}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
    <div id="c1">
        <p>Plan date: </p>
        <p>Fact date: </p>
        <p>Candidate: </p>
        <p>Vacancy: </p>
    </div>

    <div id="c2">
        <p>${interview.planDate}</p>
        <p>${interview.factDate}</p>
        <p><a href="/candidates/${interview.candidate.id}">${interview.candidate.surname} ${interview.candidate.name}</a></p>
        <p><a href="/vacancies/${interview.vacancy.id}">${interview.vacancy.position}</a></p>
    </div>

    <div id="c3" style="margin-top: 220px; text-align: center;">
        <a href="/interviews/${interview.id}/edit">Edit an interview</a>
        <a href="/interviews">Interviews List</a>
    </div>
</div>
</body>
</html>
