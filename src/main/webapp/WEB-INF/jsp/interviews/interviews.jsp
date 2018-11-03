<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interviews</title>
</head>
<body>
<h1>Interviews List</h1>
Filtering
<form action="/interviews/filtering" method="post">
    Plan Date:  <input type="datetime-local"  name="plan_date" value="${interview.planDate}"><br>
    Fact Date:  <input type="datetime-local"  name="fact_date" value="${interview.factDate}"><br>
    Candidate:
    <select name="candidate">
        <option value="">Choose candidate</option>
        <c:forEach var="candidate" items="${candidates_list}">
            <option value="${candidate.id}">${candidate.surname} ${candidate.name}</option>
        </c:forEach>
    </select><br>
    Vacancy:
    <select name="vacancy">
        <option value="">Choose vacancy</option>
        <c:forEach var="vacancy" items="${vacancies_list}">
            <option value="${vacancy.id}">${vacancy.position}</option>
        </c:forEach>
    </select>

    <input type="submit" value="Submit">
</form>

<table border="4">
    <tr>
        <td>N</td>
        <td>
            Plan date<br>
            <a href="/interviews?sort=asc&field=plan_date">sort asc</a><br>
            <a href="/interviews?sort=desc&field=plan_date">sort desc</a>
        </td>
        <td>
            Fact date<br>
            <a href="/interviews?sort=asc&field=fact_date">sort asc</a><br>
            <a href="/interviews?sort=desc&field=fact_date">sort desc</a>
        </td>
        <td>
            Candidate<br>
        </td>
        <td>
            Vacancy<br>
        </td>
    </tr>
    <c:forEach var="interview" items="${interviews_list}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/interviews/${interview.id}">${interview.planDate}</a></td>
            <td>${interview.factDate}</td>
            <td><a href="/candidates/${interview.candidate.id}">${interview.candidate.surname} ${interview.candidate.name}</a></td>
            <td><a href="/vacancies/${interview.vacancy.id}">${interview.vacancy.position}</a></td>
        </tr>
    </c:forEach>
</table>

Page ${page}
<a href="/interviews/page/${page - 1}">Previous</a>
<a href="/interviews/page/${page + 1}">Next</a>

<br><br>
Add new vacancy<br>
<form action="/interviews" method="post">
    Plan date: <input type="datetime-local" name="plan_date"><br>
    Fact date: <input type="datetime-local" name="fact_date"><br>
    Candidate:
    <select name="candidate">
        <c:forEach var="candidate" items="${candidates_list}">
            <option value="${candidate.id}">${candidate.surname} ${candidate.name}</option>
        </c:forEach>
    </select><br>
    Vacancy:
    <select name="vacancy">
        <c:forEach var="vacancy" items="${vacancies_list}">
            <option value="${vacancy.id}">${vacancy.position}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit">
</form>

${error}
<a href="..">Homepage</a>
</body>
</html>
