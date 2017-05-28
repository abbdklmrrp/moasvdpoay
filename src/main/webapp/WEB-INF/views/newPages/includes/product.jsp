<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-xs-1"></div>
        <div class="col-xs-10">
            <h1 style="text-align: center">Product</h1>
            <br>
            <div align="center">
                <h1>${product.name}</h1>
                <div class="row">
                    <label>Name:</label> ${product.name}
                </div>
                <div class="row">
                    <label>Description:</label> ${product.description}
                </div>
                <div class="row">
                    <label>Price:</label> ${product.basePrice} $
                </div>
                <div class="row">
                    <label>Processing Strategy:</label> ${product.processingStrategy.name}
                </div>
                <button onclick="goBack()" class="btn btn-info"> <span
                        class="glyphicon glyphicon-menu-left"></span> Back
                </button>
            </div>
        </div>
        <div class="col-xs-1"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script>
    function goBack() {
        window.history.back();
    }
</script>

