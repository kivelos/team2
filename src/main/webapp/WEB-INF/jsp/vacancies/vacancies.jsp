<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vacancies</title>
</head>
<body>
<h1>Vacancies List</h1>
Filtering
<form action="/vacancies/filtering" method="post">
    Position: <input type="text" name="position"><br>
    Salary in dollars from: <input type="number" name="salary_in_dollars_from" step="0.01" min="0.00"><br>
    Salary in dollars to: <input type="number" name="salary_in_dollars_to" step="0.01" min="0.00"><br>
    State:
    <select name="state">
        <option value="">Choose state</option>
        <c:forEach var="state" items="${states}">
            <option value="${state.name()}">${state.name()}</option>
        </c:forEach>
    </select><br>
    Experience In Years: <input type="number" name="experience_years_require" step="0.01" min="0.00"><br>
    Developer:
    <select name="developer">
        <option value="">Choose developer</option>
        <c:forEach var="user" items="${users_list}">
            <option value="${user.id}">${user.surname} ${user.name}</option>
        </c:forEach>
    </select>

    <input type="submit" value="Submit">
</form>

<table border="4">
    <tr>
        <td>N</td>
        <td>
            Position<br>
            <a href="/vacancies?sort=asc&field=position">sort asc</a><br>
            <a href="/vacancies?sort=desc&field=position">sort desc</a>
        </td>
        <td>
            Salary from<br>
            <a href="/vacancies?sort=asc&field=salary_in_dollars_from">sort asc</a><br>
            <a href="/vacancies?sort=desc&field=salary_in_dollars_from">sort desc</a>
        </td>
        <td>
            Salary to<br>
            <a href="/vacancies?sort=asc&field=salary_in_dollars_to">sort asc</a><br>
            <a href="/vacancies?sort=desc&field=salary_in_dollars_to">sort desc</a>
        </td>
        <td>
            State<br>
            <a href="/vacancies?sort=asc&field=vacancy_state">sort asc</a><br>
            <a href="/vacancies?sort=desc&field=vacancy_state">sort desc</a>
        </td>
        <td>
            Experience<br>
            <a href="/vacancies?sort=asc&field=experience_years_require">sort asc</a><br>
            <a href="/vacancies?sort=desc&field=experience_years_require">sort desc</a>
        </td>
    </tr>
    <c:forEach var="vacancy" items="${vacancies_list}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/vacancies/${vacancy.id}">${vacancy.position}</a></td>
            <td>${vacancy.salaryInDollarsFrom}</td>
            <td>${vacancy.salaryInDollarsTo}</td>
            <td>${vacancy.vacancyState}</td>
            <td>${vacancy.experienceYearsRequire}</td>
        </tr>
    </c:forEach>
</table>

Page ${page}
<a href="/vacancies/page/${page - 1}">Previous</a>
<a href="/vacancies/page/${page + 1}">Next</a>

<br><br>
Add new vacancy<br>
<form action="/vacancies" method="post">
    Position: <input type="text" name="position"><br>
    Salary in dollars from: <input type="number" name="salary_in_dollars_from" step="0.01" min="0.00"><br>
    Salary in dollars to: <input type="number" name="salary_in_dollars_to" step="0.01" min="0.00"><br>
    State:
    <select name="state">
        <c:forEach var="state" items="${states}">
            <option value="${state.name()}">${state.name()}</option>
        </c:forEach>
    </select><br>
    Experience In Years: <input type="number" name="experience_years_require" step="0.01" min="0.00"><br>
    Developer:
    <select name="developer">
        <c:forEach var="user" items="${users_list}">
            <option value="${user.id}">${user.surname} ${user.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit">
</form>

${error}
<a href="..">Homepage</a>
</body>
</html>
