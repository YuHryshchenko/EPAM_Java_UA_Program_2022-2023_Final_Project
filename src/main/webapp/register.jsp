<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="register_jsp.title" scope="page" />
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
					<h3><fmt:message key="register_jsp.h3" /></h3>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="register" />
						<div class="form-group">
							<label for="input-login"><fmt:message key="register_jsp.login" /></label>
							<input type="text" name="login" pattern="[a-zA-Z0-9]{5,20}" class="form-control" id="input-login" required="required" />
						</div>
						<div class="form-group">
							<label for="input-password"><fmt:message key="register_jsp.password" /></label>
							<input type="password" name="password" pattern="[a-zA-Z0-9]{5,20}" class="form-control" id="input-password" required="required" />
						</div>
						<div class="form-group">
							<label for="input-email"><fmt:message key="register_jsp.email" /></label>
							<input type="email" name="email" class="form-control" id="input-email" required="required" />
						</div>
						<div class="form-group">
							<label for="input-first-name"><fmt:message key="register_jsp.firstname" /></label>
							<input type="text" name="first-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'-]{3,20}" class="form-control" id="input-first-name" required="required" />
						</div>
						<div class="form-group">
							<label for="input-last-name"><fmt:message key="register_jsp.lastname" /></label>
							<input type="text" name="last-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'-]{3,20}" class="form-control" id="input-last-name" required="required" />
						</div>
						<c:if test="${role.getName() == 'admin'}">
							<div class="form-group">
								<label for="input-role"><fmt:message key="register_jsp.role" /></label>
								<select name="role-id" class="form-control" id="input-role" required="required">
									<option value="1" selected="selected"><fmt:message key="register_jsp.teacher" /></option>
									<option value="2"><fmt:message key="register_jsp.student" /></option>
								</select>
							</div>
						</c:if>
						<input type="reset" class="btn btn-light my-btn" value="<fmt:message key="register_jsp.cancel" />" />
						<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="register_jsp.submit" />" />
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
