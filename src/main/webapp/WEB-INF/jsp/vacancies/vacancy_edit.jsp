<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vacancy Edit</title>
</head>
<body>
<h1>Vacancy Edit</h1>
<form action="/vacancies" method="post">
    Position: <input type="text" name="position" value="${vacancy.position}"><br>
    Salary in dollars from: <input type="number" name="salary_in_dollars_from"
                                   value="${vacancy.salaryInDollarsFrom}" step="0.01" min="0.00"><br>
    Salary in dollars to: <input type="number" name="salary_in_dollars_to"
                                 value="${vacancy.salaryInDollarsTo}" step="0.01" min="0.00"><br>
    State:
    <select name="state">
        <c:forEach var="state" items="${states}">
            <option value="${state}">${state}</option>
        </c:forEach>
    </select>
    Experience In Years: <input type="number" name="experience" value="${vacancy.experienceYearsRequire}" step="0.01" min="0.00"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
