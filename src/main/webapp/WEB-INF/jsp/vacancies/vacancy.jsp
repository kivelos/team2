<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${vacancy.position}</title>
</head>
<body>
<h1>${vacancy.position}</h1>
Salary From: ${vacancy.salaryInDollarsFrom}<br>
Salary To: ${vacancy.salaryInDollarsTo}<br>
State: ${vacancy.vacancyState}<br>
Experience: ${vacancy.experienceYearsRequire} years

<a href="/vacancies/${vacancy.id}/edit">Edit vacancy</a>
<a href="/vacancies">Vacancies List</a>
</body>
</html>
