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
import java.util.Date;

@WebServlet(name = "AddExamServlet")
public class AddExamServlet extends HttpServlet {

    private Connection dbcon;

    private void insertExamIntoDB(Exam exam) throws SQLException {
        // Declare our statement
        Statement statement = dbcon.createStatement();

        String query = "INSERT INTO exams(exam_name, exam_date, exam_duration) VALUES ('";
        query +=       exam.getName() + "', '";
        query +=       exam.getFormatedDate() + "', '";
        query +=       exam.getDuration() + "');";

        // Perform the query
        int rs = statement.executeUpdate(query);
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
        String examName = null;
        String examDateString = null;
        int examDuration = 0;
        Date examDate = null;
        try {
            examName = request.getParameter("examname");
            examDateString = request.getParameter("examdate");
            examDuration = Integer.parseInt(request.getParameter("examduration"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            examDate = formatter.parse(examDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Exam newExam = new Exam(examName, examDate, examDuration);

        try {
            insertExamIntoDB(newExam);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("addexam.jsp");
        dispatcher.forward(request, response);
    }
}
