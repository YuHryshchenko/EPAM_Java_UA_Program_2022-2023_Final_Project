<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pagination">
	<c:choose>
		<c:when test="${param.command == 'getallcourses'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=getallcourses&teacher=${param.teacher}&theme=${param.theme}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message 						key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=getallcourses&teacher=${param.teacher}&theme=${param.theme}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message 						key="pagination.next" /></a>
			</c:if>
		</c:when>
		<c:when test="${param.command == 'myprofile'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=myprofile&tab=${param.tab}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=myprofile&tab=${param.tab}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message key="pagination.next" /></a>
			</c:if>
		</c:when>
		<c:when test="${param.command == 'getallusers'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=getallusers&tab=${param.tab}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=getallusers&tab=${param.tab}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message key="pagination.next" /></a>
			</c:if>
		</c:when>
	</c:choose>
	<div class="sortdiv">${nothingFound}</div>
</div>
