$(document).ready(function () {
    $('#btnShowReport').click(function () {
        if (checkDate()) {
            drawChartAndTable();
        }
    });
    $('#btnDownloadReport').click(function () {
        if (checkDate()) {
            $('#formWithRegionsAndDates').submit();
        }
    });

});

function fillData() {
    var btnShow = document.getElementById('btnShowReport');
    var btnDownload = document.getElementById('btnDownloadReport');
    if ($('#beginDate').val() != '' && $('#endDate').val() != '') {
        btnShow.removeAttribute("disabled");
        btnDownload.removeAttribute("disabled");
    }
    else {
        btnShow.setAttribute("disabled", "disabled");
        btnDownload.setAttribute("disabled", "disabled");
    }
}

function checkDate() {
    var start = new Date($('#beginDate').val());
    var end = new Date($('#endDate').val());
    if ((end - start) < 0) {
        showError("Please, enter correct date period");
        return false;
    }
    else {
        return true;
    }
}

function showError(message) {
    sweetAlert("Oops...", message, "error");
}

google.charts.load('current', {'packages': ['corechart']});

function drawChartAndTable() {
    var list = [];
    var len = 0;
    var isError = Boolean(false);
    jQuery.ajax({
        url: 'data',
        type: "GET",
        dataType: "json",
        data: jQuery("#formWithRegionsAndDates").serialize(),
        async: false,
        success: function (response) {
            list = response;
            len = list.length;
        },
        error: function () {
            showError("Error on the server");
            isError = Boolean(true);
        }
    });
    if (isError) {
        return;
    }
    var rowList = [];
    for (var i = 0; i < len; i++) {
        var row = [];
        row.push(list[i].timePeriod, list[i].ordersCount, list[i].complaintsCount);
        rowList.push(row);
    }

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Day');
    data.addColumn('number', 'Orders');
    data.addColumn('number', 'Complaints');

    data.addRows(rowList);
    var select = document.getElementById('sel1');
    var options = {
        //if graph type change back to line
        // chart: {
        //     title: 'Statistic in ' + select.value,
        //     subtitle: 'Jcompany'
        // },
        legend: {position: 'bottom'},
        axes: {
            x: {
                0: {side: 'top'}
            }
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('line_top_x'));
    chart.draw(data, options);
    var tableTemplate = '<table id=table_id class=display>' +
        '<thead>' +
        '<tr>' +
        '<th>Date</th>' +
        '<th>Orders</th>' +
        '<th>Complaints</th>' +
        '</tr>' +
        '</thead>' +
        '</table>';
    $('#table_div').html(tableTemplate);
    $('#table_id').DataTable({
        searching: false,
        data: list,
        columns: [
            {data: 'timePeriod'},
            {data: 'ordersCount'},
            {data: 'complaintsCount'}
        ]
    })
}