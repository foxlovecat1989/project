<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><b>Classify</b></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-3">
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
                        <div class="form-group">
                            TX: <input id="tx" name="tx" type="checkbox">
                        </div>

                        <button id="add" type="button" class="btn btn-info">Add</button>
                        <button id="upt" type="button" class="btn btn-warning">Update</button>
                        <button id="del" type="button" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>   
        <!-- /.col-lg-3 -->

        <div class="col-lg-9">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4><b>List</b></h4>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="myTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>TX</th>
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
        <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->



    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>


        $(document).ready(function () {

        $("#myTable").on("click", "tr", function () {
            var id = $(this).find('td').eq(0).text().trim();
            //console.log(id);
            $.get("${pageContext.request.contextPath}/app/portfolio/classify/" + id, function (data, status) {
                console.log(JSON.stringify(data));
                $("#myForm").find("#id").val(data.id);
                $("#myForm").find("#name").val(data.name);
                $("#myForm").find("#tx").prop('checked', data.tx);
            });
        });

        $("#add").on("click", function () {
            var jsonObj = $('#myForm').serializeObject();
            var jsonStr = JSON.stringify(jsonObj);
            console.log(jsonStr);
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/classify/",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonStr,
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                    table_list();
                }
            });
        });

        $("#upt").on("click", function () {
            var jsonObj = $('#myForm').serializeObject();
            var jsonStr = JSON.stringify(jsonObj);
            console.log(jsonStr);
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/classify/" + jsonObj.id,
                type: "PUT",
                contentType: "application/json; charset=utf-8",
                data: jsonStr,
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                    table_list();
                }
            });
        });

        $("#del").on("click", function () {
            var id = $("#myForm").find("#id").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/classify/" + id,
                type: "DELETE",
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                table_list();
                }
            });
        });

        // 資料列表
        table_list();
    });

            // myform重製方法
            function resetForm() {
                $("#myForm").get(0).reset();
                $("#msg").text("");
                $("#id").attr("readonly", false);
                $("#name").attr("disabled", false);
                $("#tx").attr("disabled", true);
                $("#add").attr("disabled", true);
                $("#upt").attr("disabled", false);
                $("#del").attr("disabled", false);
            }
            
            function table_list() {
                $.get("${pageContext.request.contextPath}/app/portfolio/classify/", function (datas, status) {
                    console.log("Datas: " + datas);
                    $("#myTable tbody > tr").remove();
                    $.each(datas, function (i, item) {
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td></tr>';
                        $('#myTable').append(String.format(html,
                                item.id,
                                item.name,
                                item.tx
                        ));
                    });
                });
            }

</script>        