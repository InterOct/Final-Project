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
    <title>Edit products</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.product.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.product.producer" var="producer"/>
    <fmt:message bundle="${loc}" key="local.product.image" var="image_path"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <c:set scope="session" var="url" value="/admin/edit_products"/>

</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="col-md-12">

    <div class="row">
        <table class="table table-condensed">
            <thead>
            <tr>
                <th>${name}</th>
                <th>${categoty_name}</th>
                <th>${price}</th>
                <th>${producer}</th>
                <th>${image_path}</th>
                <th>${description}</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <form action="${pageContext.request.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="add-product">
                <tr>
                    <td><input type="text" name="name" value=""/></td>
                    <td><input type="text" name="catName" value=""/></td>
                    <td><input type="text" name="price" value=""/></td>
                    <td><input type="text" name="producer" value=""/></td>
                    <td><input type="text" name="imgPath" value=""/></td>
                    <td><input type="text" name="description" value=""/></td>
                    <td><input type="submit" value="${add}"/></td>
                </tr>
            </form>
            </tbody>
        </table>
        <c:if test="${not empty requestScope.products}">
            <table class="table table-condensed">
                <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>${name}</th>
                    <th>${categoty_name}</th>
                    <th>${price}</th>
                    <th>${producer}</th>
                    <th>${image_path}</th>
                    <th>${description}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${requestScope.products}">
                    <tr>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="edit-product">
                            <td><img src="${pageContext.request.contextPath}${product.imgPath}" height="50px"
                                     alt="Image"/></td>
                            <td><input type="text" name="id" value="${product.id}"/></td>
                            <td><input type="text" name="name" value="${product.name}"/></td>
                            <td><input type="text" name="catName" value="${product.catName}"/></td>
                            <td><input type="text" name="price" value="${product.price}"/></td>
                            <td><input type="text" name="producer" value="${product.shortDescription}"/></td>
                            <td><input type="text" name="imgPath" value="${product.imgPath}"/></td>
                            <td><input type="text" name="description" value="${product.description}"/></td>
                            <td><input type="submit" value="${edit}"/></td>
                        </form>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="remove-product">
                            <input type="hidden" name="id" value="${product.id}"/>
                            <td>
                                <button type="submit" class="btn btn-xs btn-danger">
                                    <span class="glyphicon glyphicon-remove"></span>&nbsp;
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