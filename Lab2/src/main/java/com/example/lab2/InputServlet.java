package com.example.lab2;

import javax.json.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "inputServlet")
public class InputServlet extends HttpServlet {
    CaptchaManager captcha = new CaptchaManager();

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
                String categoryString = value.toString();
                categories.add(categoryString.substring(1, categoryString.length() - 1));
            }

            //verify cookie
            HttpSession session = request.getSession();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie: cookies) {
                    String name = cookie.getName();
                    if (name.equals("selectedCategory")) {
                        session.setAttribute("selectedCategory", cookie.getValue());
                        System.out.println(cookie.getValue());
                    }
                }
            }

            //captcha
            captcha.generateNumbers();

            //set attributes
            request.setAttribute("categories", categories);
            request.setAttribute("captchaFirstNumber", captcha.getFirstNumber());
            request.setAttribute("captchaSecondNumber", captcha.getSecondNumber());

            System.out.println(categories.get(0));

            RequestDispatcher dispatcher = request.getRequestDispatcher("input.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        int captchaAnswer = Integer.parseInt(request.getParameter("captchaAnswer"));

        //verify captcha
        if(captchaAnswer != captcha.getSum()){
            PrintWriter writer = response.getWriter();
            String htmlRespone = "<html><h2>Wrong captcha answer. </h2><a href=\"input\"><h2>Back to form</h2></a></html>";
            writer.println(htmlRespone);
        }
        else{
            //verify if category is set
            if(category == null){
                ServletContext context = this.getServletContext();
                category = context.getInitParameter("defaultCategory");
            }
            else{
                Cookie categoryCookie = new Cookie("selectedCategory", category);
                response.addCookie(categoryCookie);
            }

            //create record object
            Record newRecord = new Record(category, key, value);

            //update json file
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

                OutputStream os = new FileOutputStream("E:\\facultate\\M2\\Java\\Java-Labs\\Lab2\\files\\records.json");
                JsonWriter jsonWriter = Json.createWriter(os);

                jsonWriter.writeObject(recJsonObject);
                jsonWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("result");
        }
    }
}
