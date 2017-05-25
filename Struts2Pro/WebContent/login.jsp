<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面 </title>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqlib/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	$("[type='button']").click(function(){
		if($.trim($("[name='userName']").val())==""){
			$("[name='userName']").select();
			$("[name='userName']").focus();
			$("#msg").html("请输入用户名");
			return;
		}
		if($.trim($("[name='pwd']").val())==""){
			$("[name='pwd']").select();
			$("[name='pwd']").focus();
			$("#msg").html("请输入密码");
			return;
		}
		$.post("${pageContext.request.contextPath }/user/checkLogin",{"user.userName":$("[name='userName']").val(),"user.pwd":$("[name='pwd']").val()},function(data){
			if(data=="1"){
				location.href="main.jsp";
			}
			else{
				$("#msg").html("用户名密码错误，请重新输入");
			}
		});
	});
});
</script>
</head>
<body>
登陆页面
<br/>
<br/>
<form method="post">
<table>
<tr><td>用户名:</td><td><input type="text" name="userName"/></td></tr>
<tr><td>密&nbsp;码:</td><td><input type="password" name="pwd"/></td></tr>
<tr><td colspan="2"><input type="button" value="提交"/></td></tr>
</table>
</form>
<div id="msg"></div>
<br/>
<br/>
<a href="${pageContext.request.contextPath }/main.jsp">返回主界面</a>
</body>
</html>