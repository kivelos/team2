<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Candidates</title>
</head>
<body>
<h1>Candidates List</h1>
<table>
    <c:forEach var="candidate" items="${candidates_list}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td><a href="/candidates/${candidate.id}">${candidate.surname} ${candidate.name}</a></td>
            <td>${candidate.birthday}</td>
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
