<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/jqlib/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/layer/layer.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".delete").click(function(){
		var str=this.lang.split("!");
		if(!confirm("你真的要删除【"+str[1]+"】这张照片吗")){
			return;
		}
		$.post("${pageContext.request.contextPath}/picture/delete",
				{"picture.id":str[0]},function(){
			location.href="${pageContext.request.contextPath}/user/list";
		});
	});
	$(".display").click(function(){
		layer.use('extend/layer.ext.js');
		$.getJSON("${pageContext.request.contextPath}/picture/getpic",
				{"picture.id":this.lang,"idType":"picture"},function(data){
					layer.photos({
						html:"",
					    json:data
					});
				});
	});
});
</script>
</head>
<body>
修改用户信息
<br/>
<br/>
<form action="${pageContext.request.contextPath }/user/save" method="post">
<table>
<tr><td>用户名</td><td><input type="text" name="user.userName" value="<s:property value="user.userName"/>"/></td></tr>
<tr><td>密码</td><td><input type="text" name="user.pwd" value="<s:property value="user.pwd"/>"/></td></tr>
<tr><td><input type="hidden" value="<s:property value="user.id"/>" name="user.id"/>
<input type="submit" value="修改"/></td></tr>
</table>
</form>
<br/>
<br/>
<s:if test="%{#PICTURES.size()>0}">
显示用户所有照片
<br/>
<br/>
<table class="bordered">
<thead>
  <tr><th>序号</th><th>照片名称</th><th>显示</th><th>删除</th></tr>
</thead>
<s:iterator value="PICTURES" id="cpic" status="s">
 <tr>
   <td><s:property value="#s.index+1"/></td>
   <td><s:property value="#cpic.name"/></td>
   <td><a href="#" class="display" lang="<s:property value="#cpic.id"/>">显示</a></td>
   <td><a href="#" class="delete" lang="<s:property value="#cpic.id"/>!<s:property value="#cpic.name"/>">删除</a></td>
 </tr>
</s:iterator>
</table>
</s:if>

上传照片
<br/>
<br/>
<form action="${pageContext.request.contextPath }/picture/add" enctype="multipart/form-data" method="post">
<table>

<tr><td>选择照片</td><td><input type="file" name="image"/></td></tr>
<tr><td>照片名称</td><td><input type="text" name="pictures[0].name"/>
<input type="hidden" name="pictures[0].uid" value="<s:property value="user.id"/>"/>
</td></tr>

<tr><td>选择照片</td><td><input type="file" name="image"/></td></tr>
<tr><td>照片名称</td><td><input type="text" name="pictures[1].name"/>
<input type="hidden" name="pictures[1].uid" value="<s:property value="user.id"/>"/>
</td></tr>
<tr><td colspan="2"><input type="submit" value="提交"/></td></tr>

</table>
</form>
<br/>   
<br/>
<a href="${pageContext.request.contextPath }/main.jsp">返回主界面</a>
<s:debug></s:debug>
</body>
</html>