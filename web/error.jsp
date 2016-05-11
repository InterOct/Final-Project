<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.errorpage.title" var="title"/>
    <fmt:message bundle="${loc}" key="local.errorpage.text" var="text"/>
    <title><c:out value="${title}"/></title>
    <c:set scope="session" var="url" value="error.jsp"/>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<c:out value="${text}"/>
</body>
</html>