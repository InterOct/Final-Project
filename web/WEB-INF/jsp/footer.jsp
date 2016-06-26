<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.copyright" var="copyright"/>
<footer class="container-fluid text-center">
    <p>${copyright}</p>
</footer>
