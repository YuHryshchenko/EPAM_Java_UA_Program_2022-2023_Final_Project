<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${param.locale}" scope="session" />
<fmt:setBundle basename="resources" />
<c:set var="currentLocale" value="${param.locale}" scope="session" />

<%-- <jsp:forward page="index.jsp" /> --%>
<%-- <jsp:forward page="${param.current_url}" /> --%>

<% String current_url = request.getParameter("current_url"); response.sendRedirect(current_url); %>
