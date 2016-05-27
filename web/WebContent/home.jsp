<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.welcome" var="welcome" />
<fmt:message key="local.guest" var="guest" />
<fmt:message key="local.singin" var="singin" />
<fmt:message key="local.register" var="register" />
<fmt:message key="local.manage" var="manage" />
<fmt:message key="local.buy" var="buy" />
<fmt:message key="local.history" var="history" />
<fmt:message key="local.logout" var="logout" />

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.home" />
</jsp:include>

<body>
	<jsp:include page="elements/logo.jsp" />

	<c:choose>
		<c:when test="${empty sessionScope.user}">
			<h1>${welcome}, ${guest}</h1>
			<hr/>
			<a href="<c:url value="singin" />">${singin}</a>
        |
        	<a href="<c:url value="register" />">${register}</a>
		</c:when>
		<c:otherwise>
			<h1>${welcome}, ${fn:toUpperCase(user.login)}</h1>
			<hr/>
			<c:choose>
    			<c:when test="${sessionScope.role.admin}">
        			<a href="<c:url value="manage" />">${manage}</a>
        		|
    			</c:when>    
    			<c:otherwise>
					<a href="<c:url value="buy" />">${buy}</a>
        		|
        			<a href="<c:url value="history" />">${history}</a>
        		|
    			</c:otherwise>
			</c:choose>
			
			<a href="<c:url value="logout" />">${logout}</a>
		</c:otherwise>
	</c:choose>

	<jsp:include page="elements/footer.jsp" />
</body>
</html>