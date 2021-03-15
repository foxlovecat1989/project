<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <!-- head -->
        <%@include file="/portfolio/include/head.jspf"  %>

        <script>
            $(document).ready(function () {
               
                $("#myTable").on("click", "tr", function () {
                    var id = $(this).find('td').eq(0).text().trim(); // 找出 tr 裡面的 td, 第一個 td 元素, 也就是 id
                    //console.log(id);
                    $.get("${pageContext.request.contextPath}/app/portfolio/investor/" + id, function (data, status) {
                        // 從路徑取回資料存進 data
                        console.log(JSON.stringify(data));
                        $("#myform").find("#id").val(data.id);
                        $("#myform").find("#username").val(data.username);
                        $("#myform").find("#password").val(data.password);
                        $("#myform").find("#email").val(data.email);
                        $("#myform").find("#balance").val(data.balance);
                    });
                });
                
                $("#add").on("click", function () {
                    var jsonObj = $('#myform').serializeObject();   // 將表單序列化成物件
                    var jsonStr = JSON.stringify(jsonObj);          // 將物件 stringify 成 JSON 格式
                    $.ajax({ // 將資料傳至路徑 - InvestorController
                        url: "${pageContext.request.contextPath}/app/portfolio/investor/",
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        data: jsonStr,
                        async: true,
                        cache: false,
                        processData: false,
                        success: function (resposeJsonObject) {
                            //alert(JSON.stringify(resposeJsonObject));
                            table_list();
                        }
                    });
                });
                
                $("#upt").on("click", function () {
                    var jsonObj = $('#myform').serializeObject();
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
                            table_list();
                        }
                    });
                });
                
                $("#del").on("click", function () {
                    var id = $("#myform").find("#id").val();
                    $.ajax({
                        url: "${pageContext.request.contextPath}/app/portfolio/investor/" + id,
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

            function table_list() {
                $.get("${pageContext.request.contextPath}/app/portfolio/investor/", function (datas, status) {
                    console.log("Datas: " + datas);
                    $("#myTable tbody > tr").remove(); // 將之前的資料清空
                    $.each(datas, function (i, item) {  // 將 datas 取出格式化成表格欄位
                        var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td></tr>';
                        $('#myTable').append(String.format(html,
                                item.id,
                                item.username,
                                item.password,
                                item.email,
                                item.balance,
                                item.code,
                                item.isPassed
                                ));
                    });
                });
            }
            
            
        </script>
    </head>
    <body>
        <div id="layout">
            <!-- Menu toggle -->
            <%@include file="/portfolio/include/toggle.jspf"  %>

            <!-- Menu -->
            <%@include file="/portfolio/include/menu.jspf"  %>

            <div id="main">
                <div class="header">
                    <h1>Investor</h1>
                    <h2>投資人</h2>
                </div>
                <table>
                    <td valign="top">
                        <div class="content">
                            <form id="myform" class="pure-form">
                                <fieldset>
                                    <legend> <h2 class="content-subhead">資料維護</h2></legend>
                                    
                                    <input id="id" vslue="0"   name="id" placeholder="ID" readonly="true"/><p />
                                    <input id="username" name="username" placeholder="username"/><p />
                                    <input id="password" name="password" placeholder="password"/><p />
                                    <input id="email" name="email" placeholder="email"/><p />
                                    <input id="balance" name="balance" placeholder="balance" type="number"/><p />
                                    
                                    <button id="add" type="button" class="pure-button pure-button-primary">新增</button>
                                    <button id="upt" type="button" class="pure-button pure-button-primary">修改</button>
                                    <button id="del" type="button" class="pure-button pure-button-primary">刪除</button>
                                    
                                </fieldset>
                            </form>
                        </div>
                    </td>    
                    <td valign="top">    
                        <div class="content">
                            <form class="pure-form">
                                <fieldset>
                                    <legend><h2 class="content-subhead">資料列表</h2></legend>
                                    <table id="myTable" class="pure-table pure-table-bordered">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>username</th>
                                                <th>password</th>
                                                <th>email</th>
                                                <th>balance</th>
                                                <th>code</th>
                                                <th>pass</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- 自動插入列表內容 -->
                                        </tbody>
                                    </table> 
                                </fieldset>
                            </form>
                        </div>    
                    </td>
                </table>
                

            </div>
        </div>
        <!-- Foot -->
        <%@include file="/portfolio/include/foot.jspf"  %>   
    </body>
</html>
