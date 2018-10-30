<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${interview_feedback.interview.candidate.surname} ${interview_feedback.interview.vacancy.position}</title>
    <spring:url value="/resources/entity_style.css" var="entityCSS" />
    <link href="${entityCSS}" rel="stylesheet">
</head>
<body>
<div id=content>
    <div id="c1">
        <p>Candidate:</p>
        <p>Vacancy: </p>
        <p>Interviewer</p>: </p>
        <p>Reason: </p>
        <p>State: </p>
    </div>
    <div id="c2">
        <p> ${interview_feedback.interview.candidate.surname}</p>
        <p>${interview_feedback.interview.vacancy.position}</p>
        <p>${interview_feedback.interviewer.surname} ${interview_feedback.interviewer.name}</p>
        <p>${interview_feedback.reason}</p>
        <p> ${interview_feedback.feedbackState}</p>
    </div>

    <div id="s">
        <a href="/feedbacks/${interview_feedback.id}/edit">Edit Interview Feedback</a>
        <a href="/feedbacks">Interview Feedback List</a>
    </div>
</div>
</body>
</html>
