<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedbacks</title>
</head>
<body>
<h1>Feedbacks List</h1>
Filtering
<form action="/feedbacks/filtering" method="post">
    Interview:
    <select name="interview">
        <option value="">Choose interview</option>
        <c:forEach var="interview" items="${interviews_list}">
            <option value="${interview.id}">${interview.candidate.surname} - ${interview.vacancy.position}</option>
        </c:forEach>
    </select>
    Interviewer:
    <select name="interviewer">
        <option value="">Choose interviewer</option>
        <c:forEach var="user" items="${users_list}">
            <option value="${user.id}">${user.surname} ${user.name}</option>
        </c:forEach>
    </select>
    Reason:
    <input type="text" name="reason"><br>
    State:
    <select name="state">
        <option value="">Choose state</option>
        <c:forEach var="state" items="${states}">
            <option value="${state.name}">${state.name}</option>
        </c:forEach>
    </select><br>
    <input type="submit" value="Submit">
</form>

<table border="4">
    <tr>
        <td>N</td>
        <td>
            Candidate<br>
        </td>
        <td>
            Vacancy<br>
        </td>
        <td>
            Interviewer<br>
        </td>
        <td>
            Reason<br>
            <a href="/feedbacks?sort=asc&field=reason">a</a>/
            <a href="/feedbacks?sort=desc&field=reason">d</a>
        </td>
        <td>
            State<br>
            <a href="/feedbacks?sort=asc&field=feedback_state">a</a>/
            <a href="/feedbacks?sort=desc&field=feedback_state">d</a>
        </td>
    </tr>
    <c:forEach var="feedback" items="${feedbacks_list}" varStatus="status">
        <tr>
            <td><a href="/feedbacks/${feedback.id}">${status.count}</a> </td>
            <td>${feedback.interview.candidate.surname}</td>
            <td>${feedback.interview.vacancy.position}</td>
            <td><a href="/users/${feedback.interviewer.id}">${feedback.interviewer.surname} ${feedback.interviewer.name}</a></td>
            <td>${feedback.reason}</td>
            <td>${feedback.feedbackState}</td>
        </tr>
    </c:forEach>
</table>

Page ${page}
<a href="/feedbacks/page/${page - 1}">Previous</a>
<a href="/feedbacks/page/${page + 1}">Next</a>

<br><br>
Add new Interview Feedback<br>
<form action="/feedbacks" method="post">
    Interview:
    <select name="interview">
        <c:forEach var="interview" items="${interviews_list}">
            <option value="${interview.id}">${interview.candidate.surname} - ${interview.vacancy.position}</option>
        </c:forEach>
    </select>
    Interviewer:
    <select name="interviewer">
        <c:forEach var="user" items="${users_list}">
            <option value="${user.id}">${user.surname} ${user.name}</option>
        </c:forEach>
    </select>
    Reason:
    <input type="text" name="reason"><br>
    State:
    <select name="state">
        <c:forEach var="state" items="${states}">
            <option value="${state.name}">${state.name}</option>
        </c:forEach>
    </select><br>
    <input type="submit" value="Submit">
</form>

${error}
<a href="..">Homepage</a>
</body>
</html>
