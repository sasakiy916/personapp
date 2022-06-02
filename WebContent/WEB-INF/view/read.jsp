<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%
List<Person> list = (List<Person>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/personapp/Create">新規追加</a>
<% if(list != null && list.size() > 0){ %>
<table>
<tr><th>id</th><th>name</th><th>age</th></tr>
<% for(Person person:list){ %>
<tr><td><%=person.getId() %></td>
<td><%=person.getName() %></td>
<td><%=person.getAge() %></td></tr>
<% } %>
</table>
<% } %>
</body>
</html>