<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${candidate.surname} ${candidate.name}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
</head>
<body>
<div id=content>
    <div id="c1">
        <p>Surname:</p>
        <p>Name: </p>
        <p>Birthday: </p>
        <p>Salaty: </p>
        <p>State: </p>
    </div>
    <div id="c2">
        <p> ${candidate.surname}</p>
        <p>${candidate.name}</p>
        <p>${candidate.birthday}</p>
        <p>${candidate.salaryInDollars}</p>
        <p> ${candidate.candidateState}</p>
    </div>

    <div id="s">
        <a href="/candidates/${candidate.id}/edit">Edit candidate</a>
        <a href="/candidates">Candidates List</a>
    </div>
</div>
</body>
</html>
