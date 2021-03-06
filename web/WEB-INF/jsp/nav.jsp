<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.nav.home" var="home"/>
<fmt:message bundle="${loc}" key="local.nav.categories" var="categories"/>
<fmt:message bundle="${loc}" key="local.nav.admin" var="admin"/>
<fmt:message bundle="${loc}" key="local.login" var="login"/>
<fmt:message bundle="${loc}" key="local.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.admin.edit.categories" var="edit_categories"/>
<fmt:message bundle="${loc}" key="local.admin.edit.users" var="edit_users"/>
<fmt:message bundle="${loc}" key="local.admin.edit.goods" var="edit_goods"/>
<fmt:message bundle="${loc}" key="local.admin.edit.orders" var="edit_orders"/>
<fmt:message bundle="${loc}" key="local.admin.edit.coupons" var="edit_coupons"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
             var="ru_button"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
             var="en_button"/>
<fmt:message bundle="${loc}" key="local.label.login"
             var="login_label"/>
<fmt:message bundle="${loc}" key="local.label.password"
             var="password_label"/>
<fmt:message bundle="${loc}" key="local.login"
             var="login"/>
<fmt:message bundle="${loc}" key="local.singup.text"
             var="singup"/>
<fmt:message bundle="${loc}" key="local.cart" var="cart"/>
<fmt:message bundle="${loc}" key="local.account" var="account"/>
<jsp:include page="${pageContext.request.contextPath}/controller">
    <jsp:param name="command" value="get-categories"/>
</jsp:include>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">E-SHOP</a>
        </div>
        ${pageContext.request.contextPath}
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/index.jsp">${home}</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">${categories}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="category" items="${requestScope.categories}">
                            <li>
                                <a href="${pageContext.request.contextPath}/index.jsp?cat=${category.name}">${category.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${(not empty sessionScope.user)}">
                    <c:choose>
                        <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${admin}<span
                                        class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/admin/edit_users">${edit_users}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/admin/edit_categories">${edit_categories}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/admin/edit_products">${edit_goods}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/admin/edit_orders">${edit_orders}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/admin/edit_coupons">${edit_coupons}</a>
                                    </li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/cart"><span
                                    class="glyphicon glyphicon-shopping-cart"></span> ${cart}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <li class="dropdown">
                    <c:choose>
                        <c:when test="${empty sessionScope.user.firstName}">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span
                                    class="glyphicon glyphicon-log-in"></span> ${login}</a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <form action="${pageContext.request.contextPath}/controller"
                                                      method="post">
                                                    <input type="hidden" name="command" value="login"/>
                                                    <div class="form-group">
                                                        <label for="login">${login_label}</label>
                                                        <input type="text" name="login" class="form-control" id="login"
                                                               value=""
                                                               placeholder="${login_label}"
                                                               pattern="^[a-zA-Z\d]{3,16}$"
                                                               title="Input correct login.">
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="password">${password_label}</label>
                                                        <input type="password" name="password" class="form-control"
                                                               id="password" value=""
                                                               placeholder="${password_label}"
                                                               title="Input correct password.">
                                                    </div>
                                                    <input type="submit" class="btn btn-primary full" value="${login}">
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/registration.jsp"
                                       class="hyperlink text-center">${singup}</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span
                                    class="glyphicon glyphicon-user"></span> ${sessionScope.user.firstName}</a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="${pageContext.request.contextPath}/user_page">${account}</a>
                                </li>
                                <li>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <form action="${pageContext.request.contextPath}/controller"
                                                      method="post" style="display: block;">
                                                    <input type="hidden" name="command" value="logout"/>
                                                    <input type="submit" class="btn btn-primary full"
                                                           value="${logout}"/><br/>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li>
                    <form action="${pageContext.request.contextPath}/controller" method="post" style="padding-top: 9px">
                        <input type="hidden" name="command" value="change-local"/>
                        <input type="hidden" name="local" value="en"/>
                        <input type="submit" value="${en_button}" class="btn btn-link"/><br/>
                    </form>
                </li>
                <li>
                    <form action="${pageContext.request.contextPath}/controller" method="post"
                          style="padding-top: 9px ">
                        <input type="hidden" name="command" value="change-local"/>
                        <input type="hidden" name="local" value="ru"/>
                        <input type="submit" value="${ru_button}" class="btn btn-link"/><br/>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
