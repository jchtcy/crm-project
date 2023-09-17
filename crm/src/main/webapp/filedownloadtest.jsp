<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <title>演示文件下载</title>
    <script type="text/javascript">
        $(function () {
            // 给"下载"按钮添加单击事件
            $("#fileDownloadButton").click(function () {
               // 发送文件下载请, 文件下载的请求只能发同步请求, 不能发异步请求
                window.location.href = "workbench/activity/fileDowwnload.do"
            });
        })
    </script>

</head>
<body>
<input type="button" value="下载" id="fileDownloadButton">
</body>
</html>
