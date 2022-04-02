package com.revature.proj1.control;

import java.io.IOException;
import java.io.PrintWriter;
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
        //read the form input
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        //processing into response
        PrintWriter writer = response.getWriter();

        //build the html code for output back to frontend
        String htmlResponse = "<html>";
        htmlResponse += "<h2>Your username is: " + username + "<br>";
        htmlResponse += "Your password is: " + password + "</h2>";
        htmlResponse += "<html>";

        //return response to frontend
        writer.println(htmlResponse);
    }
}
