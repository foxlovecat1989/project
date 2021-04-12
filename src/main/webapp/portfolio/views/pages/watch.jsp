<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><b>我的觀察股, watch_id: ${sessionScope.watch_id}</b></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-2">
            <div class="panel panel-red">
                <div class="panel-heading">
                    <h4><b>Maintenance</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form role="form" id="myForm">
                        <div class="form-group">
                            <input id="id" name="id" class="form-control" placeholder="ID" readonly="true" />
                        </div>
                        <div class="form-group">
                            <input id="name" name="name" class="form-control" placeholder="Name" />
                        </div>

                        <button id="upt" type="button" class="btn btn-info">Modify</button>

                    </form>
                </div>
            </div>
        </div>   
        <!-- /.col-lg-2 -->

        <div class="col-lg-5">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4><b>List</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="myTable1">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Symbol</th>
                                <th>Classify</th>
                                <th>Remove</th>
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
        <!-- /.col-lg-5 -->
        
        <div class="col-lg-5">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4><b>List</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="myTable2">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Symbol</th>
                                <th>Classify</th>
                                <th>Add</th>
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
        <!-- /.col-lg-5 -->
        
     

    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->



<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    var watch = null;
    
    var watch_id = ${sessionScope.watch_id};

    $(document).ready(function () {

        $("#upt").on("click", function () {
            var jsonObj = $('#myForm').serializeObject();
            var jsonStr = JSON.stringify(jsonObj);
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/watch/" + watch_id,
                type: "PUT",
                contentType: "application/json; charset=utf-8",
                data: jsonStr,
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                    table_list1();
                }
            });
        });

        $("#myTable1").on("click", "tr td:nth-child(5)", function () {
            var tstock_id = $(this).attr('tstock_id');
            if (confirm("是否要刪除？" + tstock_id)) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/app/portfolio/watch/" + watch_id + "/remove/" + tstock_id,
                    type: "DELETE",
                    async: true,
                    cache: false,
                    processData: false,
                    success: function (resposeJsonObject) {
                        table_list1();
                    }
                });
            }
        });

        $("#myTable2").on("click", "tr td:nth-child(5)", function () {
            // 判斷該 tstock_id 是否已經加入 Watch ?
            var tstock_id = $(this).attr('tstock_id');
            var flag = true;
            if (watch != null && watch.tStocks != null) {
                $.each(watch.tStocks, function (i, item) {
                    if (item.id == tstock_id) {
                        alert(item.name + ' ' + item.symbol + ' 已加入！');
                        flag = false;
                        return;
                    }
                });
            }
            if (flag && confirm("是否要增加？")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/app/portfolio/watch/" + watch_id + "/add/" + tstock_id,
                    type: "GET",
                    async: true,
                    cache: false,
                    processData: false,
                    success: function (resposeJsonObject) {
                        table_list1();
                    }
                });
            }
        });

        // 資料列表(Watch List)
        table_list1();
        // 資料列表(TStock List)
        table_list2();
    });


            // 1. 透過 watch_id 找到 watch
            // 2. 再透過 watch.tStocks 取得觀察股(tstock)資料
            function table_list1() {
                $.get("${pageContext.request.contextPath}/app/portfolio/watch/" + watch_id, function (data, status) {
                    console.log(JSON.stringify(data));
                    $("#myForm").find("#id").val(data.id);
                    $("#myForm").find("#name").val(data.name);
                    watch = data; // 設定 watch 變數資料
                    
                    $("#myTable1 tbody > tr").remove();
                    
                    $.each(watch.tStocks, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td tstock_id="{4}">{5}</td></tr>';
                        delbtn_html = '<button type="button" class="btn btn-info">Remove</button>';
                        $('#myTable1').append(String.format(html,
                                item.id,
                                item.name,
                                item.symbol,
                                item.classify.name,
                                item.id,
                                delbtn_html
                                ));
                    });
                });
            }
            
            // 取得所有 tstock 列表資料
            function table_list2() {
                $.get("${pageContext.request.contextPath}/app/portfolio/tstock/", function (datas, status) {
                    console.log(JSON.stringify(datas));
                    $("#myTable2 tbody > tr").remove();
                    $.each(datas, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td tstock_id="{4}">{5}</td></tr>';
                        addbtn_html = '<button type="button" class="btn btn-info">Add</button>';

                        $('#myTable2').append(String.format(html,
                                item.id,
                                item.name,
                                item.symbol,
                                item.classify.name,
                                item.id,
                                addbtn_html
                                ));
                    });
                });
            }

</script>        