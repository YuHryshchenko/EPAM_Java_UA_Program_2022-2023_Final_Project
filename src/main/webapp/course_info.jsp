<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="course_info_jsp.title" scope="page" />
<head>
	<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
	<nav class="navbar navbar-expand-lg fixed-top ">
		<%@ include file="/WEB-INF/jspf/navbar.jspf" %>
	</nav>
	<header class="header">
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
	</header>
	<main class="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-8 divmain">
					<h3><fmt:message key="course_info_jsp.h3" /></h3>
					<div>
						<fmt:message key="course_info_jsp.course_title" /> ${course.title}<br/>
						<fmt:message key="course_info_jsp.theme" /> ${course.theme}<br/>
						<fmt:message key="course_info_jsp.start" /> ${course.startDate}<br/>
						<fmt:message key="course_info_jsp.finish" /> ${course.finishDate}<br/>
						<fmt:message key="course_info_jsp.length" /> ${course.length} <fmt:message key="course_info_jsp.days" /><br/>
						<fmt:message key="course_info_jsp.students" /> ${course.students}<br/>
					</div>
					<div class="courseactions">
						<c:choose>
							<c:when test="${role.getName() == 'admin'}">

								<p>${infoMessage}</p>
								<form action="controller" method="get" class="edit-delete-course">
									<input type="hidden" name="command" value="edit_course_open" />
									<input type="hidden" name="course-id" value="${course.id}" />
									<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="course_info_jsp.edit" />" />
								</form>

								<form action="controller" method="post" class="edit-delete-course">
									<input type="hidden" name="command" value="remove_course" />
									<input type="hidden" name="course-id" value="${course.id}" />
									<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="course_info_jsp.remove" />" />
								</form>

							</c:when>
							<c:when test="${role.getName() == 'teacher'}">

								<p>${infoMessage}</p>
								<c:choose>
									<c:when test="${authorizedUser.id == course.teacherId && course.startDate < currentDate && course.finishDate > currentDate}">
										<fmt:message key="course_info_jsp.teacher_inprogress" />
									</c:when>
									<c:when test="${authorizedUser.id == course.teacherId && course.startDate > currentDate}">
										<fmt:message key="course_info_jsp.teacher_notstarted" />
									</c:when>
									<c:when test="${authorizedUser.id == course.teacherId && course.finishDate < currentDate}">
										<c:set var="course" value="${course}" scope="session" />
										<form action="controller" method="post" class="edit-delete-course">
											<input type="hidden" name="command" value="mark_open" />
											<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="course_info_jsp.edit_marks" />" />
										</form>
									</c:when>
									<c:otherwise>
										<fmt:message key="course_info_jsp.teacher_not" />
									</c:otherwise>
								</c:choose>

							</c:when>
							<c:when test="${role.getName() == 'student'}">

								<p>${infoMessage}</p>
								<c:set var="found" value="0" />
								<c:forEach items="${fullList}" var="courseStudent">
									<c:if test="${course.id == courseStudent.course.id && course.startDate < currentDate && course.finishDate > currentDate}">
										<fmt:message key="course_info_jsp.student_inprogress" />
										<c:set var="found" value="1" />
									</c:if>
									<c:if test="${course.id == courseStudent.course.id && course.startDate > currentDate}">
										<fmt:message key="course_info_jsp.student_notstarted" />
										<c:set var="found" value="1" />
									</c:if>
									<c:if test="${course.id == courseStudent.course.id && course.finishDate < currentDate}">
										<fmt:message key="course_info_jsp.student_mark" /> ${courseStudent.mark}
										<c:set var="found" value="1" />
									</c:if>
								</c:forEach>

								<c:if test="${found == 0}">
									<c:if test="${course.startDate > currentDate}">
										<form action="controller" method="post" class="edit-delete-course">
											<input type="hidden" name="command" value="subscribe" />
											<input type="hidden" name="course-id" value="${course.id}" />
											<input type="hidden" name="student-id" value="${authorizedUser.id}" />
											<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="course_info_jsp.subscribe" />" />
										</form>
									</c:if>
									<c:if test="${course.startDate < currentDate}">
										<fmt:message key="course_info_jsp.subscribe_imp" />
									</c:if>
								</c:if>
								<c:remove var="found" />

							</c:when>
							<c:otherwise>

								<c:choose>
									<c:when test="${course.startDate > currentDate}">
										<fmt:message key="course_info_jsp.guest" /> <a href="register.jsp" class="regular_link" title="Registration"><fmt:message key="course_info_jsp.register" /></a>
									</c:when>
									<c:otherwise>
										<fmt:message key="course_info_jsp.subscribe_imp" />
									</c:otherwise>
								</c:choose>

							</c:otherwise>
						</c:choose>
					</div>
					<c:remove var="infoMessage" />
					<c:remove var="editCourse" />
				</div>
				<div class="col-md-3 divlogin">
					<%@ include file="/WEB-INF/jspf/divlogin.jspf" %>
				</div>
				<div class="col-md-1 col-sm-0"></div>
			</div>
		</div>
	</main>

	<footer class="fixed-bottom footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</footer>
</body>
</html>
