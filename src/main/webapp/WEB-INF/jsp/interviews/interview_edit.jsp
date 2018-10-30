<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vacancy Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS"/>
    <link href="${entityEditCSS}" rel="stylesheet">
</head>

<body>
<div id="content">
    <h1>Interview Edit</h1>
    <form action="/interviews/${interview.id}/edit" method="post">
        Plan Date:  <input type="datetime-local" name="plan_date" value="${interview.plan_date}"><br>
        Fact Date:  <input type="datetime-local" name="fact_date" value="${interview.fact_date}"><br>
        Candidate:
        <select name="candidate">
            <c:forEach var="candidate" items="${candidates_list}">
                <c:choose>
                        <option value="${candidate.id}" selected>${candidate.surname} ${candidate.name}</option>
                </c:choose>
            </c:forEach>
        </select>
        <select name="vacancy">
            <c:forEach var="vacancy" items="${vacancies_list}">
                <c:choose>
                    <option value="${vacancy.id}" selected>${vacancy.position}</option>
                </c:choose>
            </c:forEach>
        </select>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
