function getStatisticList() {
    var rowList = [];
    var list;
    var len;
    jQuery.ajax({
        url: 'graph/graphData',
        type: "GET",
        dataType: "json",
        data: jQuery("#formWithRegionsAndDates").serialize(),
        async: false,
        success: function (response) {
            list = response;
            len = list.length;
        },
        error: function () {
            return null;
        }
    });

    for (var i = 0; i < len; i++) {
        var row = [];
        row.push(new Date(list[i].year, list[i].month, list[i].day, 0, 0, 0, 0), list[i].countOfOrders, list[i].countOfComplaints);
        rowList.push(row);
    }
    return rowList;
}

function getCountOfPages() {
    var count;
    jQuery.ajax({
        url: 'graph/countOfPages',
        type: "GET",
        dataType: "json",
        async: false,
        success: function (response) {
            count = response;
        },
        error: function () {
            count = 1;
        }
    });
    return count;
}

function getDataByPage(page) {
    var rowList = [];
    var list;
    var len;
    jQuery.ajax({
        url: 'graph/graphPartOfData',
        type: "GET",
        dataType: "json",
        data: {pageNumber: page},
        async: false,
        success: function (response) {
            list = response;
            len = list.length;
        },
        error: function () {
            return null;
        }
    });

    for (var i = 0; i < len; i++) {
        var row = [];
        row.push(new Date(list[i].year, list[i].month, list[i].day, 0, 0, 0, 0), list[i].countOfOrders, list[i].countOfComplaints);
        rowList.push(row);
    }
    return rowList;
}