<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.input" var="input" />
<fmt:message key="local.login" var="login" />
<fmt:message key="local.password" var="password" />
<fmt:message key="local.loginerr" var="loginerr" />
<fmt:message key="local.passworderr" var="passworderr" />
<fmt:message key="local.enter" var="enter" />
<fmt:message key="local.${param.action}" var="action" />

<h1>${fn:toUpperCase(action)}</h1>

<hr/>
<form method="POST" action="${param.action}">
    <p>${input}</p>
    <fieldset>
	<label for="login">${login}</label>
	<input type="text" name="login" id="login" required pattern="\w{3,10}" title="${loginerr}">
	<label for="password">${password}</label>
	<input type="password" name="password" id="password" required pattern="\w{3,10}" title="${passworderr}">
	<input type="submit" value="${enter}">
	</fieldset>
</form>

<c:if test="${not empty requestScope.error}">
	${requestScope.error.message}
</c:if>