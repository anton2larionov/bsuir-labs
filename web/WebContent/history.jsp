<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.notallowed" var="notallowed" />
<fmt:message key="local.nodestshow" var="nodestshow" />
<fmt:message key="local.history" var="history" />
<fmt:message key="local.buy" var="buy" />
<fmt:message key="local.city" var="city" />
<fmt:message key="local.date" var="date" />

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.history" />
</jsp:include>

<body>
	<jsp:include page="elements/logo.jsp" />
	
	<h1>${fn:toUpperCase(history)}</h1>
	<hr/>
	<c:choose>
		<c:when test="${empty user}">
			<h2>${notallowed}</h2>
		</c:when>
		<c:otherwise>
		
		<sql:query dataSource="jdbc/airportdb" var="result">
			SELECT login, city, date FROM history WHERE login = ? ORDER BY date DESC LIMIT 10
			<sql:param value="${user.login}" />
		</sql:query>
		<c:choose>
			<c:when test="${result.rowCount < 1}">
				<h2>${nodestshow}</h2>
				<a href="<c:url value="buy" />">${buy}</a>
			</c:when>
			<c:otherwise>
					<table>
						<tr>
							<th>${city}</th>
							<th>${date}</th>
						</tr>
						<c:forEach var="row" items="${result.rows}">
							<tr>
								<td><c:out value="${row.city}" /></td>
								<td><c:out value="${row.date}" /></td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
		</c:choose>

		</c:otherwise>
	</c:choose>
	<jsp:include page="elements/footer.jsp" />
</body>
</html>