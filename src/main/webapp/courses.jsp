<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<!doctype html>
<html>
<fmt:message var="title" key="courses_jsp.title" scope="page" />
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
					<h3><fmt:message key="courses_jsp.h3" /></h3>
					<form action="controller" method="get" class="form-inline my-form">
						<input type="hidden" name="command" value="getallcourses" />
						<div class="form-group">
							<fmt:message key="courses_jsp.teacher" /> <select name="teacher" class="form-control">
								<option value="0"><fmt:message key="courses_jsp.all_teachers" /></option>
								<c:forEach items="${teachers}" var="teacher">
									<option value="${teacher.id}" ${param.teacher == teacher.id ? "selected='selected'" : ""}>${teacher}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<fmt:message key="courses_jsp.theme" /> <select name="theme" class="form-control">
								<option value="all"><fmt:message key="courses_jsp.all_themes" /></option>
								<c:forEach items="${themes}" var="theme">
									<option value="${theme}" ${param.theme == theme ? "selected='selected'" : ""}>${theme}</option>
								</c:forEach>
							</select>
						</div>
						<input type="hidden" name="sort" value="${param.sort}" />
						<input type="hidden" name="page" value="1" />
						<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="courses_jsp.select" />" />
					</form>

					<t:sort-courses />

					<t:show-courses-list />

					<t:pagination />

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
