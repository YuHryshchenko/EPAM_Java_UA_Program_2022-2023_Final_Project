<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="edit_course_jsp.title" scope="page" />
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
				<div class="col-md-5 divmain">
					<h3><fmt:message key="edit_course_jsp.h3" /></h3>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="edit_course_save" />
						<div class="form-group">
							<label for="input-title"><fmt:message key="edit_course_jsp.course_title" /></label>
							<input type="text" name="title" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9 '-]{5,255}" class="form-control" id="input-title" value="${editCourse.title}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-theme"><fmt:message key="edit_course_jsp.theme" /></label>
							<input type="text" name="theme" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9 '-]{5,255}" class="form-control" id="input-theme" value="${editCourse.theme}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-start-date"><fmt:message key="edit_course_jsp.start" /></label>
							<input type="date" name="start-date" class="form-control" id="input-start-date" value="${editCourse.startDate}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-finish-date"><fmt:message key="edit_course_jsp.finish" /></label>
							<input type="date" name="finish-date" class="form-control" id="input-finish-date" value="${editCourse.finishDate}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-teacher"><fmt:message key="edit_course_jsp.teacher" /></label>
							<select name="teacher-id" class="form-control" id="input-teacher" required="required">
								<c:forEach items="${teachers}" var="teacher">
									<option value="${teacher.id}" ${editCourse.teacherId == teacher.id ? "selected='selected'" : ""} >${teacher}</option>
								</c:forEach>
							</select>
						</div>
						<input type="reset" class="btn btn-light my-btn" value="<fmt:message key="edit_course_jsp.cancel" />" />
						<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="edit_course_jsp.submit" />" />
					</form>
				</div>
				<div class="col-md-3 col-sm-0"></div>
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
