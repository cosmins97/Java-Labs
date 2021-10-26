package com.example.lab3;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@WebServlet(name = "AddStudentServlet")
public class AddStudentServlet extends HttpServlet {

    private Connection dbcon;

    private void insertStudentIntoDB(String studentName, String[] examIds) throws SQLException {
        Statement statement = dbcon.createStatement();

        String query = "INSERT INTO students(student_name) VALUES ('";
        query +=       studentName + "');";

        int rs = statement.executeUpdate(query);

        query = "select student_id from students where student_name = '" + studentName + "';";

        ResultSet rs1 = statement.executeQuery( query );
        rs1.next();

        query = "";

        if (examIds != null && examIds.length != 0) {
            for (String selectedExam : examIds) {
                query += "INSERT INTO students_exams(student_id, exam_id) VALUES ('";
                query +=       rs1.getInt("student_id") + "', '";
                query +=       selectedExam + "');";
            }
            int rs2 = statement.executeUpdate(query);
        }
    }

    // "init" sets up a database connection
    public void init(ServletConfig config) throws ServletException
    {
        String loginUser = "postgres";
        String loginPasswd = "postgressqljava";
        String loginUrl = "jdbc:postgresql://localhost:5432/postgres";

        // Load the PostgreSQL driver
        try
        {
            Class.forName("org.postgresql.Driver");
            dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        }
        catch (ClassNotFoundException ex)
        {
            System.err.println("ClassNotFoundException: " + ex.getMessage());
            throw new ServletException("Class not found Error");
        }
        catch (SQLException ex)
        {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String studentName = request.getParameter("studentName");

        String[] selectedExams = request.getParameterValues("exams");

        try {
            insertStudentIntoDB(studentName, selectedExams);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        Statement stmt = null;
        List<Exam> allExams = new ArrayList<Exam>();
        try {
            stmt = dbcon.createStatement();

            ResultSet rs = stmt.executeQuery( "select * from exams ;" );

            while ( rs.next() ) {

                String  examName = rs.getString("exam_name");

                String examDateString = rs.getString("exam_date");

                int examDuration  = rs.getInt("exam_duration");

                int examId = rs.getInt("exam_id");

                Date examDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(examDateString);

                allExams.add(new Exam(examId, examName, examDate, examDuration));

            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        request.setAttribute("exams", allExams);

        RequestDispatcher dispatcher = request.getRequestDispatcher("addstudent.jsp");
        dispatcher.forward(request, response);
    }
}
