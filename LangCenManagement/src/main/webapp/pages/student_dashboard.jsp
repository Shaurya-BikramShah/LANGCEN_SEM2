<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/student.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <h2>Dashboard</h2>
            <nav>
                <ul>
                    <li><a href="#" class="btn-profile active">
                        <i class="fas fa-user"></i> My Profile
                    </a></li>
                    <li><a href="#">
                        <i class="fas fa-home"></i> Home
                    </a></li>
                    <li><a href="#">
                        <i class="fas fa-tasks"></i> Missions
                    </a></li>
                    <li><a href="#">
                        <i class="fas fa-trophy"></i> Achievements
                    </a></li>
                    <li><a href="#">
                        <i class="fas fa-graduation-cap"></i> Programs
                    </a></li>
                    <li><a href="#">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </a></li>
                </ul>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <div class="system-info">
                <small>Current Date and Time (UTC - YYYY-MM-DD HH:MM:SS formatted): 2025-04-29 20:43:48</small>
                <small>Current User's Login: Shaurya-BikramShah</small>
            </div>

            <div class="welcome-section">
                <h1>Welcome, ${studentName != null ? studentName : 'Student'}</h1>
            </div>

            <div class="profile-section">
                <h2>Profile Details</h2>
                <form class="profile-form">
                    <div class="form-group">
                        <textarea placeholder="Bio" rows="3">${studentBio != null ? studentBio : ''}</textarea>
                    </div>
                    <div class="form-group">
                        <textarea placeholder="Skills" rows="3">${studentSkills != null ? studentSkills : ''}</textarea>
                    </div>
                    <div class="form-group">
                        <textarea placeholder="Interests" rows="3">${studentInterests != null ? studentInterests : ''}</textarea>
                    </div>
                    <button type="submit" class="btn-update">Update Profile</button>
                </form>
            </div>

            <div class="dashboard-grid">
                <!-- Achievements Section -->
                <div class="dashboard-card">
                    <h2>Achievements</h2>
                    <ul class="achievement-list">
                        <li>
                            <i class="fas fa-check-circle success"></i>
                            Joined 3 Programs
                        </li>
                        <li>
                            <i class="fas fa-award"></i>
                            Leadership Badge
                        </li>
                    </ul>
                </div>

                <!-- Current Mission Section -->
                <div class="dashboard-card">
                    <h2>Current Mission</h2>
                    <div class="mission-details">
                        <p>Enroll in Growth Bootcamp</p>
                        <p class="status">Status: <span class="in-progress">In Progress</span></p>
                    </div>
                </div>

                <!-- Programs Section -->
                <div class="dashboard-card">
                    <h2>Programs</h2>
                    <ul class="program-list">
                        <li>
                            <i class="fas fa-rocket"></i>
                            Growth Accelerator
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>S