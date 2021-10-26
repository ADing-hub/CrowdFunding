<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/includex/include-head.jsp" %>
<%--引入pagination的css--%>
<link href="css/pagination.css" rel="stylesheet" />
<%--引入基于jquery的paginationjs--%>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="/layer/layer.js"></script>
<script type="text/javascript" src="crowd/my-role.js" charset="UTF-8"></script>
<script type="text/javascript">
    $(function () {
        // 设置各个全局变量，方便外部js文件中使用
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        // 调用外部的生成分页的函数
        generatePage();

        // 给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {
            // 设置全局变量的keyword为id=inputKeyword的元素中的内容
            window.keyword = $("#inputKeyword").val();
            // 将页码归为1
            window.pageNum = 1;
            // 重新执行分页操作
            generatePage();
        });

        // 单击添加按钮，打开添加角色的模态框（这段js代码位于jsp文件的$(function())中）
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 单击模态框中的保存按钮，给后端发送要保存的数据
        $("#saveRoleBtn").click(function () {
            // 获取id为addRoleModal的子元素中name为"roleName"的元素的内容，并去空格(trim)
            var roleName = $.trim($("#addModal [name=roleName]").val());

            $.ajax({
                url:"role/do/save.json",
                type:"post",
                data:{
                    "name":roleName
                },
                dataType:"json",
                success:function (response) {
                    // 返回的result为SUCCESS
                    if (response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 进入最后一页 方便显示添加的内容
                        window.pageNum = 999;
                        // 重新生成分页
                        generatePage();
                    }
                    // 返回的result为FAILED
                    if (response.result == "FAILED")
                        layer.msg("操作失败"+response.message)
                },
                error:function (response) {
                    layer.msg("statusCode="+response.status + " message="+response.statusText);
                }

            });

            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理模态框文本框
            $("#addModal [name=roleName]").val("");

        });

        // 给铅笔按钮绑定单击响应函数
        // 注意，如果这里使用简单的$(".pencilBtn").click();来绑定，会发现只在初始页生效，当进入其他页码时，按钮失效
        // 因此，这里使用jQuery的on()函数解决上面的问题
        // on()函数三个传参：1、事件名 ; 2、真正要绑定的按钮的选择器 ; 3、绑定的函数
        $("#rolePageTBody").on("click",".pencilBtn",function () {

            // 打开模态框
            $("#editModal").modal("show");

            // 获取表格中当前行的roleName（通过找父元素的前一个兄弟元素）
            var roleName = $(this).parent().prev().text();

            // 根据pencilBtn的id获得角色id
            // 存放在全局变量中，为了让执行更新操作的按钮可以获取到roleId
            window.roleId = this.id;

            // 将得到的roleName填充到模态框中（id=updateRoleModal的元素的后代元素，且name=roleName的文本框）
            $("#editModal [name=roleName]").val(roleName);

        });

        // 给更新模态框中的更新按钮绑定单击响应函数
        $("#updateRoleBtn").click(function () {

            // 从模态框的文本框中获得修改后的roleName
            var roleName = $("#updateRoleModal [name=roleName]").val();

            $.ajax({
                url: "role/do/update.json",
                type: "post",
                data: {
                    "id":window.roleId,	// 从全局遍历取得当前角色的id
                    "name":roleName
                },
                dataType: "json",
                success:function (response) {
                    if (response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if (response.result == "FAILED")
                        layer.msg("操作失败"+response.message)
                },
                error:function (response) {
                    layer.msg("statusCode="+response.status + " message="+response.statusText);
                }
            });

            // 关闭模态框
            $("#editModal").modal("hide");
            // 由于铅笔按钮每次都会向文本框填充被更新角色的原始数据，因此不需要情况文本框
        });
        // 给多选删除按钮绑定单击事件
        $("#batchRemoveBtn").click(function (){

            // 创建一个数组对象，用来存放后面获得的角色对象
            var roleArray = [];

            // 遍历被勾选的内容
            $(".itemBox:checked").each(function () {
                // 通过this引用当前遍历得到的多选框的id
                var roleId = this.id;

                // 通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();

                roleArray.push({
                    "id":roleId,
                    "name":roleName
                });
            });

            // 判断roleArray的长度是否为0
            if (roleArray.length == 0){
                layer.msg("请至少选择一个来删除");
                return ;
            }

            // 显示确认框
            showConfirmModal(roleArray);
        });


        // 为 “确认删除” 按钮绑定单击事件
        $("#removeRoleBtn").click(function () {

            var arrayStr = JSON.stringify(window.roleIdArray);


            $.ajax({
                url: "role/do/delete/array.json",
                type: "post",
                data: arrayStr,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success:function (response) {
                    if (response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if (response.result == "FAILED")
                        layer.msg("操作失败"+response.message)
                },
                error:function (response) {
                    layer.msg("statusCode="+response.status + " message="+response.statusText);
                }
            });

            // 关闭模态框
            $("#confirmModal").modal("hide");
        });

        // 单击全选框时，使下面的内容全选/全不选
        $("#summaryBox").click(function () {
            // 获取当前状态（是否被选中）
            var currentStatus = this.checked;

            $(".itemBox").prop("checked",currentStatus);

        });

        // 由下面的选择框，改变全选框的勾选状态
        $("#rolePageTBody").on("click",".itemBox",function () {

            // 获取当前已被选中的itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            // 获取当前的所有的itemBox数量
            var currentBoxCount = $(".itemBox").length;

            $("#summaryBox").prop("checked",checkedBoxCount == currentBoxCount);
        });

        // 给单条删除按钮绑定单击事件
        $("#rolePageTBody").on("click",".removeBtn",function () {

            // 通过X按钮删除时，只有一个角色，因此只需要建一个特殊的数组，存放单个对象即可
            var roleArray = [{
                "id": this.id,
                "name": $(this).parent().prev().text()
            }]

            // 调用显示模态框函数，传入roleArray
            showConfirmModal(roleArray);

        });

    });
</script>
    <%@include file="/WEB-INF/includex/include-nav.jsp" %>
<body>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/includex/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" id="inputKeyword" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;" ><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <%--  tbody的id=rolePageTBody,用于绑定on()函数 --%>
                            <tbody id="rolePageTBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页导航条 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <%@include file="modal/role/modal-role-add.jsp" %>
    <%@include file="modal/role/modal-role-edit.jsp" %>
    <%@include file="modal/role/modal-role-confirm.jsp" %>
    <%@include file="modal/role/modal-role-assign-auth.jsp" %>
</body>
</html>
