<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interviews</title>
</head>
<body>
Filtering
<form action="/interviews/filtering" method="post">
    planDate: <input type="text" name="planDate"><br>
    factDate: <input type="text" name="factDate"><br>
    id_candidate: <input type="number" name="id_candidate"><br>
    id_vacancy: <input type="number" name="id_vacancy"><br>
    <input type="submit" value="Submit">
</form>
<table border="4">
    <tr>
        <td>№</td>
        <td>planDate<br>
            <a href="/interviews?sort=asc&field=planDate">a</a>/
            <a href="/interviews?sort=desc&field=planDate">d</a>
        </td>
        <td>factDate<br>
            <a href="/interviews?sort=asc&field=factDate">a</a>/
            <a href="/interviews?sort=desc&field=factDate">d</a>
        </td>
        <td>id_candidate<br>
            <a href="/interviews?sort=asc&field=id_candidate">a</a>/
            <a href="/interviews?sort=desc&field=id_candidate">d</a>
        </td>
        <td>id_vacancy<br>
            <a href="/interviews?sort=asc&field=id_vacancy">a</a>/
            <a href="/interviews?sort=desc&field=id_vacancy">d</a>
        </td>
    </tr>
    <c:forEach items="${interviews_list}" var="interview" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <!--<td><a href="/interviews/${interview.id}">${int.email}</a></td>-->
            <td>${interview.planDate}</td>
            <td>${interview.factDate}</td>
            <td>${interview.id_Candidate}</td>
        </tr>
    </c:forEach>
</table>
<form action="/interviews/page/${page+1}" method="post">
    <input type="submit" value="Next">
</form>
<form action="/interviews/page/${page-1}" method="post">
    <input type="submit" value="Previous">
</form>

<br><br><br>
Add new interview<br>
<form action="/interviews" method="post">
    planDate: <input type="text" name="planDate"><br>
    factDate: <input type="text" name="factDate"><br>
    id_candidate: <input type="number" name="id_candidate"><br>
    id_vacancy: <input type="number" name="id_vacancy"><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
