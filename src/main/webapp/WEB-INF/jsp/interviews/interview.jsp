<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${interview.planDate} ${interview.factDate}</title>
</head>
<body>
<h1>${candidate.planDate} ${candidate.factDate}</h1>
candidate_id: ${interview.candidateId} candidate_text: ${interview.candidate_text}<br>
vacancy_id: ${interview.vacancyId} vacancy_text: ${interview.vacancy_text}<br>

<a href="/interviews/${interview.id}/edit">Edit an interview</a>
<a href="/interviews">Interviews List</a>
</body>
</html>