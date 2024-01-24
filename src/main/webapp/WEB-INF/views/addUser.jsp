<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<h2>Add New User</h2>
<form:form action="${pageContext.request.contextPath}/admin/users/add" method="POST" modelAttribute="user">
    <form:label path="username">Username:</form:label>
    <form:input path="username" />

    <form:label path="email">Email:</form:label>
    <form:input path="email" />

    <form:label path="password">Password:</form:label>
    <form:password path="password" />

    <form:label path="birthday">Birthday:</form:label>
    <form:input path="birthday" type="date" />

    <form:label path="address">Address:</form:label>
    <form:input path="address" />

    <form:label path="phone">Phone:</form:label>
    <form:input path="phone" />

    <form:label path="sex">Sex:</form:label>
    <form:radiobutton path="sex" value="MALE" label="Male" />
    <form:radiobutton path="sex" value="FEMALE" label="Female" />
    <form:radiobutton path="sex" value="OTHER" label="Other" />

    <input type="submit" value="Add User" />
</form:form>
</body>
</html>
