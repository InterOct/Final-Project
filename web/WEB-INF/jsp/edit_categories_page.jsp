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
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.categories" var="edit_categories"/>
    <c:set scope="session" var="url" value="/admin/edit_categories"/>
    <title>${edit_categories}</title>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="col-md-12">

    <div class="row">
        <table class="table table-condensed">
            <thead>
            <tr>
                <th>${categoty_name}</th>
                <th>${description}</th>
                <th>${add}</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <form action="${pageContext.request.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="add-category">
                <tr>
                    <td><input type="text" name="name" value=""/></td>
                    <td><input type="text" name="description" value=""/></td>
                    <td><input type="submit" value="${add}"/></td>
                <tr></tr>
            </form>
            </tbody>
        </table>
        <c:if test="${not empty requestScope.categories}">
            <table class="table table-condensed">
                <thead>
                <tr>
                    <th>${categoty_name}</th>
                    <th>${description}</th>
                    <th>${edit}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="category" items="${requestScope.categories}">
                    <tr>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="edit-category">
                            <td><input type="text" name="name" value="${category.name}"/></td>
                            <td><input type="text" name="description" value="${category.description}"/></td>
                            <td><input type="submit" value="${edit}"/></td>
                        </form>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="remove-category">
                            <input type="hidden" name="name" value="${category.name}"/>
                            <input type="hidden" name="description" value="${category.description}"/>
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