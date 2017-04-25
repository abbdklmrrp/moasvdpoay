<%--
  Created by IntelliJ IDEA.
  User: Aleksandr
  Date: 20.04.17
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['line']});
        google.charts.load('current', {'packages': ['table']});

        function getStatisticList() {
            var rowList = [];
            var list;
            var len;
            jQuery.ajax({
                url: 'graphData',
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

        function drawChart() {

            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Day');
            data.addColumn('number', 'Orders');
            data.addColumn('number', 'Complaints');


            var dataList = getStatisticList();
            if (dataList == null) {
                $('#err').html("Error while sending request");
                return;
            }
            data.addRows(dataList);

            var item_data = $('#formWithRegionsAndDates').serialize();
            console.log(item_data);

            var select = document.getElementById('sel1');
            var options = {
                chart: {
                    title: 'Statistic in ' + select.value,
                    subtitle: 'NetCrooker'
                },
                width: 800,
                height: 400,
                axes: {
                    x: {
                        0: {side: 'top'}
                    }
                }
            };

            var chart = new google.charts.Line(document.getElementById('line_top_x'));
            chart.draw(data, google.charts.Line.convertOptions(options));
            var table = new google.visualization.Table(document.getElementById('table_div'));
            table.draw(data, {showRowNumber: false, width: '100%', height: '300px'});
        }

    </script>
</head>
<body>
<form id="formWithRegionsAndDates">
    <label>Choose region:</label>
    <select id="sel1" name="region">
        <c:forEach var="region" items="${regions}">
            <option value="${region}">${region}</option>
        </c:forEach>
    </select>
    <br/>
    <label>Date Begin:</label>
    <input type="date" name="beginDate"/>
    <br/>
    <label>Date End:</label>
    <input type="date" name="endDate"/>
    <br/>
</form>
<button id="b1" onclick="drawChart()">Show</button>
<span id="err" style="color: red"></span>
<div id="line_top_x"></div>
<br/>
<div id="table_div"></div>
</body>
</html>