<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 27.04.2017
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write complaint</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <%-- <script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>--%>
    <spring:url value="/resources/js/jquery-1.12.1.min.js"
                var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
</head>
<body>
<%--<form class="container" style="min-height: 500px">--%>
<form id="search-form">
    <div class="left-login">
        <h1>Search Form</h1>
        <br>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Phone</label>
            <div class="col-sm-10">
                <input type=text class="form-control" id="phone" name="phone">
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label" readonly="readonly">Email</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email">
            </div>
        </div>
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label" readonly="readonly">Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="fullName" name="fullName">
            </div>
        </div>
        <button type="submit" class="log-btn" id="bth-search" onclick="searchViaAjax()">Search</button>
    </div>
</form>
<form method="post" action="${contextPath}/csr/writeComplaint">
    <div class="right-login">
        <div class="form-group ">
            Product
            <select id="products" name="products" class="form-control" aria-required="true">

            </select>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label" readonly="readonly">Client Id</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="id" name="id">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label" readonly="readonly">Description</label>
                <div class="col-sm-10">
                    <textarea name="description" cols="38" rows="10"></textarea>
                </div>
            </div>
            <button type="submit" class="log-btn">Save</button>
        </div>
    </div>
</form>


<script>
    jQuery(document).ready(function ($) {
        $("#search-form").submit(function (event) {

            // Disble the search button
            enableSearchButton(false);

            // Prevent the form from submitting via the browser.
            event.preventDefault();

            searchViaAjax();

        });
    });

    function searchViaAjax() {

        var search = $("#phone").val();
//        search["phone"] = $("#phone").val();
//        search["email"] = $("#email").val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "${contextPath}/csr/getSearchUser",
            data: JSON.stringify(search),
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                display(data);
            },
            error: function (e) {
                console.log("ERROR: ", e);
                display(e);
            },
            done: function (e) {
                console.log("DONE");
                enableSearchButton(true);
            }
        });

    }

    function enableSearchButton(flag) {
        $("#btn-search").prop("disabled", flag);
    }

    function displayProducts(data) {
        $('#products').empty();
        for (var i in data) {
            $('#products').append('<option value=' + data[i].id + '>' + data[i].name + '</option>');
        }
        $('#products').val(data[0]);
    }

    function display(data) {
        /*var json = "<h4>Ajax Response</h4><pre>"
         + JSON.stringify(data, null, 4) + "</pre>";
         var text=data.address+" "+data.phone;*/
        $('#email').val(data.email);
        $('#id').val(data.id);
        var name = data.name + " " + data.surname;
        $('#fullName').val(name)
        var product = data;
        var search = $("#id").val();
        ;
//        search["phone"] = $("#phone").val();
//        search["email"] = $("#email").val();
//        search["id"] = $("#id").val();
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "${contextPath}/csr/getSearchProduct",
            data: JSON.stringify(search),
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                displayProducts(data);
            }
        });
    }
</script>

</body>
</html>
