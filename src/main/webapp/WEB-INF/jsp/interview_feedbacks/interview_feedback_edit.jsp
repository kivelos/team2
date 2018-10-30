<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vacancy Edit</title>
    <spring:url value="/resources/entity_edit_style.css" var="entityEditCSS"/>
    <link href="${entityEditCSS}" rel="stylesheet">
</head>
<body>
<div id="content">
    <h1>InterviewFeedback Edit</h1>
    <form action="/feedbacks/${interview_feedback.id}/edit" method="post">
        Interview:
        <select name="interview">
            <c:forEach var="interview" items="${all_interviews}">
                <c:choose>
                    <c:when test="${interview.id == interview_feedback.interview.id}">
                        <option value="${interview.id}" selected>${interview.candidate.surname} ${interview.vacancy.position}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${interview.id}">${interview.candidate.surname} ${interview.vacancy.position}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        Interviewer:
        <select name="developer">
            <c:forEach var="user" items="${all_developers}">
                <c:choose>
                    <c:when test="${user.id == interview_feedback.interviewer.id}">
                        <option value="${user.id}" selected>${user.surname} ${user.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${user.id}">${user.surname} ${user.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        Reason: <input type="text" name="reason" value="${interview_feedback.reason}"><br>
        State:
        <select name="state">
            <c:forEach var="state" items="${all_states}">
                <c:choose>
                    <c:when test="${state.name == interview_feedback.feedbackState}">
                        <option value="${state.name}" selected>${state.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${state.name}">${state.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select><br>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>