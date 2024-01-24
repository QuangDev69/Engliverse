<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>

<html>
<head data-layout="vertical" data-topbar="light" data-sidebar="dark"
	data-sidebar-size="lg" data-sidebar-image="none"
	data-preloader="disable">
<title>User List</title>
<link rel="stylesheet" href="<c:url value='/static/css/userList.css'/>"
	type="text/css" />
</head>
<body>

	<h2>User List</h2>
	<div class="add-new">
		<a href="${pageContext.request.contextPath}/admin/users/add">Add
			New User</a>

	</div>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Email</th>
			<th>Birthday</th>
			<th>Phone</th>
			<th>Sex</th>
			<th>Address</th>

			<th>Actions</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>${user.birthday}</td>
				<td>${user.phone}</td>
				<td>${user.sex}</td>
				<td>${user.address}</td>
				<td><a>Edit</a> || <a>Delete</a></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>
