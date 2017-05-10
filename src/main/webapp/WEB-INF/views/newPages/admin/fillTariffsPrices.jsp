<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 9.05.2017
  Time: 9:28
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="All complaints"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp"/>
<form method="post" modelAttribute="priceByRegionDto"
      action="${pageContext.request.contextPath}/admin/fillTariffsPrices">

    <div class="container">
        <h3 style="text-align: center">Fill in products with prices</h3>

        <div class="row">
            <div class="form-group row" id='productId'>
                <label class="col-sm-4 control-label">Select category</label>
                <div class="col-sm-8">
                    <select name="productId" class="form-control" required>
                        <c:forEach var="product" items="${products}">
                            <option value="${product.id}">${product.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="table-responsive">
                <%--<table class="table table-bordered" id="tbl-fill-product-pricez">--%>
                <table class="table table-bordered" id="tbl-all-complaints">
                    <thead>
                    <tr>
                        <th>Place name</th>
                        <th>Select region</th>
                        <th>Enter price</th>
                    </tr>
                    </thead>
                    <tbody id="myTable">
                    <c:forEach var="place" items="${placesForFillInTariff}">
                        <tr>
                            <td>${place.name}</td>
                            <td><input type="checkbox" name="placeId" value="${place.id}">check</td>
                            <td><input type="text" name="priceByRegion"></td>
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
    <div class="row">
        <div class="col-sm-4 col-xs-4"></div>
        <button type="submit" class="btn btn-primary col-sm-4 col-xs-4">Save</button>
        <div class="col-sm-4 col-xs-4"></div>
    </div>
</form>
</body>
</html>