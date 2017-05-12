<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h1 style="text-align: center">Write to support</h1>
        <br>
        <div class="col-md-2 col-sm-2"></div>
        <div class="col-md-7 col-sm-7">
            <div class="row">
                <div class="col-md-5 col-sm-5"><h4>Choose product:</h4></div>
                <div class="col-md-7 col-sm-7">
                    <select id="products" name="products" class="form-control" aria-required="true">
                        <c:forEach var="product" items="${productList}">
                            <option value="${product.id}">${product.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <br>
            <textarea id="description" name="description" cols="65" rows="15" style="text-align: center"></textarea>
            <br>
            <br>
            <div class="row">
                <div class="col-md-6"></div>
                <div class="col-md-2"><button type="submit" class="btn btn-primary" onclick="saveComplaint()">  Send  </button></div>
                <div class="col-md-4"></div>
            </div>
        </div>
        <div class="col-md-2 col-sm-2"></div>
    </div>
    <div class="col-md-2"></div>
</div>
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
