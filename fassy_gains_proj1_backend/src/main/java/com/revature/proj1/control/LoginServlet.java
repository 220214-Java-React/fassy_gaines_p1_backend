package com.revature.proj1.control;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        String JSON = request.getReader().lines().collect(Collectors.joining());
        //User user = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> temp = mapper.readValue(JSON, Map.class);
        HashMap<String, String> myMap = new HashMap<>(temp);
        //read the form input
        String username = myMap.get("username");
        String password = myMap.get("password");

        System.out.println(username + "/" + password);

    }
}
