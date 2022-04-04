package com.revature.proj1.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.proj1.users.User;
import com.revature.proj1.users.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/UserServlet")
public class UserController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // get all users
        List<User> users = userService.getAll();
        String JSON = "";
        // still in java form, what do I need to do? Convert to JSON String (aka Marshalling)
        try{
            JSON = mapper.writeValueAsString(users);

            // after its in string form, I need to do a few things to get it back to the user, what are they?
            response.setContentType("application/json"); // inform the caller that we are sending JSON back
            response.setStatus(200); // inform the caller that the operation was a success
            response.getOutputStream().println(JSON); // write the data to the body of the response
        } catch(Exception e){
            logger.warn(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;
        logger.info(JSON);

        // We unmarshall the JSON string into a Java instance of the User class
        try{
             user = mapper.readValue(JSON, User.class);

            // we have a new user object -> what do we do with it?
            // try and persist it to the database, however we should not go to our Repository directly.
            // We should instead pass this variable to the UserService so that it can handle sending to the DAO.
             userService.create(user);
             logger.debug(user.toString());
        } catch(Exception e){
            logger.warn(e);
        }


        // 200 - OK, 201 - Created is good if you're returning, 204 - No Content
        resp.setStatus(204);
    }
}
