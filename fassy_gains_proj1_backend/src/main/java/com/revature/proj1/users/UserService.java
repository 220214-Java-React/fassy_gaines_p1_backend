package com.revature.proj1.users;

import com.revature.proj1.factory.ConnectionFactory;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.revature.proj1.factory.ConnectionFactory;
import com.revature.proj1.users.User;
import com.revature.proj1.repos.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class UserService {
    private static User currentUser;
    private final Logger logger;
    private final UserRepository userRepository;
    private final BCrypt.Hasher hasher;
    private final String SALT = "xEZew5rGNU?tyoF.";

    public UserService() {
        this.logger = LogManager.getLogger(UserService.class);
        this.userRepository = new UserRepository();
        this.hasher = BCrypt.withDefaults();
        currentUser = null;
    }

    public void create(HashMap<String, String> myUser) {
        User user = new User();

        user.setUsername(myUser.get("username"));
        user.setPassword(myUser.get("password"));
        user.setEmail(myUser.get("email"));
        user.setFname(myUser.get("fname"));
        user.setLname(myUser.get("lname"));
        user.setRole_ID(myUser.get("role"));

        System.out.println(user.toString());

        String encryptedPass = encryptPassword(user.getPassword());
        user.setPassword(encryptedPass);
        user.setIs_active(true);
        //creating random user id
        user.setId(userID());
        userRepository.create(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

   /* public User validate(User user){
        // user object passed in contains whatever the user entered when logging in
        // user entered information that can compare against the db info

        User dbUser = userRepository.getByUsername(user.getUsername());
        // dbUser contains the user that's on the database

        if(dbUser != null){ // if it is null, the username doesn't exist
            if(dbUser.getPassword().equals(encryptPassword(user.getPassword()))) {
                currentUser = dbUser;
                return dbUser;
            }
        }
        return null;
    }*/

    private String encryptPassword(String password){
        return new String(
                hasher.hash(4, SALT.getBytes(StandardCharsets.UTF_8),
                        password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }

    public int userID()
    {
        int id = 0;
        boolean isUnique = false;
        Random rand = new Random();

        try
        {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM ers_users");
            ResultSet rs = pst.executeQuery();
            while(!isUnique)
            {
                id = rand.nextInt(1000000000);
                while (rs.next())
                {
                    if (rs.getInt(1) == id)
                    {
                    break;
                    }
                }
                isUnique = true;
            }
            return id;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

        return 0;
    }
}
