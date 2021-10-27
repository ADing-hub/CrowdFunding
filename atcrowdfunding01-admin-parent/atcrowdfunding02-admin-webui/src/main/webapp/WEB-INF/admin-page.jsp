<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="includex/include-head.jsp" %>
<link rel="stylesheet" href="/css/pagination.css">
<script type="text/javascript" src="/jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        initPageInation()
    });

    // 生成页码导航条的函数
    function initPageInation() {
        // 总记录数
        var totalRecord = "${requestScope.pageInfo.total}";
        // 声明一个JSON对象存储Pagination要设置的熟悉
        var properties = {
            num_edge_entries: 3,                                 //边缘页数
            num_display_entries: 5,                             //主体页数
            callback: pageSelectCallback,                       // 指定用户点击"翻页”的按钮时跳转页面的回调
            items_per_page:${requestScope.pageInfo.pageSize},   // 每页要显示的数量
            current_page:${requestScope.pageInfo.pageNum -1},   // Pagination 内部使用pageIndex来管理页码
            prev_text: "上一页",                                  // 上一页的显示文本
            next_text: "下一页"                                   // 下一页的显示文本
        };
        // 生成导航条页码
        $("#Pagination").pagination(totalRecord, properties);
    };

    // 回调函数的含义：声明出来以后不是自己调用，而是交给系统和框架调用
    // 用户点击“ 1 2 3”这样的页码时调用这个函数
    // pageIndex是Pagination 传给我们的那个从 0 开始的页码
    function pageSelectCallback(pageIndex, jQuery) {
        // 根据pageIndex计算得到pageNum
        var pageNum = pageIndex + 1;
        window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
        // 由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
        return false;
    }
</script>
<body>
<%@ include file="/WEB-INF/includex/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/includex/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="post" class="form-inline" role="form"
                          style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <%--
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='admin/to/add/page.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    --%>
                    <a style="float:right;" href="/admin/to/add/page.html" class="btn btn-primary" ">
                        <i class="glyphicon glyphicon-plus">新增</i>
                    </a>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>

                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有查询到你要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <%--<button type="button" class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i>
                                            </button>--%>
                                            <a href="assign/to/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs">
                                                    <i class=" glyphicon glyphicon-check"></i>
                                                </a>
                                           <%--
                                           <button type="button" class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i>
                                            </button>
                                            --%>
                                            <a href="/admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs">
                                                <i  class=" glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html" class="btn btn-danger btn-xs">
                                                <i class="glyphicon glyphicon-remove"></i>
                                            </a>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
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
</html>
