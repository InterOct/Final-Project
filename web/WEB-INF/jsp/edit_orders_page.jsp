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
    <title>Edit categories</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <c:set scope="session" var="url" value="/admin/edit_orders"/>
    <jsp:include page="${pageContext.request.contextPath}/Controller">
        <jsp:param name="command" value="get-orders"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="col-md-12">

    <div class="row">
        <table class="table table-condensed">
            <tr>
                <td>order_id</td>
                <td>user_id</td>
                <td>date</td>
                <td>status</td>
            </tr>
            <c:forEach var="order" items="${requestScope.orders}">
                <tr>
                    <td><span>${order.id}</span></td>
                    <td><span>${order.userId}</span></td>
                    <td><span>${order.date}</span></td>
                    <td><span>${order.status}</span></td>
                    <td>
                        <table class="table table-condensed">
                            <c:forEach var="productMap" items="${order.products}">
                                <tr>
                                    <td><span>${productMap.key.name}</span></td>
                                    <td><span>${productMap.key.price}</span></td>
                                    <td><span>${productMap.value}</span></td>
                                </tr>
                            </c:forEach>
                        </table>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>