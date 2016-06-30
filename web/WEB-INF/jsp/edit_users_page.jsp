<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.label.login" var="login_label"/>
    <fmt:message bundle="${loc}" key="local.label.password" var="password_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.first" var="name_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.last" var="surname_label"/>
    <fmt:message bundle="${loc}" key="local.registration.tel" var="tel"/>
    <fmt:message bundle="${loc}" key="local.registration.address" var="address"/>
    <fmt:message bundle="${loc}" key="local.registration.email" var="email_label"/>
    <fmt:message bundle="${loc}" key="local.nav.admin" var="admin"/>
    <fmt:message bundle="${loc}" key="local.user.banned" var="banned"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.users" var="edit_users"/>
    <c:set scope="session" var="url" value="/admin/edit_users"/>
    <title>${edit_users}</title>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<jsp:include page="${pageContext.request.contextPath}/controller">
    <jsp:param name="command" value="get-users"/>
</jsp:include>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-danger">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong><fmt:message bundle="${loc}" key="${requestScope.message}"/></strong>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.users}">
        <table>
            <thead>
            <tr>
                <td>ID</td>
                <th>${login_label}</th>
                <th>${name_label}</th>
                <th>${email_label}</th>
                <th>${address}</th>
                <th>${tel}</th>
                <th>${admin}</th>
                <th>${banned}</th>
                <th>${edit}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${requestScope.users}">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="edit-user">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="password" value="${user.password}">
                    <input type="hidden" name="firstName" value="${user.firstName}"/>
                    <input type="hidden" name="lastName" value="${user.lastName}"/>
                    <input type="hidden" name="login" value="${user.login}"/>
                    <input type="hidden" name="email" value="${user.email}"/>
                    <input type="hidden" name="address" value="${user.address}"/>
                    <input type="hidden" name="tel" value="${user.tel}"/>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.firstName}</td>
                        <td>${user.email}</td>
                        <td>${user.address}</td>
                        <td>${user.tel}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.role eq 'ADMIN'}">
                                    <input type="checkbox" name="role" value="ADMIN" checked/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="role" value="ADMIN"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.banned}">
                                    <input type="checkbox" name="banned" value="true" checked/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="banned" value="true"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><input type="submit" value="${edit}"/></td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>