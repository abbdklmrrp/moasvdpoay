google.charts.load('current', {'packages': ['corechart']});

function drawChart() {

    var data = new google.visualization.DataTable();
    data.addColumn('date', 'Day');
    data.addColumn('number', 'Orders');
    data.addColumn('number', 'Complaints');


    var dataList = getStatisticList();
    if (dataList == null) {
        $('#err').html("No data for this period");
        return;
    }
    data.addRows(dataList);
    drawPageData(0);
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

    // var chart = new google.charts.Line(document.getElementById('line_top_x'));
    // chart.draw(data, google.charts.Line.convertOptions(options));
    var chart = new google.visualization.LineChart(document.getElementById('line_top_x'));
    chart.draw(data, options);
}

function drawPageNumbers() {
    var count = getCountOfPages();
    console.log(count);
    var html = '<label id="pages">Pages</label>';
    for(var i=0;i<count;i++){
        var inc = i+1;
        html+='<button onclick=drawPageData('+i+')>'+ inc +'</button>';
    }
    $('#table_page').html(html);
}

function drawPageData(pageNumber) {
    drawPageNumbers();
    var list = getDataByPage(pageNumber);
    console.log(list);
    $('#table_data').html("");
    var html = '<table>';
    html+='<tr>';
    html+='<th>Date</th>';
    html+='<th>Orders</th>';
    html+='<th>Complaints</th>';
    html+='</tr>';
    for(var i=0;i<list.length;i++){
        html+='<tr>';
        html+='<td>'+list[i][0].toDateString()+'</td>';
        html+='<td>'+list[i][1]+'</td>';
        html+='<td>'+list[i][2]+'</td>';
        html+='</tr>';
    }
    html+='</table>';
    $('#table_data').html(html);
}