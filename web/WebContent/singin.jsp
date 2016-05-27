<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="elements/header.jsp">
	<jsp:param name="title" value="local.singin" />
</jsp:include>
<body>
	<jsp:include page="elements/logo.jsp" />

	<jsp:include page="elements/form.jsp">
		<jsp:param name="action" value="singin" />
	</jsp:include>

	<jsp:include page="elements/footer.jsp" />
</body>
</html>