<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidate Edit</title>
</head>
<body>
<h1>Candidate Edit</h1>
<form action="/candidates/edit" method="post">
    Surname: <input type="text" name="surname" value="${candidate.surname}"><br>
    Name: <input type="text" name="name"><br>
    Birthday: <input type="date" name="birthday"><br>
    Salary in dollars: <input type="number" name="salary_in_dollars" step="0.01" min="0.00"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
