<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="pagination">
	<c:choose>
		<c:when test="${param.command == 'get_all_courses'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message 						key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message 						key="pagination.next" /></a>
			</c:if>
		</c:when>
		<c:when test="${param.command == 'my_profile'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=my_profile&tab=${param.tab}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=my_profile&tab=${param.tab}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message key="pagination.next" /></a>
			</c:if>
		</c:when>
		<c:when test="${param.command == 'get_all_users'}">
			<c:if test="${not empty prev}">
				<a href="controller?command=get_all_users&tab=${param.tab}&sort=${param.sort}&page=${prev}" class="page-link sortlink"><fmt:message key="pagination.prev" /></a>
			</c:if>
			<c:if test="${not empty next}">
				<a href="controller?command=get_all_users&tab=${param.tab}&sort=${param.sort}&page=${next}" class="page-link sortlink"><fmt:message key="pagination.next" /></a>
			</c:if>
		</c:when>
	</c:choose>
	<div class="sortdiv">${nothingFound}</div>
</div>
