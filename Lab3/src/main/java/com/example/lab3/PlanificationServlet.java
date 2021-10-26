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

@WebServlet(name = "PlanificationServlet")
public class PlanificationServlet extends HttpServlet {
    private Connection dbcon;

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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String resultTable = "<table>\n" +
                "  <tr>\n" +
                "    <th>Exam</th>\n" +
                "    <th>Date</th>\n" +
                "    <th>Duration (h)</th>\n" +
                "    <th>Attendees</th>\n" +
                "  </tr>";

        Statement stmt = null;

        try {
            stmt = dbcon.createStatement();

            ResultSet rs = stmt.executeQuery( "select * from exams ;" );

            ResultSet rs1;

            while ( rs.next() ) {

                String examName = rs.getString("exam_name");

                String examDateString = rs.getString("exam_date");

                int examDuration  = rs.getInt("exam_duration");

                int examId = rs.getInt("exam_id");

                resultTable += "<tr>";
                resultTable += ("<td>" + examName + "</td>");
                resultTable += ("<td>" + examDateString + "</td>");
                resultTable += ("<td>" + examDuration + "</td>");
                resultTable += "<td>";

                Statement stmt2 = dbcon.createStatement();

                rs1 = stmt2.executeQuery( "select * from students_exams where exam_id = " + examId + ";" );

                while(rs1.next()){
                    int studentId = rs1.getInt("student_id");

                    Statement stmt3 = dbcon.createStatement();

                    ResultSet rs2 = stmt3.executeQuery( "select * from students where student_id = " + studentId + ";" );
                    rs2.next();

                    resultTable += (rs2.getString("student_name") + "<br>");
                }

                resultTable += "</td></tr>";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultTable += "</table>";

        request.setAttribute("resultTable", resultTable);

        RequestDispatcher dispatcher = request.getRequestDispatcher("planification.jsp");
        dispatcher.forward(request, response);
    }
}
