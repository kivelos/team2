<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${candidate.surname} ${candidate.name}</title>
</head>
<body>
<h1>${candidate.surname} ${candidate.name}</h1>
Birthday: ${candidate.birthday}<br>
Status: ${candidate.candidateState}<br>

<a href="/candidates/${candidate.id}/edit">Edit candidate</a>
<a href="/candidates">Candidates List</a>
</body>
</html>
