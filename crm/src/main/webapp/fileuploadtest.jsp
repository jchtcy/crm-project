<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <title>演示文件上传</title>

</head>
<body>
<!--
    文件上传个三个条件:
    1、表单组件标签只能用:<input type="file">
        不能用<input type="text|password|radio|checkbox|hidden|button|submit|reset">,<select>,<texttarea>
    2、请求方式只能用:post
    3、form表单的编码格式只能用: multipart/form-data
        根据HTTTP协议, 浏览器每次向后台提交参数, 都会对参数进行统一编码,
        默认的编码格式是urlencoded,这种数据只能对文本数据进行编码,
        浏览器每次向后台提交数据, 都会首先把所有的参数转换成字符串,然后对数据统一进行urlencoded编码
-->
<form action="workbench/activity/fileUpload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="myFile"><br>
    <input type="text" name="username"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
