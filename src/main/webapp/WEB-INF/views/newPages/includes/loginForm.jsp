<%--
  Created by Anna Rysakova
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <form method="POST" action="${pageContext.request.contextPath}/doLogin">
                <div class="login-form">
                    <h1 style="text-align: center">Login</h1>
                    <br>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="Username " id="UserName" name="username">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group log-status">
                        <input type="password" class="form-control" placeholder="Password" id="Password"
                               name="password">
                        <i class="fa fa-lock"></i>
                    </div>
                    <c:if test="${not empty error}">
                        <h4 style="text-align: center; color: #ce001b;">${error}</h4>
                    </c:if>
                    <br>
                    <div style="text-align: center">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="col-md-4"></div>
</section>