$.fn.pageMe = function (opts, dataURL, productId) {
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
    pager.data("numPages", 0);
    pager.data("curr", 0);
    var firstTime = Boolean(true);
    goTo(0);

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
        var goToPage = parseInt(pager.data("curr")) + 1;
        goTo(goToPage);
    }

    var numberOfPage;

    function goTo(page) {
        var startAt = page * perPage,
            endOn = startAt + perPage;

        var beforeUpdate = parseInt(pager.data("numPages"));
        var numItems = 0;
        var list;
        var listLen;
        jQuery.ajax({
            url: dataURL,
            type: "GET",
            dataType: "json",
            data: {start: startAt, end: endOn, productId: productId},
            async: false,
            success: function (response) {
                numItems = response.total;
                list = response.data;
                listLen = list.length;
                numberOfPage = page;
            },
            error: function (response) {
                numItems = -1;
                alert("Can't get data from the server")
            }
        });
        if (numItems <= 0) {
            return;
        }
        $('#tbl-operations').removeClass("hide");

        var numPages = Math.ceil(numItems / perPage);

        console.log("count of elem " + numItems);
        console.log("start " + ((beforeUpdate * perPage) - perPage));
        console.log("end " + beforeUpdate * perPage);
        if ((numItems > beforeUpdate * perPage || numItems < (beforeUpdate * perPage) - perPage) && !firstTime) {
            location.reload();

        }

        if (firstTime) {
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
            // pager.children().eq(page+1).addClass("active");
            firstTime = Boolean(false);
        }


        var html = "";
        for (var i = 0; i < listLen; i++) {
            html += "<tr>"
            html+="<td>"+list[i].placeName+"</td>";
            html+="<td>"+list[i].priceProduct+"</td>";
            html += "</tr>";
        }
        $('#myTable').html(html);
        var productName = list[0].productName;
        $('#nameProduct').html('<label style="text-align: center">'+productName+'</label>');

        //children.css('display','none').slice(startAt, endOn).show();//change table

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
        pager.data("numPages", numPages);
        pager.children().removeClass("active");
        pager.children().eq(page + 1).addClass("active");
    }
};

