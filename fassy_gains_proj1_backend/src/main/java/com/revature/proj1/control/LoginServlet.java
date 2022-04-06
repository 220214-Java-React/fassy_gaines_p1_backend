package com.revature.proj1.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.revature.proj1.factory.ConnectionFactory;
import com.revature.proj1.users.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    BCrypt.Hasher hasher = BCrypt.withDefaults();
    String SALT = "xEZew5rGNU?tyoF.";
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        boolean match = false;
        String JSON = request.getReader().lines().collect(Collectors.joining());
        //User user = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> temp = mapper.readValue(JSON, Map.class);
        HashMap<String, String> myMap = new HashMap<>(temp);
        //read the form input
        String password = myMap.get("password");
        String username = myMap.get("username");

        String encryptedPass = encryptPassword(password);
        User user = checkUsernamePassword(username, encryptedPass);

        if(user != null)
        {
            user.setIs_active(true);
            user.setPassword(password);
            response.setStatus(202);
        }
        else
        {
            response.setStatus(200);
        }
    }

    private String encryptPassword(String password)
    {
        return new String(
                hasher.hash(4, SALT.getBytes(StandardCharsets.UTF_8),
                        password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);

    }

    public User checkUsernamePassword(String username, String password)
    {

        try (Connection connection = ConnectionFactory.getConnection())
        {
            String sql = "select * from ers_users";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next())
            {
                if(username.equals(resultSet.getString(2)))
                {
                    if(password.equals(resultSet.getString(4)))
                    {
                        User user = new User(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(4), resultSet.getString(3),
                                resultSet.getString(5), resultSet.getString(6), resultSet.getString(8));
                        return user;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
