package com.example.lab2;

import javax.json.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "inputServlet")
public class InputServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        List<String> categories = new ArrayList<String>();
        try {
            InputStream fis = new FileInputStream("E:\\facultate\\M2\\Java\\Java-Labs\\Lab2\\files\\categories.json");
            //create JsonReader object
            JsonReader jsonReader = Json.createReader(fis);
            JsonObject jsonObject = jsonReader.readObject();

            //we can close IO resource and JsonReader now
            jsonReader.close();
            fis.close();

            JsonArray jsonArray = jsonObject.getJsonArray("categories");

            for(JsonValue value : jsonArray){
                categories.add(value.toString());
            }

            request.setAttribute("categories", categories);

            RequestDispatcher dispatcher = request.getRequestDispatcher("input.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/input.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String key = request.getParameter("key");
        String value = request.getParameter("value");

        Record newRecord = new Record(category, key, value);

        List<Record> records = new ArrayList<Record>();

        try {
            InputStream fis = new FileInputStream("E:\\facultate\\M2\\Java\\Java-Labs\\Lab2\\files\\records.json");
            //create JsonReader object
            JsonReader jsonReader = Json.createReader(fis);
            JsonObject jsonObject = jsonReader.readObject();

            //we can close IO resource and JsonReader now
            jsonReader.close();
            fis.close();

            int newIndex = jsonObject.keySet().size();

            JsonObjectBuilder recBuilder = Json.createObjectBuilder();

            for (String k: jsonObject.keySet()
                 ) {
                recBuilder.add(k, jsonObject.get(k));
            }

            JsonObjectBuilder recInfoBuilder = Json.createObjectBuilder();
            recInfoBuilder.add("category", newRecord.getCategory());
            recInfoBuilder.add("key", newRecord.getKey());
            recInfoBuilder.add("value", newRecord.getValue());
            recBuilder.add(String.valueOf(newIndex), recInfoBuilder);

            JsonObject recJsonObject = recBuilder.build();

//            OutputStream os = new FileOutputStream("E:\\facultate\\M2\\Java\\Java-Labs\\Lab2\\files\\records.json");
//            JsonWriter jsonWriter = Json.createWriter(os);
//
//            jsonWriter.writeObject(recJsonObject);
//            jsonWriter.close();

            System.out.println(recJsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("result");
    }
}
