<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<fmt:message var="title" key="edit_user_jsp.title" scope="page" />
<head>
		<c:if test="${role.getName() != 'admin' && authorizedUser.id != editUser.id}">
            <fmt:message var="errorMessage" key="edit_user_jsp.access_denied" scope="session" />
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
					<h3><fmt:message key="edit_user_jsp.h3" /></h3>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="edit_user_save" />
						<div class="form-group">
							<label for="input-login"><fmt:message key="edit_user_jsp.login" /></label>
							<input type="text" name="login" pattern="[a-zA-Z0-9]{5,20}" class="form-control" id="input-login" value="${editUser.login}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-password"><fmt:message key="edit_user_jsp.password" /></label>
							<input type="password" name="password" pattern="[a-zA-Z0-9]{5,20}" class="form-control" id="input-password" />
						</div>
						<div class="form-group">
							<label for="input-email"><fmt:message key="edit_user_jsp.email" /></label>
							<input type="email" name="email" class="form-control" id="input-email" value="${editUser.email}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-first-name"><fmt:message key="edit_user_jsp.firstname" /></label>
							<input type="text" name="first-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'-]{3,20}" class="form-control" id="input-first-name" value="${editUser.firstName}" required="required" />
						</div>
						<div class="form-group">
							<label for="input-last-name"><fmt:message key="edit_user_jsp.lastname" /></label>
							<input type="text" name="last-name" pattern="[a-zA-Zа-яА-ЯіІїЇєЄ0-9'-]{3,20}" class="form-control" id="input-last-name" value="${editUser.lastName}" required="required" />
						</div>
						<c:if test="${role.getName() == 'admin'}">
							<div class="form-group">
								<label for="input-status"><fmt:message key="edit_user_jsp.status" /></label>
								<select name="status" class="form-control" id="input-status" required="required">
									<option value="active" ${editUser.status == 'active' ? "selected='selected'" : ""} ><fmt:message key="edit_user_jsp.active" /></option>
									<option value="banned" ${editUser.status == 'banned' ? "selected='selected'" : ""} ><fmt:message key="edit_user_jsp.banned" /></option>
								</select>
							</div>
						</c:if>
						<input type="reset" class="btn btn-light my-btn" value="<fmt:message key="edit_user_jsp.cancel" />" />
						<input type="submit" class="btn btn-light my-btn" value="<fmt:message key="edit_user_jsp.submit" />" />
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
