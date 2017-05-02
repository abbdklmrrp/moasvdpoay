function check() {
    var btn = document.getElementById('b1');
    if ($('#beginDate').val() != '' && $('#endDate').val() != '') {
        btn.removeAttribute("disabled");
    }
    else {
        btn.setAttribute("disabled", "disabled");
    }
}
google.charts.load('current', {'packages': ['corechart']});

function drawChart() {
    $('#err').empty();
    var list = [];
    var len;
    var isError = Boolean(false);
    jQuery.ajax({
        url: 'graph/graphData',
        type: "GET",
        dataType: "json",
        data: jQuery("#formWithRegionsAndDates").serialize(),
        async: false,
        success: function (response) {
            list = response;
            len = list.length;
            if (len == 0) {
                $('#err').html("No data for this period");
                isError = Boolean(true);
            }
        },
        error: function (response) {
            $('#err').html("Can't connect to the server");
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
        chart: {
            title: 'Statistic in ' + select.value,
            subtitle: 'NetCrooker'
        },
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