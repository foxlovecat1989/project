<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><b>Watch_List</b></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4><b>List</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="myTable">
                        <thead>
                            <tr>
                                <th>分類</th><th>名稱</th><th>代號</th>
                                <th>昨收</th><th>報價</th><th>漲跌</th>
                                <th>漲跌幅%</th><th>交易量</th><th>交易時間</th>
                                <th>買進</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- 自動插入列表內容 -->
                        </tbody>
                    </table>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4><b>Chart</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <!-- Chart 繪圖容器 -->
                    <div id="container" style="width:90%; height: 400px; margin:10px"></div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->




<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>

    var watch_id = ${sessionScope.watch_id};
    
    $(document).ready(function () {

        $('#myTable').DataTable({
            responsive: true
        });

        watchList();
        // 走勢圖
        $("#myTable").on("click", "tr td:nth-child(3)", function () {
            var symbol = $(this).text();
            //alert(symbol);
            queryHistQuotes(symbol);
        });
        // 下單
        $("#myTable").on("click", "tr td:nth-child(10)", function () {
            var tstock_id = $(this).attr('tstock_id');
            if (tstock_id == '')
                return;
            if (confirm("是否要買進？")) {
                amount = prompt("請輸入購買股數(請以1000股為單位)？", "1000");
                if (amount == null)
                    return;
//                        if(parseInt(amount) % 1000 != 0) {
//                            alert('請輸入1000的倍數(1張=1000股)');
//                            return;
//                        }
                $.ajax({
                    url: "${pageContext.request.contextPath}/app/portfolio/order/buy/" + tstock_id + "/" + amount,
                    type: "GET",
                    async: true,
                    cache: false,
                    processData: false, //To avoid making query String instead of JSON
                    success: function (resposeJsonObject) {
                        alert('成交回報: ' + resposeJsonObject);
                    }
                });
            }
        });
    });

    function watchList() {
        $.get("${pageContext.request.contextPath}/app/portfolio/watch/" + watch_id, function (data, status) {
            console.log(JSON.stringify(data));
            // 請撰寫
            $("#myTable tbody > tr").remove();
            $.each(data.tStocks, function (i, item) {
                var html = '<tr>' +
                        '<td align="center">{0}</td><td>{1}</td><td>{2}</td>' +
                        '<td align="right">{3}</td><td align="right">{4}</td><td align="right">{5}</td>' +
                        '<td align="right">{6}</td><td align="right">{7}</td><td>{8}</td>' +
                        '<td tstock_id="{9}">{10}</td>' +
                        '</tr>';
                var tstock_id = "";
                var buybtn_html = " ";
                if (item.classify.tx) {
                    buybtn_html = '<button type="button" class="btn btn-info">下單</button>';
                    tstock_id = item.id;
                }
                $('#myTable').append(String.format(html,
                        item.classify.name,
                        item.name,
                        item.symbol,
                        item.preClosed,
                        item.price,
                        item.changePrice,
                        item.changeInPercent,
                        numberFormat(item.volumn),
                        getYMDHMS(item.transactionDate),
                        tstock_id,
                        buybtn_html));
            });
        });
    }
</script>

<!-- Chart 繪圖 -->
<script type = "text/javascript" src = "https://www.gstatic.com/charts/loader.js"></script>
<script>
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(function () {
        queryHistQuotes('^TWII');
    });

    function queryHistQuotes(symbol) {
        $.get("${pageContext.request.contextPath}/app/portfolio/price/histquotes/" + symbol, function (quotes, status) {
            console.log("quotes: " + quotes);
            drawChart(symbol, quotes);
        });
    }

    function drawChart(symbol, quotes) {
        // 建立 data 欄位
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'High');
        data.addColumn('number', 'Open');
        data.addColumn('number', 'Close');
        data.addColumn('number', 'Low');
        data.addColumn('number', 'AdjClose');
        data.addColumn('number', 'Volumn');

        $.each(quotes, function (i, item) {
            var array = [getMD(quotes[i].date), quotes[i].high, quotes[i].open, quotes[i].close, quotes[i].low, quotes[i].adjClose, quotes[i].volume];
            data.addRow(array);
        });

        // 設定 chart 參數
        var options = {
            title: symbol + ' 日K線圖',
            legend: 'none',
            vAxes: [
                {},
                {minValue: 1, maxValue: 6000000}
            ],
            series: {
                1: {targetAxisIndex: 0, type: 'line', color: '#e7711b'},
                2: {targetAxisIndex: 1, type: 'bars', color: '#cccccc'}
            },
            candlestick: {
                fallingColor: {strokeWidth: 0, fill: '#0f9d58'}, // green
                risingColor: {strokeWidth: 0, fill: '#a52714'}   // red
            },
            chartArea: {left: 50}
        };
        // 產生 chart 物件
        var chart = new google.visualization.CandlestickChart(document.getElementById('container'));
        // 繪圖
        chart.draw(data, options);
    }
</script>        