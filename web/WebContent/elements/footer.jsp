<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="base.i18n.locale" />

<fmt:message key="local.copyright" var="copyright" />

<hr/>
<footer>${copyright} &copy; ${initParam['author']}</footer>
