<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/jqlib/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/layer/layer.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".delete").click(function(){
		var str=this.lang.split("!");
		if(!confirm("你真的要删除【"+str[1]+"】这个用户吗")){
			return;
		}
		$.post("${pageContext.request.contextPath }/user/delete",{"user.id":str[0]},function(){
			location.href="list";
		});
	});
	$(".modify").click(function(){
		location.href="${pageContext.request.contextPath}/user/modify?user.id="+this.lang;
	});
	
	$(".picture").click(function(){
		layer.use('extend/layer.ext.js');
		$.getJSON("${pageContext.request.contextPath}/picture/getpics",
				{"picture.uid":this.lang},function(data){
					layer.photos({
						html:"",
					    json:data
					});
			});
	});
	$(".picture").each(function(i,e){
		$.post("${pageContext.request.contextPath}/picture/getnum",
				{"picture.uid":e.lang},function(data){
					e.innerHTML=e.innerHTML+"("+data+")";
				});
	});
});

</script>
</head>
<body>
用户列表
<table class="bordered">
<tr><th>序号</th><th>用户名</th><th>密码</th><th>图片</th><th>删除</th><th>修改</th></tr>
<s:iterator value="#USERLIST" status="s" id="cuser">
<tr><td><s:property value="#s.index+1"/></td>
<td><s:property value="#cuser.userName"/></td>
<td><s:property value="#cuser.pwd"/></td>
<td><a href="#" class="picture" lang="<s:property value="#cuser.id"/>">图片</a></td>
<td><a href="#" class="delete" lang="<s:property value="#cuser.id"/>!<s:property value="#cuser.userName"/>">删除</a></td>
<td><a href="#" class="modify" lang="<s:property value="#cuser.id"/>">修改</a></td></tr>
</s:iterator>
</table>
</body>
<a href="${pageContext.request.contextPath }/main.jsp">返回主界面</a>
<s:debug></s:debug>
</html>