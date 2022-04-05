package com.revature.proj1.repos;

import com.revature.proj1.factory.ConnectionFactory;
import com.revature.proj1.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
    UserRepository is the starting point of our persistence layer.

    CRUD Operations are those that allow the modification or persistence of data in some

                                DML -> Data Manipulation Language
    C - Create                  insert
    R - Read                    select
    U - Up.date                  update
    D - De.lete                  delete
 */
public class UserRepository implements DAO<User>{
    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    @Override
    public void create(User user){
        // here we write our SQL to create a user
        Connection connection = null;


        try{
            connection = ConnectionFactory.getConnection();
            String roleSql = "insert into ers_user_roles(role_id, role_) values (?, ?)";
            String sql = "insert into ers_users(user_id, username, password_, email, given_name, surname, is_active, role_id) values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement roleStmt = connection.prepareStatement(roleSql);
            if(user.getRole_ID() == "e")
            {
                roleStmt.setString(1, user.getRole_ID());
                roleStmt.setString(2, "employee");
                roleStmt.executeUpdate();
            }
            else if(user.getRole_ID() == "f")
            {
                roleStmt.setString(1, user.getRole_ID());
                roleStmt.setString(2, "financial manager");
                roleStmt.executeUpdate();
            }
            else
            {
                roleStmt.setString(1, user.getRole_ID());
                roleStmt.setString(2, "administrator");
                roleStmt.executeUpdate();
            }

            // once we have that link
            // we create a statement to be executed
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFname());
            stmt.setString(6, user.getLname());
            stmt.setBoolean(7, user.isIs_active());
            stmt.setString(8, user.getRole_ID());
            stmt.executeUpdate();

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }


    // Please enter your username:
    // enter your password:

    // iterate through our list of users and find where the username matched and check the password

    // in our service, when a user enters their username and password,
    //we can run getByUsername() to get the user from the database then check their password
    public User getByUsername(String username){
        User user = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from ers_users where username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);

            // this
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_"),
                        resultSet.getString("email"),
                        resultSet.getString("given_name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role_id")
                );
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return user;
    }

    // select * from users where id = ?
    @Override
    public User getById(int id){

        User user = null;
        String sql = "select * from ers_users where user_id = ?";
        Connection connection;

        try{
            connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_"),
                        rs.getString("email"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getString("role_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // select * from users
    @Override
    public List<User> getAll(){
        List<User> users = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from ers_users";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                users.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_"),
                        resultSet.getString("email"),
                        resultSet.getString("given_name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role_id")));
            }
        } catch (Exception e) {
            logger.warn(e);
        }
        return users;
    }

    @Override
    public void update(User user){

    }

    // delete from users where id = ?
    @Override
    public void deleteById(int id){

    }
}
