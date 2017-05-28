/**
 * @author Revniuk Aleksandr
 */


/**
 * This function disable buttons if date is not valid, else enable.
 */
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
/**
 * This function check if date period is correct.
 *
 * @returns {boolean} <code>true</code> if date was valid, <code>false</code> if period of time was wrong.
 */
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
/**
 * This function show modal window with error.
 *
 * @param message message of error
 */
function showError(message) {
    sweetAlert("Oops...", message, "error");
}

google.charts.load('current', {'packages': ['corechart']});
/**
 * This function draw chart and table with received data.
 *
 * @param dataUrl url of data
 * @param name name of object
 */
function drawChartAndTable(dataUrl,name) {
    var list = [];
    var len = 0;
    var isError = Boolean(false);
    jQuery.ajax({
        url: dataUrl,
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
        row.push(list[i].timePeriod, list[i].amount);
        rowList.push(row);
    }

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Day');
    data.addColumn('number', name);

    data.addRows(rowList);
    var select = document.getElementById('sel1');
    var options = {
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
        '<th>'+name+'</th>' +
        '</tr>' +
        '</thead>' +
        '</table>';
    $('#table_div').html(tableTemplate);
    $('#table_id').DataTable({
        searching: false,
        data: list,
        columns: [
            {data: 'timePeriod'},
            {data: 'amount'}
        ]
    })
}