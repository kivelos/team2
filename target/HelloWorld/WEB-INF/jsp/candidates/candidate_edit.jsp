<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidate Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS" />
    <link href="${entityEditCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
<h1>Candidate Edit</h1>
<form action="/candidates/${candidate.id}/edit" method="post">
    Surname: <input type="text" name="surname" value="${candidate.surname}"><br>
    Name: <input type="text" name="name" value="${candidate.name}"><br>
    Birthday: <input type="date" name="birthday" value="${candidate.birthday}"><br>
    Salary in dollars: <input type="number" name="salary_in_dollars" step="0.01" min="0.00"
                              value="${candidate.salaryInDollars}"><br>
    Status: <select name="state">
    <option value="" selected>Choose State</option>
    <c:forEach var="state" items="${states}">
        <c:choose>
            <c:when test="${state.name == candidate.candidateState}">
                <option value="${state.name}" selected>${state.name}</option>
            </c:when>
            <c:otherwise>
                <option value="${state.name}">${state.name}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>
    <input type="submit" value="Submit">
</form>
</div>
</body>
</html>
