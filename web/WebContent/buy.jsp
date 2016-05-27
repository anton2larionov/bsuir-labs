<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.buy" var="buy" />
<fmt:message key="local.notallowed" var="notallowed" />
<fmt:message key="local.choose" var="choose" />
<fmt:message key="local.nodestoffer" var="nodestoffer" />

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.buy" />
</jsp:include>

<body>
	<jsp:include page="elements/logo.jsp" />
	
	<h1>${fn:toUpperCase(buy)}</h1>
	<hr/>
	<c:choose>
		<c:when test="${empty user}">
			<h2>${notallowed}</h2>
		</c:when>
		
		<c:when test="${empty dests}">
			<h2>${nodestoffer}</h2>
		</c:when>
		
		<c:otherwise>
			<form method="POST" action="buy">
				<p>${fn:toUpperCase(sessionScope.user.login)}, ${choose}</p>
				<fieldset>
					<jsp:include page="elements/destinations.jsp" />
					<input type="submit" value="${buy}" name="Buy" />
				</fieldset>
			</form>
		</c:otherwise>
	</c:choose>
	<jsp:include page="elements/footer.jsp" />
</body>
</html>