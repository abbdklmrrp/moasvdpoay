<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 9.05.2017
  Time: 15:04
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Products by region"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>

<div class="container">
    <h3 style="text-align: center">Product price by region</h3>

    <div class="table-responsive">
        <table class="table table-bordered" id="tbl-fill-product-pricez">
            <%--<table class="table table-bordered" id="tbl-all-complaints">--%>
            <thead>
            <tr>
                <th>Product name</th>
                <th>Product description</th>
                <th>Region</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody id="myTable">
            <c:forEach var="product" items="${productPriceByRegion}">
                <tr>
                    <td>${product.productName}</td>
                    <td>${product.productDescription}</td>
                    <td>${product.placeName}</td>
                    <td>${product.priceProduct}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-md-12 text-center">
        <ul class="pagination" id="myPager"></ul>
    </div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script>
    $.fn.pageMe = function (opts) {
        var $this = this,
            defaults = {
                perPage: 7,
                showPrevNext: false,
                hidePageNumbers: false
            },
            settings = $.extend(defaults, opts);

        var listElement = $this;
        var perPage = settings.perPage;
        var children = listElement.children();
        var pager = $('.pager');

        if (typeof settings.childSelector != "undefined") {
            children = listElement.find(settings.childSelector);
        }

        if (typeof settings.pagerSelector != "undefined") {
            pager = $(settings.pagerSelector);
        }

        var numItems = children.size();
        var numPages = Math.ceil(numItems / perPage);

        pager.data("curr", 0);

        if (settings.showPrevNext) {
            $('<li><a href="#" class="prev_link">«</a></li>').appendTo(pager);
        }

        var curr = 0;
        while (numPages > curr && (settings.hidePageNumbers == false)) {
            $('<li><a href="#" class="page_link">' + (curr + 1) + '</a></li>').appendTo(pager);
            curr++;
        }

        if (settings.showPrevNext) {
            $('<li><a href="#" class="next_link">»</a></li>').appendTo(pager);
        }

        pager.find('.page_link:first').addClass('active');
        pager.find('.prev_link').hide();
        if (numPages <= 1) {
            pager.find('.next_link').hide();
        }
        pager.children().eq(1).addClass("active");

        children.hide();
        children.slice(0, perPage).show();

        pager.find('li .page_link').click(function () {
            var clickedPage = $(this).html().valueOf() - 1;
            goTo(clickedPage, perPage);
            return false;
        });
        pager.find('li .prev_link').click(function () {
            previous();
            return false;
        });
        pager.find('li .next_link').click(function () {
            next();
            return false;
        });

        function previous() {
            var goToPage = parseInt(pager.data("curr")) - 1;
            goTo(goToPage);
        }

        function next() {
            goToPage = parseInt(pager.data("curr")) + 1;
            goTo(goToPage);
        }

        function goTo(page) {
            var startAt = page * perPage,
                endOn = startAt + perPage;

            children.css('display', 'none').slice(startAt, endOn).show();

            if (page >= 1) {
                pager.find('.prev_link').show();
            }
            else {
                pager.find('.prev_link').hide();
            }

            if (page < (numPages - 1)) {
                pager.find('.next_link').show();
            }
            else {
                pager.find('.next_link').hide();
            }

            pager.data("curr", page);
            pager.children().removeClass("active");
            pager.children().eq(page + 1).addClass("active");

        }
    };

    $(document).ready(function () {

        $('#myTable').pageMe({pagerSelector: '#myPager', showPrevNext: true, hidePageNumbers: false, perPage: 4});

    });
</script>
</body>
</html>