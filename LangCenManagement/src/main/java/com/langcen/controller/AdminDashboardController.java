package com.langcen.controller;

import com.langcen.model.StudentModel;
import com.langcen.service.DashboardService;
import com.langcen.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin-dashboard")
public class AdminDashboardController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdminDashboardController.class.getName());
    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<StudentModel> students = dashboardService.getAllStudents();
            request.setAttribute("students", students);
            request.getRequestDispatcher("/pages/admin_dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching students", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load the admin dashboard.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    validateAndAddStudent(request, response);
                    break;
                case "update":
                    validateAndUpdateStudent(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing action: " + action, e);
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/pages/admin_dashboard.jsp").forward(request, response);
        }
    }

    private void validateAndAddStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> errors = validateInputs(request);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            doGet(request, response);
            return;
        }

        StudentModel student = extractStudentFromRequest(request);
        dashboardService.addStudent(student);
        response.sendRedirect("admin-dashboard");
    }

    private void validateAndUpdateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> errors = validateInputs(request);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            doGet(request, response);
            return;
        }

        StudentModel student = extractStudentFromRequest(request);
        dashboardService.updateStudent(student);
        response.sendRedirect("admin-dashboard");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            request.setAttribute("errorMessage", "Username is required to delete a student.");
            doGet(request, response);
            return;
        }

        dashboardService.deleteStudent(username);
        response.sendRedirect("admin-dashboard");
    }

    private List<String> validateInputs(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String roleId = request.getParameter("roleId");

        if (!ValidationUtil.validateFullName(fullName)) {
            errors.add("Full name must be 3-50 alphabetic characters.");
        }
        if (!ValidationUtil.validateUsername(username)) {
            errors.add("Username must be at least 7 characters long and include only letters, numbers, and underscores.");
        }
        if (!ValidationUtil.validateEmail(email)) {
            errors.add("Invalid email format.");
        }
        if (!ValidationUtil.validatePhone(phone)) {
            errors.add("Phone number must be in the format +XXXXXXXXXXXXX (13 digits).");
        }
        if (!ValidationUtil.validatePassword(password)) {
            errors.add("Password must include uppercase, lowercase, number, and special character.");
        }
        try {
            Integer.parseInt(roleId);
        } catch (NumberFormatException e) {
            errors.add("Role ID must be a valid integer.");
        }

        return errors;
    }

    private StudentModel extractStudentFromRequest(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String profilePicture = request.getParameter("profilePicture");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        StudentModel student = new StudentModel(fullName, username, email, phone, password, null, profilePicture);
        student.setRoleId(roleId);
        return student;
    }
}