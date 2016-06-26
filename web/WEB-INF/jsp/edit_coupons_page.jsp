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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.coupons" var="edit_coupons"/>
    <c:set scope="session" var="url" value="/admin/edit_coupons"/>
    <jsp:include page="${pageContext.request.contextPath}/Controller">
        <jsp:param name="command" value="get-coupons"/>
    </jsp:include>
    <title>${edit_coupons}</title>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="col-md-12">
    <div class="row">
        <table class="table table-condensed">
            <tr>
                <th>id user</th>
                <th>${discount}</th>
                <th></th>
            </tr>
            <form action="${pageContext.request.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="add-coupon">
                <tr>
                    <td><input type="text" name="userId" value=""/></td>
                    <td><input type="text" name="discount" value=""/></td>
                    <td><input type="submit" value="${add}"/></td>
                </tr>
            </form>
        </table>
        <c:if test="${not empty requestScope.coupons}">
            <table class="table table-condensed">
                <thead>
                <tr>
                    <th>id</th>
                    <th>id user</th>
                    <th>${discount}</th>
                    <th>${edit}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="coupon" items="${requestScope.coupons}">
                    <tr>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="edit-coupon">
                            <input type="hidden" name="id" value="${id}"/>
                            <td>${coupon.id}</td>
                            <td><input type="text" name="userId" value="${coupon.userId}"/></td>
                            <td><input type="text" name="discount" value="${coupon.discount}"/></td>
                            <td><input type="submit" value="${edit}"/></td>
                        </form>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="remove-coupon">
                            <input type="hidden" name="id" value="${coupon.id}">
                            <td>
                                <button type="submit" class="btn btn-xs btn-danger">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>