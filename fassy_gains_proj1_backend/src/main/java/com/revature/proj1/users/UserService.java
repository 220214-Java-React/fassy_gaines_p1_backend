package com.revature.proj1.users;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.revature.proj1.users.User;
import com.revature.proj1.repos.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.List;


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

    public void create(User user) {
        String encryptedPass = encryptPassword(user.getPassword());
        user.setPassword(encryptedPass);

        userRepository.create(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User validate(User user){
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
    }

    private String encryptPassword(String password){
        return new String(
                hasher.hash(4, SALT.getBytes(StandardCharsets.UTF_8),
                        password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }
}
