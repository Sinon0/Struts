<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<script type="text/javascript" src="jqlib/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	$("[name='user.userName']").blur(function(){
		if($.trim($(this).val())==""){
			return;
		}
		$.post("${pageContext.request.contextPath }/user/checkExists",{"user.userName":$(this).val(),"user.pwd":""},function(data){
			//已存在返回1，不存在返回0
			$("[name='isExists']").val(data);
			if(data=="1"){
				$("#msg").html("该用户名已经注册，请重新输入");
			}
		});
	});
  $("[type='submit']").click(function(){
	  if($.trim($("[name='user.userName']").val())==""){
		  $("#msg").html("用户名不能为空，请输入用户名");
		  return false;
	  }
	 if($("[name='isExists']").val()=="1"){
		 $("#msg").html("用户名重复，请重新输入");
		 return false;
	 }
	 if($.trim($("[name='user.pwd']").val())==""){
		 $("#msg").html("密码不能为空，请重新输入");
	 }
	 return true;
  });
});
</script>
</head>
<body>
<br>
用户注册
<br/>
<br/>
<form action="${pageContext.request.contextPath }/user/add" method="post">
<table>
<tr><td>用户名:</td><td><input type="text" name="user.userName"/></td></tr>
<tr><td>密&nbsp;码:</td><td><input type="password" name="user.pwd"/></td></tr>
<tr><td colspan="2"><input type="submit" value="提交"/></td></tr>
</table>
</form>
<input type="hidden" name="isExists" value="0"/>
<div id="msg"></div>
<br/>
<br/>
<a href="${pageContext.request.contextPath }/main.jsp">返回主界面</a>
</body>
</html>