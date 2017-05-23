<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Order"/>
    </jsp:include>
    <style type="text/css">

        #back {
            background-color: rgba(0, 0, 0, 0.4);
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            display: none;
            z-index: 1000;
        }

        #area {
            background-color: white;
            font-family: 'Open Sans', sans-serif;
            width: 478px;
            padding: 17px;
            border-radius: 5px;
            text-align: center;
            position: fixed;
            left: 50%;
            top: 50%;
            margin-left: -256px;
            margin-top: -200px;
            overflow: hidden;
            display: none;
            z-index: 2000;
        }

        .close {
            margin-left: 364px;
            margin-top: 4px;
            cursor: pointer;
        }

    </style>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="pageName" value="Orders"/>
</jsp:include>
<div class="container" style="margin-bottom: 30px; width:60%; max-width: 400px;">
    <div class="login-form">
        <h1 style="text-align: center">Order info</h1>
        <br>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Product name</label>
            <div class="col-sm-10">
                <input readonly style="border-bottom-width: 0;" type="text" class="form-control" name="productName"
                       id="productName" value="${order.productName}"><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Product type</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="productType" id="productType"
                       value=${order.productType}><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Customer type</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="customerType" id="customerType"
                       value=${order.customerType}><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Region</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="place" id="place" value="${order.place}"><br>
            </div>
        </div>
        <h1 style="text-align: center">Customer info</h1>
        <br>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="userName" id="userName"
                       value="${order.userName}"><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Surname</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="userSurname" id="userSurname"
                       value="${order.userSurname}"><br>
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Phone</label><br>
            <div class="col-sm-10">
                <input readonly type="text" class="form-control" name="phone" id="phone" value="${order.phone}"><br>
            </div>
        </div>
        <div class="row" id="actvBtn${order.orderId}">
            <div class="col-sm-1"></div>
            <div class="col-sm-4">
                <button class="btn btn-success" onclick="activateOrder(${order.orderId})">Activate</button>
            </div>
            <div class="col-sm-2"></div>
            <div class="col-sm-4">
                <button class="btn btn-primary" onclick="show('block')">Write email</button>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </div>
</div>
<div id="back" style='display: none;'></div>
<div id="area" style='display: none;'>
    <img class="close" onclick="show('none')" src="http://sergey-oganesyan.ru/wp-content/uploads/2014/01/close.png">
    <h1 style="text-align: center">Write to client</h1>
    <br>
    <textarea id="description" name="description" cols="60" rows="14" val>
        Dear ${order.userName} ${order.userSurname}
        Information about ${order.productName}
    </textarea>
    <br>
    <div align="center">
        <button type="submit" class="btn btn-success" onclick="sendEmail(${order.orderId})"> Send</button>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
<script src="${pageContext.request.contextPath}/resources/js/activateOrderByCsr.js"></script>
<script>
    function show(state){
        document.getElementById('area').style.display = state;
        document.getElementById('back').style.display = state;
    }

    function sendEmail(orderId){
        show('none');
        var text = $("#description").val();
        $.ajax({
            url: 'sendEmail',
            data: {orderId: orderId, text: text},
            type: "POST",
            dataType: 'text',
            success: function (resultMsg) {
                if (resultMsg === '"success"') {
                    swal({
                        title: "The email was successfully sent.",
                        type: "success"
                    });
                    $('#description').val("");
                }
            },
            error: function () {
                swal("Sorry, an error on server has occurred", "please, try again.", "error");
                console.log("error");
            }
        })
    }
</script>

