package com.revature.proj1.control;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/ReimbursementServlet")
public class ReimbursementServlet extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {

        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> temp = mapper.readValue(JSON, Map.class);
        HashMap<String, String> myMap = new HashMap<>(temp);
        String monies = myMap.get("money");
        float f = Float.parseFloat(monies);
        // We unmarshall the JSON string into a Java instance of the User class
    }
}
