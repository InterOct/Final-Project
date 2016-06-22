<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>E-SHOP</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.welcome.message" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.details" var="details"/>
    <c:set scope="page" var="currentPage" value="${requestScope.currentPage}"/>
    <c:set scope="page" var="curCategory" value="${param.cat}"/>
    <jsp:include page="${pageContext.request.contextPath}/Controller">
        <jsp:param name="command" value="get-products"/>
    </jsp:include>
    <c:set scope="session" var="url" value="index.jsp?cat=${curCategory}&page=${currentPage}"/>
    <style type="text/css">
        form {
            display: inline-block;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="col-md-12">
            <span>${requestScope.message}</span>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-12">
            <h1 class="text-center strong text-info">${welcome}</h1>
        </div>
    </div>
    <br>
    <div class="row content">
        <div class="col-sm-2 sidenav hidden-xs">
            <ul class="list-group">
                <li class="list-group-item-heading h2 text-center">${categories}</li>
                <c:forEach var="category" items="${requestScope.categories}">
                    <li class="list-group-item"><a
                            href="${pageContext.request.contextPath}/index.jsp?cat=${category.name}">${category.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-10">
            <div class="row window" style="padding: 0">
                <div class="col-sm-offset-1 col-sm-5">
                    <span class="h2 text-info">${curCategory}</span>
                </div>
                <div class="col-sm-6">
                    <ul class="pagination pull-right">
                        <c:choose>
                            <c:when test="${currentPage le 1}">
                                <li class="disabled"><a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/index.jsp?page=${currentPage - 1}&cat=${curCategory}"
                                       aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="active"><a href="#">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/index.jsp?page=${i}&cat=${curCategory}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${currentPage ge requestScope.numOfPages}">
                                <li class="disabled"><a href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/index.jsp?page=${currentPage + 1}&cat=${curCategory}"
                                       aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
            <c:set var="counter" value="${0}" scope="page"/>
            <c:forEach var="product" items="${requestScope.products}">
                <c:if test="${(counter%4)==0}"><div class="row"></c:if>
                <div class="col-sm-3">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${product.name}</div>
                        <div class="panel-body"><img src="${product.imgPath}"
                                                     class="img-responsive"
                                                     style="margin: auto; max-height: 300px;"
                                                     alt="Image"></div>
                        <div class="panel-footer">
                            <div style="overflow: auto; text-align: center;">
                                <h3>${price}:${product.price}$</h3>
                                <form action="${pageContext.request.contextPath}/Controller" method="post">
                                    <input type="hidden" name="command" value="add-to-cart">
                                    <input type="hidden" name="id" value="${product.id}"/>
                                    <input type="hidden" name="name" value="${product.name}"/>
                                    <input type="hidden" name="catName" value="${product.catName}"/>
                                    <input type="hidden" name="price" value="${product.price}"/>
                                    <input type="hidden" name="producer" value="${product.shortDescription}"/>
                                    <input type="hidden" name="imgPath" value="${product.imgPath}"/>
                                    <input type="hidden" name="description" value="${product.description}"/>
                                    <input type="submit" class="btn btn-primary full" value="${cart}"
                                           style="min-width: 100px;">
                                </form>
                                <form action="${pageContext.request.contextPath}/Controller" method="get">
                                    <input type="hidden" name="command" value="view-product">
                                    <input type="hidden" name="id" value="${product.id}"/>
                                    <input type="submit" class="btn btn-primary full" value="${details}"
                                           style="min-width: 100px;">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <c:set var="counter" value="${counter+1}" scope="page"/>
                <c:if test="${(counter%4)==0}"></div><br></c:if>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>