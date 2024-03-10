<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>
    <form action="/register" method="POST">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
          <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <button type="button" onclick="togglePasswordVisibility('password')">Show/Hide</button>
        </div>
        <div>
            <input type="submit" value="Register">
        </div>
    </form>

    <script>
        function togglePasswordVisibility(fieldId) {
            var passwordField = document.getElementById(fieldId);
            if (passwordField.type === "password") {
                passwordField.type = "text";
            } else {
                passwordField.type = "password";
            }
        }
    </script>
</body>
</html>
