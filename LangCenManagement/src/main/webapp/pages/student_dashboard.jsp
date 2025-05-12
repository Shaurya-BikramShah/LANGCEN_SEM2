<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/student.css">
</head>
<body>
    <div class="dashboard-container">
        <div class="sidebar">
            <h2>Dashboard</h2>
            <nav>
                <ul>
                    <li><a href="#" class="active">My Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                    <li><a href="#">Missions</a></li>
                    <li><a href="#">Achievements</a></li>
                    <li><a href="#">Programs</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </nav>
        </div>
        <div class="main-content">
            <h1>Welcome, ${user.username}!</h1>
            <div class="profile-details">
                <h2>Profile Details</h2>
                <form action="${pageContext.request.contextPath}/student-dashboard" method="post">
                    <input type="hidden" name="student_id" value="${user.student_id}">
                    <input type="text" name="username" value="${user.username}" readonly>
                    <input type="email" name="email" value="${user.email}">
                    <input type="text" name="phone" value="${user.phone}">
                    <button type="submit" name="action" value="update" class="btn-action">Update Profile</button>
                </form>
            </div>
            <div class="dashboard-grid">
                <div class="dashboard-card">
                    <h2>Achievements</h2>
                    <ul>
                        <li>✔️ Joined 3 Programs</li>
                        <li>🏆 Leadership Badge</li>
                    </ul>
                </div>
                <div class="dashboard-card">
                    <h2>Current Mission</h2>
                    <p>Enroll in Growth Bootcamp</p>
                    <p>Status: <span class="status in-progress">In Progress</span></p>
                </div>
                <div class="dashboard-card">
                    <h2>Programs</h2>
                    <p>🌱 Growth Accelerator</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>