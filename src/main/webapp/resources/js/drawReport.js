google.charts.load('current', {'packages': ['corechart']});

function drawChart() {

    var list = [];
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
            if (len==0) {
                $('#err').html("No data for this period");
                return;
            }
        },
        error: function () {
            $('#err').html("Can't connect to the server");
            return;
        }
    });

    var rowList = [];
    for (var i = 0; i < len; i++) {
        var row = [];
        row.push(list[i].timePeriod, list[i].complaintsCount, list[i].ordersCount);
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
        legend: { position: 'bottom' },
        axes: {
            x: {
                0: {side: 'top'}
            }
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('line_top_x'));
    chart.draw(data, options);
    var tableTemplate = '<table id=table_id class=display>'+
        '<thead>'+
        '<tr>'+
        '<th>Date</th>'+
        '<th>Orders</th>'+
        '<th>Complaints</th>'+
        '</tr>'+
        '</thead>'+
        '</table>';
    $('#table_div').html(tableTemplate);
    $('#table_id').DataTable({
        searching: false,
        data: list,
        columns: [
            { data: 'timePeriod' },
            { data: 'complaintsCount' },
            { data: 'ordersCount' }
        ]
    })
}

// function drawPageNumbers() {
//     var count = getCountOfPages();
//     console.log(count);
//     var html = '<label id="pages">Pages</label>';
//     for(var i=0;i<count;i++){
//         var inc = i+1;
//         html+='<button onclick=drawPageData('+i+')>'+ inc +'</button>';
//     }
//     $('#table_page').html(html);
// }

// function drawPageData(pageNumber) {
//     drawPageNumbers();
//     var list = getDataByPage(pageNumber);
//     console.log(list);
//     $('#table_data').html("");
//     var html = '<table id="test">';
//     html+='<tr>';
//     html+='<th>Date</th>';
//     html+='<th>Orders</th>';
//     html+='<th>Complaints</th>';
//     html+='</tr>';
//     for(var i=0;i<list.length;i++){
//         html+='<tr>';
//         html+='<td>'+list[i][0].toDateString()+'</td>';
//         html+='<td>'+list[i][1]+'</td>';
//         html+='<td>'+list[i][2]+'</td>';
//         html+='</tr>';
//     }
//     html+='</table>';
//     $('#table_data').html(html);
// }