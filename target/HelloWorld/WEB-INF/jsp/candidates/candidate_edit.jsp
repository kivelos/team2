<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidate Edit</title>
</head>
<body>
<h1>Candidate Edit</h1>
<form action="/candidates/${candidate.id}/edit" method="post">
    Surname: <input type="text" name="surname" value="${candidate.surname}"><br>
    Name: <input type="text" name="name" value="${candidate.name}"><br>
    Birthday: <input type="date" name="birthday" value="${candidate.birthday}"><br>
    Salary in dollars: <input type="number" name="salary_in_dollars" step="0.01" min="0.00"
                              value="${candidate.salaryInDollars}"><br>
    Status: <select name="state">
    <c:forEach var="state" items="${states}">
        <option value="${state.name}"></option>
    </c:forEach>
</select>
    <input type="submit" value="Submit">
</form>
</body>
</html>
