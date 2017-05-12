<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Write to support"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/residentialHeader.jsp"/>
    <jsp:include page="../includes/writeToSupport.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script>
    function saveComplaint() {
        var productId=$("#products").val();
        var description=$("#description").val();
        $.ajax({
            url: 'writeComplaint',
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
