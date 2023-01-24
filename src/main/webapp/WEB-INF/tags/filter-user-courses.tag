<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="filterdiv">
	<ul class="filter-list">
		<li class=${param.tab == "now" ? "filter-list-active" : "filter-list-item"}>
			<a href="controller?command=myprofile&tab=now&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="filter_user_courses.current" /></a>
		</li>
		<li class=${param.tab == "future" ? "filter-list-active" : "filter-list-item"}>
			<a href="controller?command=myprofile&tab=future&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="filter_user_courses.future" /></a>
		</li>
		<li class=${param.tab == "past" ? "filter-list-active" : "filter-list-item"}>
			<a href="controller?command=myprofile&tab=past&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="filter_user_courses.past" /></a>
		</li>
	</ul>
</div>
