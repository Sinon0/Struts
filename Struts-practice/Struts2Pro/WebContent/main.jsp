<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页面</title>
</head>
<body>      
<s:if test="#session.user==null">
     未登录
</s:if>
<s:else>
      当前登录用户名为:<s:property value="#session.user.userName"/>
</s:else>
<br/>
<br/><br/>
<a href="${pageContext.request.contextPath }/register.jsp">注册</a>
<a href="${pageContext.request.contextPath }/login.jsp">登陆</a>
<a href="${pageContext.request.contextPath }/user/list">显示用户</a>
</body>
</html>