<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.sorry" var="sorry" />
<fmt:message key="local.problems" var="problems" />

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.error" />
</jsp:include>
</head>
<body>
	<jsp:include page="elements/logo.jsp" />
	
	<h1>${sorry}. ${problems} :(</h1>

	<jsp:include page="elements/footer.jsp" />
</body>
</html>