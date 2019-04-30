<%--
  Created by IntelliJ IDEA.
  User: hrjin
  Date: 2019-03-22
  Time: 오전 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="../../resources/images/favicon.ico"/>">
    <title>마켓 플레이스 어드민 포탈 로그인 페이지</title>
</head>
<body>

<div class="login-box-body">
    <p class="login-box-msg" id="warning" style="color: red;"></p>

    <div class="form-group text-center mt65 ">
        <div><span class="login-font2">마켓 플레이스 어드민 포탈에 오신 것을 환영합니다.</span></div>
    </div>

    <div class="row">
        <!-- /.col -->
        <div class="col-xs-4">
            <button type="button" style="align-content: center" name="button" id="loginBtn" class="btn btn-primary btn-block btn-flat" onclick="location.href='/admin'">로그인</button>
        </div>
        <!-- /.col -->

    </div>

</div>
<!-- /.login-box-body -->

</body>
</html>

<script type="text/javascript">

</script>