<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.destination" var="destination" />

<label for="dest">${destination}</label>
<select id="dest" name="dest">
	<c:forEach items="${dests}" var="dest">
		<option value="${dest.name}">
			<c:out value="${dest.name}" />
		</option>
	</c:forEach>
</select>
