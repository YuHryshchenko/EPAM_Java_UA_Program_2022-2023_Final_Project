<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="sortdiv">
	<c:choose>
		<c:when test="${param.command == 'get_all_courses'}">
			<fmt:message key="sort_courses.sortby" /> <a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=az&page=${param.page}" 						class="sortlink">A-Z</a> |
			<a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=za&page=${param.page}" class="sortlink">Z-A</a> |
			<a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=length&page=${param.page}" class="sortlink"><fmt:message 								key="sort_courses.length" /></a> |
			<a href="controller?command=get_all_courses&teacher=${param.teacher}&theme=${param.theme}&sort=students&page=${param.page}" class="sortlink"><fmt:message 								key="sort_courses.students" /></a>
		</c:when>
		<c:when test="${param.command == 'my_profile'}">
			<fmt:message key="sort_courses.sortby" /> <a href="controller?command=my_profile&tab=${param.tab}&sort=az&page=${param.page}" class="sortlink">A-Z</a> |
			<a href="controller?command=my_profile&tab=${param.tab}&sort=za&page=${param.page}" class="sortlink">Z-A</a> |
			<a href="controller?command=my_profile&tab=${param.tab}&sort=length&page=${param.page}" class="sortlink"><fmt:message key="sort_courses.length" /></a> |
			<a href="controller?command=my_profile&tab=${param.tab}&sort=students&page=${param.page}" class="sortlink"><fmt:message key="sort_courses.students" /></a>
		</c:when>
	</c:choose>
</div>
