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
    <fmt:message bundle="${loc}" key="local.product.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.product.shortdesc" var="short_desc"/>
    <fmt:message bundle="${loc}" key="local.product.image" var="image_path"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.admin.edit.goods" var="edit_goods"/>
    <jsp:include page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="get-products"/>
    </jsp:include>
    <c:set scope="session" var="url" value="/admin/edit_products"/>
    <title>${edit_goods}</title>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.products}">
        <table style="width: 200px">
            <thead>
            <tr>
                <th></th>
                <th>${image_path}</th>
                <th>ID</th>
                <th>${name}</th>
                <th>${categoty_name}</th>
                <th>${price}</th>
                <th>
                    ${discount}
                <th>
                <th>${short_desc}</th>
                <th>${description}</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <form role="form" action="${pageContext.request.contextPath}/controller" method="post"
                  enctype="multipart/form-data">
                <input type="hidden" name="command" value="add-product">
                <tr>
                    <td colspan="3"><input type="file" name="file" accept="image/jpeg"></td>
                    <td><input type="text" name="name" value=""/></td>
                    <td>
                        <select name="catName" id="sel1">
                            <c:forEach var="category" items="${requestScope.categories}">
                                <option>${category.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="text" name="price" value=""/></td>
                    <td><input type="text" name="discountPrice" value=""/></td>
                    <td><input type="text" name="shortDescription" value=""/></td>
                    <td><input type="text" name="description" value=""/></td>
                    <td><input type="submit" value="${add}"/></td>
                </tr>
            </form>
            <c:forEach var="product" items="${requestScope.products}">
                <tr>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="edit-product">
                        <input type="hidden" name="id" value="${product.id}">
                        <td><img src="${pageContext.request.contextPath}${product.imgPath}" height="50px"
                                 alt="Image"/></td>
                        <td><input type="text" name="imgPath" value="${product.imgPath}"/></td>
                        <td>${product.id}</td>
                        <td><input type="text" name="name" value="${product.name}"/></td>
                        <td>
                            <select name="catName" id="sel2">
                                <option>${product.catName}</option>
                                <c:forEach var="category" items="${requestScope.categories}">
                                    <option>${category.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input type="text" name="price" value="${product.price}"/></td>
                        <td><input type="text" name="discountPrice" value="${product.discountPrice}"/></td>
                        <td><input type="text" name="shortDescription" value="${product.shortDescription}"/></td>
                        <td><input type="text" name="description" value="${product.description}"/></td>
                        <td><input type="submit" value="${edit}"/></td>
                    </form>
                    <td>
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="remove-product">
                            <input type="hidden" name="id" value="${product.id}"/>
                            <button type="submit" class="btn btn-xs btn-danger">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>