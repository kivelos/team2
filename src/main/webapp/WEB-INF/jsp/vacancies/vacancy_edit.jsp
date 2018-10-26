<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vacancy Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS"/>
    <link href="${entityEditCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
    <h1>Vacancy Edit</h1>
    <form action="/vacancies/${vacancy.id}/edit" method="post">
        Position: <input type="text" name="position" value="${vacancy.position}"><br>
        Salary in dollars from: <input type="number" name="salary_in_dollars_from"
                                       value="${vacancy.salaryInDollarsFrom}" step="0.01" min="0.00"><br>
        Salary in dollars to: <input type="number" name="salary_in_dollars_to"
                                     value="${vacancy.salaryInDollarsTo}" step="0.01" min="0.00"><br>
        State:
        <select name="state">
            <c:forEach var="state" items="${states}">
                <c:choose>
                    <c:when test="${state.name() == vacancy.vacancyState}">
                        <option value="${state.name()}" selected>${state.name()}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${state.name()}">${state.name()}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select><br>
        Experience In Years: <input type="number" name="experience_years_require"
                                    value="${vacancy.experienceYearsRequire}"
                                    step="0.01" min="0.00"><br>
        Developer:
        <select name="developer">
            <c:forEach var="user" items="${users}">
                <c:choose>
                    <c:when test="${user.id == vacancy.developer.id}">
                        <option value="${user.id}" selected>${user.surname} ${user.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${user.id}">${user.surname} ${user.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
