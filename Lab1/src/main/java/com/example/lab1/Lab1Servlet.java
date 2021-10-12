package com.example.lab1;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "lab1Servlet", value = "/lab1Servlet")
public class Lab1Servlet extends HttpServlet {

    public String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public String manageRepositoryFile(String filePath, InputData inputData){
        String htmlResponse = "";
        String[] fileLines;

        try {
            //write to file
            FileWriter fileWriter = new FileWriter(filePath, true);

            if(inputData.getSync().equals("True")){
                synchronized (this){
                    for(int i = 0; i < inputData.getValue(); i++){
                        fileWriter.write(inputData.getKey() + " ");
                    }

                    fileWriter.write("- [" + Timestamp.from(Instant.now()) + "]");

                    fileWriter.write("\n");
                }
            }
            else{
                for(int i = 0; i < inputData.getValue(); i++){
                    fileWriter.write(inputData.getKey() + " ");
                }

                fileWriter.write("- [" + Timestamp.from(Instant.now()) + "]");

                fileWriter.write("\n");
            }

            fileWriter.close();

            //read from file
            fileLines = readLines(filePath);

            //sort by key
            Arrays.sort(fileLines);

            //create html response
            for(int i = 0; i < fileLines.length; i++){
                htmlResponse += (fileLines[i] + "<br>");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return htmlResponse;
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // get data
        String key = request.getParameter("key");
        int value = Integer.parseInt(request.getParameter("value"));

        String mock = request.getParameter("mock");
        String sync = request.getParameter("sync");

        InputData inputData = new InputData(key, value, mock, sync);

        // get response writer
        PrintWriter writer = response.getWriter();
        String htmlRespone = "<html><h2>";

        // process data
        // if mock is true -> just confirmation message
        if(mock.equals("True")){
            htmlRespone += "Key is: " + inputData.getKey() + "<br/>";
            htmlRespone += "Value is: " + inputData.getValue() + "<br/>";
            htmlRespone += "Mock is set to: " + inputData.getMock() + "<br/>";
            htmlRespone += "Sync is set to: " + inputData.getSync() + "<br/>";
        }
        else{
            String filePath = "E:\\facultate\\M2\\Java\\Java-Labs\\Lab1\\repository.txt";

            htmlRespone += manageRepositoryFile(filePath, inputData);
        }

        htmlRespone += "</h2></html>";

        // return response
        writer.println(htmlRespone);

        //write in log
        System.out.println("Method: " + request.getMethod());
        System.out.println("Address: " + request.getRemoteAddr());
        System.out.println("Language: " + request.getLocale().toString());
        System.out.println("Parameters:");

        Enumeration<String> parameters = request.getParameterNames();

        while (parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            String parameterValue = request.getParameter(parameterName);
            System.out.println(parameterName + " : " + parameterValue);
        }
    }
}
