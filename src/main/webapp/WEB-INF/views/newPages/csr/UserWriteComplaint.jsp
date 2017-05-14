<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 09.05.2017
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value=""/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="pageName" value="UserWriteComplaint"/>
</jsp:include>
<jsp:include page="../includes/writeToSupport.jsp"/>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script>
    function saveComplaint() {
        var productId=$("#products").val();
        var description=$("#description").val();
        $.ajax({
            url: 'saveComplaint',
            data: {productId:productId,description:description },
            type: "POST",
            dataType: 'text',
            success: function (resultMsg) {
                if (resultMsg === '"success"') {
                    swal({
                        title: "The complaint was successfully sent.",
                        type: "success"
                    });
                    $('#description').val("");
                }
                else {
                    swal("Sorry, an error occurred!", "Please, try again", "error");
                }
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");
            }
        })
    }
</script>


