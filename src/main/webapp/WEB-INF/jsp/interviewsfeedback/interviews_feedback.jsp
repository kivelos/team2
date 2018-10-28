<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<header><a href="back">HR Application</a></header>
<div class="main-block">
    <h1 align="center">Show feedback table</h1>
    <p align="center"><a href="FeedbackCreate" class="create-button">Create feedback</a></p>
    <div class="filter-sort">
        Filter by:
        <a href="ViewFeedbackForm" class="filter-sort-item">Find all</a>
        <a href="FeedBackFilter?type=failure" class="filter-sort-item">failure</a>
        <a href="FeedBackFilter?type=success" class="filter-sort-item">success</a>
        <a href="FeedBackFilter?type=awaiting" class="filter-sort-item">awaiting</a>
    </div>
    <table border="1">
        <tr>
            <th>Reason</th>
            <th>FeedbackState</th>
            <th>Interviewer</th>
            <th>Interview</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="feedback" items="${list}">
            <tr>
                <td><c:out value="${feedback.reason}"></c:out></td>
                <td><c:out value="${feedback.feedbackState}"/></td>
                <td><c:out value="${feedback.id_Interviewer}"/></td>
                <td><c:out value="${feedback.id_Interview}"/></td>
                <td><a href="FeedBackEdit?id=${feedback.id}">Edit</a></td>
                <td><a href="FeedBackDelete?id=${feedback.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer> q </footer>
</body>
</html>