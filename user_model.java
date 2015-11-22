import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;

public class user_model extends db_master{
	public user_model(){
		super();	
	}
	public user_data_entry getUserByID(int input_id){
		user_data_entry result;
		try{
			connection = connect();
			Statement stmt = connection.createStatement();
			ResultSet query = stmt.executeQuery("SELECT * FROM users WHERE id=" + input_id);
			query.next();
			result = new user_data_entry(query.getInt("id"), query.getString("name"), query.getString("pass"), query.getInt("status"));
			connection.close();
            return result;
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
        return new user_data_entry(0, "fail", "fail", 1);
	}	
	public void add_user (String newUser, String newPass, int newStatus){
		try{
			connection = connect();
			String query= "INSERT INTO users (name, pass, status) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newUser);
			preparedStatement.setString(2, newPass);
			preparedStatement.setInt(3, newStatus);
			preparedStatement.executeUpdate();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
	}
}