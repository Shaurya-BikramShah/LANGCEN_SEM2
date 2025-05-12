package com.langcen.service;

import com.langcen.model.ProgramModel;
import com.langcen.model.StudentModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardService {

    private static final Logger LOGGER = Logger.getLogger(DashboardService.class.getName());
    private final String DB_URL = "jdbc:mysql://localhost:3306/langcen?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    public List<StudentModel> getAllStudents() {
        List<StudentModel> students = new ArrayList<>();
        String query = "SELECT sp.student_id, sp.username, sp.full_name, sp.email, sp.phone, sp.status, "
                + "p.program_code, p.name AS program_name, r.role_name "
                + "FROM student_profile sp "
                + "LEFT JOIN student_enrollment se ON sp.student_id = se.student_id "
                + "LEFT JOIN program p ON se.program_id = p.program_id "
                + "LEFT JOIN roles r ON sp.role_id = r.role_id";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StudentModel student = new StudentModel();
                student.setStudentId(rs.getInt("student_id"));
                student.setUsername(rs.getString("username"));
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setStatus(rs.getString("status"));

                // Program details
                if (rs.getString("program_code") != null) {
                    ProgramModel program = new ProgramModel();
                    program.setProgramCode(rs.getString("program_code"));
                    program.setName(rs.getString("program_name"));
                    student.setProgram(program);
                }

                students.add(student);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching students", e);
        }
        return students;
    }

    public void addStudent(StudentModel student) throws Exception {
        if (isUsernameTaken(student.getUsername())) throw new Exception("Username is already taken.");
        if (isEmailTaken(student.getEmail())) throw new Exception("Email is already registered.");
        if (isPhoneTaken(student.getPhone())) throw new Exception("Phone number is already registered.");

        String query = "INSERT INTO student_profile (username, password, full_name, email, phone, role_id, profile_picture, status, created_at, created_by) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, student.getUsername());
            stmt.setString(2, student.getPassword());
            stmt.setString(3, student.getFullName());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPhone());
            stmt.setInt(6, student.getRoleId());
            stmt.setString(7, student.getProfilePicture());
            stmt.setString(8, student.getStatus());
            stmt.setString(9, "admin");

            stmt.executeUpdate();
        }
    }

    public void updateStudent(StudentModel student) throws Exception {
        if (!isUsernameTaken(student.getUsername())) throw new Exception("Student does not exist.");

        String query = "UPDATE student_profile SET full_name = ?, email = ?, phone = ?, role_id = ?, profile_picture = ?, status = ?, updated_at = NOW(), updated_by = ? "
                + "WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setInt(4, student.getRoleId());
            stmt.setString(5, student.getProfilePicture());
            stmt.setString(6, student.getStatus());
            stmt.setString(7, "admin");
            stmt.setString(8, student.getUsername());

            stmt.executeUpdate();
        }
    }

    public void deleteStudent(String username) throws Exception {
        if (!isUsernameTaken(username)) throw new Exception("Student does not exist.");

        String query = "DELETE FROM student_profile WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    private boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM student_profile WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    private boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM student_profile WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    private boolean isPhoneTaken(String phone) throws SQLException {
        String query = "SELECT COUNT(*) FROM student_profile WHERE phone = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
}