<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.manage" var="manage" />
<fmt:message key="local.create" var="create" />
<fmt:message key="local.delete" var="delete" />
<fmt:message key="local.notallowed" var="notallowed" />
<fmt:message key="local.destination" var="destination" />
<fmt:message key="local.destderr" var="destderr" />
<fmt:message key="local.nodestedit" var="nodestedit" />

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.manage" />
</jsp:include>
<body>

	<jsp:include page="elements/logo.jsp" />
	
	<h1>${fn:toUpperCase(manage)}</h1>
	<hr/>

	<c:choose>
		<c:when test="${(empty user) || !role.admin}">
			<h2>${notallowed}</h2>
		</c:when>
	
		<c:otherwise>
			<c:choose>
			<c:when test="${empty dests}">
				<h2>${nodestedit}</h2>
			</c:when>
			<c:otherwise>
				<form method="POST" action="manage">
				<fieldset>
					<jsp:include page="elements/destinations.jsp" />
					<input type="submit" value="${delete}" name="Delete" />
				</fieldset>
				</form>
			</c:otherwise>
			</c:choose>
			
			<form method="POST" action="manage">
			    <fieldset>
					<label for="dest">${destination}</label>
					<input type="text" name="dest" id="dest" required 
					pattern="\w{3,50}" title="${destderr}">
					<input type="submit" value="${create}" name="Create">
				</fieldset>
			</form>

			<c:if test="${not empty requestScope.error}">
				${requestScope.error.message}
			</c:if>
		</c:otherwise>
	</c:choose>
	<jsp:include page="elements/footer.jsp" />
</body>
</html>