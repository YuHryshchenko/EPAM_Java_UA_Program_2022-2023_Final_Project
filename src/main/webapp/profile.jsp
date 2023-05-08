<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="profile_jsp.title" scope="page" />
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
					<c:choose>
						<c:when test="${role.getName() == 'admin'}">
							<h3><fmt:message key="profile_jsp.h3_admin" /></h3>
							<ul class="list-group">
								<li class="list-group-item">
									<a href="register.jsp" class="regular_link" title="Register a user"><fmt:message key="profile_jsp.register_user" /></a>
								</li>
								<li class="list-group-item">
									<a href="controller?command=get_all_users&tab=teachers&sort=az&page=1" class="regular_link" title="View and edit users"><fmt:message key="profile_jsp.view_users" /></a>
								</li>
								<li class="list-group-item">
									<a href="controller?command=create_course_open" class="regular_link" title="Create a new course"><fmt:message key="profile_jsp.create_course" /></a>
								</li>
								<li class="list-group-item">
									<a href="controller?command=get_all_courses&teacher=0&theme=all&sort=none&page=1" class="regular_link" title="View and edit courses"><fmt:message key="profile_jsp.view_courses" /></a>
								</li>
							</ul>
						</c:when>

						<c:when test="${role.getName() == 'teacher'}">
							<h3><fmt:message key="profile_jsp.h3_courses" /></h3>

							<t:filter-user-courses />
							<t:sort-courses />
							<t:show-courses-list />
							<t:pagination />

						</c:when>

						<c:when test="${role.getName() == 'student'}">
							<h3><fmt:message key="profile_jsp.h3_courses" /></h3>

							<t:filter-user-courses />
							<t:sort-courses />

							<ctg:show-student-courses /><br />

							<t:pagination />
						</c:when>
					</c:choose>
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
