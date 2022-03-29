package com.revature.proj1.users;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.revature.proj1.factory.ScannerFactory;
import com.revature.proj1.repos.UserRepository;
import com.revature.proj1.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class UserService {
    private static User currentUser;
    private final Logger logger;
    private final Scanner scanner;
    private final UserRepository userRepository;
    private final BCrypt.Hasher hasher;

    public UserService() {
        this.logger = LogManager.getLogger(UserService.class);
        this.scanner = ScannerFactory.getScanner();
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

    public void register(){
        System.out.println("\n-- User Registration --");
        User user = buildUser(); // get the username and password

        userRepository.create(user);
    }

    public User login(){
        System.out.println("\n-- User Login --");
        return validate();
    }

    public static User getCurrentUser(){
        return currentUser;
    }

    private User validate(){

        // user enters username, user enters password, build user object

        User user = new User(getUsername(), getPassword());
        if(isValid(user)){
            return user;
        } else{
            return null;
        }

//        return isValid(user) ? user : null;
    }

    private boolean isValid(User user){
        // user object passed in contains whatever the user entered when logging in
        // user entered information that can compare against the db info

        User dbUser = userRepository.getByUsername(user.getUsername());
        // dbUser contains the user that's on the database

        if(dbUser != null){ // if it is null, the username doesn't exist
            if(dbUser.getPassword().equals(user.getPassword())) {
                currentUser = dbUser;
                return true;
            }
        }

        System.out.println("Invalid credentials.");
        return false;
    }

    private User buildUser(){
        User user = new User(getUsername(), getPassword());
        return !exists(user) ? user : buildUser();
    }

    private String getUsername(){
        String username = "";
        boolean valid = false;

        while(!valid){
            System.out.print("Username: ");
            username = scanner.nextLine();

            //TODO: Username validation
            if(username.length() > 4){
                valid = true;
            } else {
                System.out.println("Username must be longer than 4 characters.");
            }
        }
        return username;
    }

    private String getPassword(){
        String password = "";
        boolean valid = false;

        while(!valid){
            System.out.print("Password: ");
            password = scanner.nextLine();

            //TODO: Password validation
            if(password.length() > 4){
                valid = true;
            } else{
                System.out.println("Password must be longer than 4 characters.");
            }
        }
        return encryptPassword(password);
    }

    private boolean exists(User user){
        // need to check if the username exists
        if (userRepository.getByUsername(user.getUsername()) == null){
            return false;
        } else{
            System.out.println("Username already exists.");
            return true;
        }

        // if method returns null -> the user doesnt exist !(true)

//        return !(userRepository.getUserByUsername(user.getUsername()) == null);

        // if it does, let them know and return true

        // if it doesn't return false
    }
    private String encryptPassword(String password){
        return hasher.hashToString(4, password.toCharArray());
    }



}
