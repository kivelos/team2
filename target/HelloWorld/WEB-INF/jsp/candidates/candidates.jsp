<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidates</title>
</head>
<body>
<h1>Candidates List</h1>
Filtering
<form action="/candidates/filtering" method="post">
    Surname: <input type="text" name="surname"><br>
    Name: <input type="text" name="name"><br>
    Birthday: <input type="date" name="birthday"><br>
    Salary in dollars: <input type="number" name="salary_in_dollars" step="0.01" min="0.00"><br>
    Status:
    <select name="state">
        <c:forEach var="state" items="${states}">
            <option value="${state.name}">${state.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit">
</form>

<table border="4">
    <tr>
        <td>N</td>
        <td>
            Surname<br>
            <a href="/candidates?sort=asc&field=surname">sort asc</a><br>
            <a href="/candidates?sort=desc&field=surname">sort desc</a>
        </td>
        <td>
            Name<br>
            <a href="/candidates?sort=asc&field=name">sort asc</a><br>
            <a href="/candidates?sort=desc&field=name">sort desc</a>
        </td>
        <td>
            State<br>
            <a href="/candidates?sort=asc?field=candidate_state">sort asc</a><br>
            <a href="/candidates?sort=desc?field=candidate_state">sort desc</a>
        </td>
    </tr>
    <c:forEach var="candidate" items="${candidates_list}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/candidates/${candidate.id}">${candidate.surname}</a></td>
            <td>${candidate.name}</td>
            <td>${candidate.candidateState}</td>
        </tr>
    </c:forEach>
</table>

<br><br><br>
Add new candidate<br>
<form action="/candidates" method="post">
    Surname: <input type="text" name="surname"><br>
    Name: <input type="text" name="name"><br>
    Birthday: <input type="date" name="birthday"><br>
    Salary in dollars: <input type="number" name="salary_in_dollars" step="0.01" min="0.00"><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
