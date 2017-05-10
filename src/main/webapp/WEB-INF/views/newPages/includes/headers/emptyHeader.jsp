<header class="navbar navbar-inverse navbar-fixed-top wet-asphalt" role="banner">
    <div class="container">
        <c: if ${not empty param.text}>
            <a href="${pageContext.request.contextPath}/${param.link}" style="text-align: right">
                <h3>${param.text}</h3>
            </a>
        </c:>
    </div>
</header>
