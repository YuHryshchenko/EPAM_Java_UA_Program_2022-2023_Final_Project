<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<!doctype html>
<html>
<fmt:message var="title" key="users_jsp.title" scope="page" />
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
					<h3><fmt:message key="users_jsp.h3" /></h3>

					<div class="filterdiv">
						<ul class="filter-list">
							<li class=${param.tab == "teachers" ? "filter-list-active" : "filter-list-item"}>
								<a href="controller?command=getallusers&tab=teachers&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="users_jsp.teachers" /></a>
							</li>
							<li class=${param.tab == "students" ? "filter-list-active" : "filter-list-item"}>
								<a href="controller?command=getallusers&tab=students&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="users_jsp.students" /></a>
							</li>
						</ul>
					</div>

					<div class="sortdiv">
						<fmt:message key="users_jsp.sortby" /> <a href="controller?command=getallusers&tab=${param.tab}&sort=az&page=${param.page}" class="sortlink">A-Z</a> | 
						<a href="controller?command=getallusers&tab=${param.tab}&sort=za&page=${param.page}" class="sortlink">Z-A</a>
					</div>

					<ul class="list-group">
						<c:forEach items="${list}" var="user">
							<li class="list-group-item"><a href="controller?command=edituseropen&id=${user.id}" class="regularlink" title="Edit user">${user}</a></li>
						</c:forEach>
					</ul>

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
