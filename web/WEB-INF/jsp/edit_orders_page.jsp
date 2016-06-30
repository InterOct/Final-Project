<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tld/custom.tld" %>
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
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.orders" var="edit_orders"/>
    <fmt:message bundle="${loc}" key="local.order.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.order.status" var="status"/>
    <c:set scope="session" var="url" value="/admin/edit_orders"/>
    <title>${edit_orders}</title>
    <jsp:include page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="get-orders"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-danger">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong><fmt:message bundle="${loc}" key="${requestScope.message}"/></strong>
        </div>
    </c:if>
    <table>
        <tr>
            <th>order_id</th>
            <th>user_id</th>
            <th>${date}</th>
            <th>${status}</th>
            <th>${edit}</th>
        </tr>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="edit-order">
                    <input type="hidden" name="id" value="${order.id}">
                    <td>${order.id}</td>
                    <td>${order.userId}</td>
                    <td>${order.date}</td>
                    <td>
                        <select name="status" id="sel1">
                            <option><cm:status value="${order.status}"/></option>
                            <option><cm:status value="CONSIDERATION"/></option>
                            <option><cm:status value="PROCESSING"/></option>
                            <option><cm:status value="COMPLETED"/></option>
                            <option><cm:status value="CANCELLED"/></option>
                        </select>
                    </td>
                    <td><input type="submit" value="${edit}"></td>
                        <%--<td>--%>
                        <%--<table>--%>
                        <%--<c:forEach var="productMap" items="${order.products}">--%>
                        <%--<tr>--%>
                        <%--<td><span>${productMap.key.name}</span></td>--%>
                        <%--<td><span>${productMap.key.price}</span></td>--%>
                        <%--<td><span>${productMap.value}</span></td>--%>
                        <%--</tr>--%>
                        <%--</c:forEach>--%>
                        <%--</table>--%>
                        <%--</td>--%>
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>