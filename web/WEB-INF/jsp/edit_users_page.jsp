<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style>
        input {
            border: none;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
    <fmt:message bundle="${loc}" key="local.user.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.users" var="edit_users"/>
    <c:set scope="session" var="url" value="/admin/edit_users"/>
    <title>${edit_users}</title>

</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <c:if test="${not empty requestScope.users}">
                <table class="table table-condensed table-hover">
                    <thead>
                    <tr>
                        <th>${login_label}</th>
                        <th>${name_label}</th>
                        <th>${surname_label}</th>
                        <th>${email_label}</th>
                        <th>${address}</th>
                        <th>${tel}</th>
                        <th>${admin}</th>
                        <th>${banned}</th>
                        <th>${discount}</th>
                        <th>${edit}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${requestScope.users}">
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="edit-user">
                            <tr>
                                <td><input type="text" name="login" value="${user.login}"/></td>
                                <td><input type="text" name="firstName" value="${user.firstName}"/></td>
                                <td><input type="text" name="lastName" value="${user.lastName}"/></td>
                                <td><input type="text" name="email" value="${user.email}"/></td>
                                <td><input type="text" name="address" value="${user.address}"/></td>
                                <td><input type="text" name="tel" value="${user.tel}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.role eq 'ADMIN'}">
                                            <input type="checkbox" name="role" value="ADMIN" checked/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="role"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.banned}">
                                            <input type="checkbox" name="banned" value="true" checked/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="banned"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><input type="text" name="discount" value="${user.discount}"/></td>
                                <td><input type="submit" value="${edit}"/></td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>