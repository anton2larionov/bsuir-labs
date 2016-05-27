<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<a href="<c:url value="home" />">
	<img src="<c:url value="/resources/images/logo.gif" />" alt="logo" class="logo">
</a>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.button.en" var="button_en" />
<fmt:message key="local.button.ru" var="button_ru" />

<div id="outer">
	<div id="inner">
		<form action="locale" method="POST" class="loc">
			<input type="hidden" name="local" value="ru" /> <input type="submit"
				value="${button_ru}" class="loc" />
		</form>
		<form action="locale" method="POST" class="loc">
			<input type="hidden" name="local" value="en" /> <input type="submit"
				value="${button_en}" class="loc" />
		</form>
	</div>
</div>