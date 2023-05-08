<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<fmt:message var="title" key="contact_jsp.title" scope="page" />
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
					<h3><fmt:message key="contact_jsp.h3" /></h3>

					<div>${report}</div>
					<c:remove var="report" />

					<div><fmt:message key="contact_jsp.info" /></div>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="write_admin" />
						<div class="form-group">
							<label for="input-name"><fmt:message key="contact_jsp.name" /></label>
							<input type="text" name="name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ '-]{3,50}" class="form-control" id="input-name" required="required" />
						</div>
						<div class="form-group">
							<label for="input-email"><fmt:message key="contact_jsp.email" /></label>
							<input type="email" name="email" class="form-control" id="input-email" required="required" />
						</div>
						<div class="form-group">
							<label for="input-subject"><fmt:message key="contact_jsp.subject" /></label>
							<input type="text" name="subject" class="form-control" id="input-subject" required="required" />
						</div>
						<div class="form-group">
							<label for="input-message"><fmt:message key="contact_jsp.main_message" /></label>
							<textarea name="message" rows="5" class="form-control" id="input-message" required="required"></textarea>
						</div>
						<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="contact_jsp.send" />" />
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
