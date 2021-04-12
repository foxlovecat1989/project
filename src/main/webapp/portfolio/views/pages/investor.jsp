<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><b>Investor</b></h1>
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
                            <input id="username" name="username" class="form-control" placeholder="Name" />
                        </div>
                        <div class="form-group">
                            <input id="password" name="password" class="form-control" placeholder="Password" />
                        </div>
                        <div class="form-group">
                            <input id="email" name="email" class="form-control" placeholder="Email" />
                        </div>
                        <div class="form-group">
                            <input id="balance" name="balance" class="form-control" placeholder="Balance" />
                        </div>
                        <div class="form-group">
                            會員級別：<select id="role_id" name="role_id"></select><p /> 
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
                                <th>Passwords</th>
                                <th>Email</th>
                                <th>Balance</th>
                                <th>Code</th>
                                <th>Enabled</th>
                                <th>Roles</th>
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

        $('#myTable').DataTable({
            responsive: true
        });

        $("#myTable").on("click", "tr", function () {
            var id = $(this).find('td').eq(0).text().trim();
            //console.log(id);
            $.get("${pageContext.request.contextPath}/app/portfolio/investor/" + id, function (data, status) {
                console.log(JSON.stringify(data));
                $("#myForm").find("#id").val(data.id);
                $("#myForm").find("#username").val(data.username);
                $("#myForm").find("#password").val(data.password);
                $("#myForm").find("#email").val(data.email);
                $("#myForm").find("#balance").val(data.balance);
                $("#msg").text("");
                $("#add").attr("disabled", true);
                $("#upt").attr("disabled", false);
                $("#del").attr("disabled", false);
                $("#username").attr("readonly", true);
            });
        });

        $("#add").on("click", function () {
            var jsonObj = $('#myForm').serializeObject();
            var jsonStr = JSON.stringify(jsonObj);
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/investor/",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonStr,
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                   // alert(JSON.stringify(resposeJsonObject));
                    resetForm();
                    table_list();
                }
            });
        });

        $("#upt").on("click", function () {
            var jsonObj = $('#myForm').serializeObject();
            var jsonStr = JSON.stringify(jsonObj);
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/investor/" + jsonObj.id,
                type: "PUT",
                contentType: "application/json; charset=utf-8",
                data: jsonStr,
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                    resetForm();
                    table_list();
                }
            });
        });

        $("#del").on("click", function () {
            var id = $("#myForm").find("#id").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/app/portfolio/investor/" + id,
                type: "DELETE",
                async: true,
                cache: false,
                processData: false,
                success: function (resposeJsonObject) {
                    resetForm();
                    table_list();
                }
            });
        });

        $("#username").blur(function () {
            console.log($("#username").val());
            if (!$("#upt").is(":disabled")) {
                return;
            }
            var username = $("#username").val();
            $.get("${pageContext.request.contextPath}/app/portfolio/investor/duplicate/" + username, function (data, status) {
                console.log(data);
                if (data) {
                    $("#add").attr("disabled", true);
                    $("#msg").text("重複名稱");
                } else {
                    $("#add").attr("disabled", false);
                    $("#msg").text("");
                }
            });
        });
        
        // role 下拉選單
        role_list();

        // 資料列表
        table_list();
    });

// myform重製方法
    function resetForm() {
        $("#myForm").get(0).reset();
        $("#msg").text("");
        $("#username").attr("readonly", false);
        $("#add").attr("disabled", false);
        $("#upt").attr("disabled", true);
        $("#del").attr("disabled", true);
    }
    function table_list() {
        $.get("${pageContext.request.contextPath}/app/portfolio/investor/", function (datas, status) {
            // console.log("Datas: " + datas);
            $("#myTable tbody > tr").remove(); // 將之前的資料清空
            $.each(datas, function (i, item) {  // 將 datas 取出格式化成表格欄位
                var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td></tr>';
                $('#myTable').append(String.format(html,
                        item.id,
                        item.username,
                        item.password,
                        item.email,
                        item.balance,
                        item.code,
                        item.enabled,
                        item.role.roleType
                )); 
            });
        });
    }
    
    function role_list() {
        $.get("${pageContext.request.contextPath}/app/portfolio/investorRole/", function (datas, status) {
            console.log("Datas: " + datas);
            datas.map(function (data) {
                $('#role_id').append('<option value="' + data.id + '">' + data.roleType + '</option>');
            });
        });
    }

</script>        