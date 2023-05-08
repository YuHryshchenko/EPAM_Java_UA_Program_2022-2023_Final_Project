<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="mark_sheet_jsp.title" scope="page" />
<head>
	<c:if test="${authorizedUser.id != course.teacherId}">
		<fmt:message var="errorMessage" key="mark_sheet_jsp.access_denied" scope="session" />
		<c:redirect url="error.jsp" />
	</c:if>
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
					<h3><fmt:message key="mark_sheet_jsp.h3" /></h3>
						<div class="edit_marks">${course}</div>
						<form action="controller" method="post">
							<input type="hidden" name="command" value="mark_save" />
							<input type="hidden" name="course-id" value="${course.id}" />
							<table>
								<tr><th><fmt:message key="mark_sheet_jsp.student" /></th><th><fmt:message key="mark_sheet_jsp.mark" /></th></tr>
									<c:forEach items="${studentsOfCourse}" var="studentOfCourse">
										<tr><td><input type="hidden" name="student-id" value="${studentOfCourse.student.id}">${studentOfCourse.student}</td>
										<td><input type="text" name="mark" maxlength="3" size="3" value="${studentOfCourse.mark}"></td></tr>
									</c:forEach>
							</table>
							<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="mark_sheet_jsp.submit" />" />
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
