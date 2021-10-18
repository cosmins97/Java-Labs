package com.example.lab2;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "resultServlet")
public class ResultServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        try {
            InputStream fis = new FileInputStream("E:\\facultate\\M2\\Java\\Java-Labs\\Lab2\\files\\records.json");
            //create JsonReader object
            JsonReader jsonReader = Json.createReader(fis);
            JsonObject jsonObject = jsonReader.readObject();
            JsonObject tableLine;

            //close IO resource and JsonReader
            jsonReader.close();
            fis.close();

            String htmlTable = "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Category</th>\n" +
                    "    <th>Key</th>\n" +
                    "    <th>Value</th>\n" +
                    "  </tr>";

            for (String k: jsonObject.keySet()
            ) {
                tableLine = jsonObject.getJsonObject(k);
                htmlTable += ("<tr><td>" + tableLine.getString("category") + "</td>" +
                        "<td>" + tableLine.getString("key") + "</td>" +
                        "<td>" + tableLine.getString("value") + "</td></tr>");
            }

            htmlTable += "</table>";

            request.setAttribute("resultTable", htmlTable);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }
}
