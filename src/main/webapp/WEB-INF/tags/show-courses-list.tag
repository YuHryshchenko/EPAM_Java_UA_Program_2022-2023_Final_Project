<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ul class="list-group">
	<c:forEach items="${list}" var="course">
		<li class="list-group-item">
			<a href="controller?command=courseinfo&course-id=${course.id}" class="regularlink" title="Look course info.">
				${course} (${course.length} <fmt:message key="show_courses.days" />, ${course.students} <fmt:message key="show_courses.students" />)
			</a>
		</li>
	</c:forEach>
</ul>
