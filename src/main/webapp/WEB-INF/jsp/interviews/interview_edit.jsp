<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interview Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS"/>
    <link href="${entityEditCSS}" rel="stylesheet">
</head>

<body>
<div id="content">
    <h1>Interview Edit</h1>
    <form action="/interviews/${interview.id}/edit" method="post">
        Plan Date:  <input type="datetime-local" name="plan_date" value="${interview.planDate.toLocalDateTime()}"><br>
        Fact Date:  <input type="datetime-local" name="fact_date" value="${interview.factDate.toLocalDateTime()}"><br>
        Candidate:
        <select name="candidate">
            <c:forEach var="candidate" items="${candidates}">
                <c:choose>
                    <c:when test="${candidate.id ==interview.candidate.id}">
                        <option value="${candidate.id}" selected>${candidate.surname} ${candidate.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${candidate.id}">${candidate.surname} ${candidate.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        Vacancy:
        <select name="vacancy">
            <c:forEach var="vacancy" items="${vacancies}">
                <c:choose>
                    <c:when test="${vacancy.id ==interview.vacancy.id}">
                        <option value="${vacancy.id}" selected>${vacancy.position}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${vacancy.id}">${vacancy.position}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
