import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;
public class art_model extends db_master{
	public art_model(){
		super();	
	}

	public art_data_entry getArtByID(int input_id){
		art_data_entry result;
		try{
			connection = connect();
			Statement stmt = connection.createStatement();
			ResultSet query = stmt.executeQuery("SELECT * FROM art WHERE id=input_id");
			result = new art_data_entry(
					query.getInt("id"),
					query.getString("name"),
					query.getString("artist"),
					query.getString("imagePath")
			);
			connection.close();
            return result;
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
        return new art_data_entry(0, "fail", "fail", "fail");
	}

//NEEDS: GetArtByARTIST
	public art_data_entry[] getByArtist(String queryName){
		try{
			connection = connect();
			Statement stmt = connection.createStatement();
			ResultSet query = stmt.executeQuery("SELECT * FROM art WHERE artist=queryName");
			ArrayList<art_data_entry> list= new ArrayList<art_data_entry>();
			while (query.next()) {
			    list.add(new art_data_entry(
			    		query.getInt("id"),
			    		query.getString("name"), 
			    		query.getString("artist"), 
			    		query.getString("image_path")) 
			    );   
			} 
			art_data_entry[] result = new art_data_entry[list.size()];
			result = list.toArray(result);
			connection.close();
            return result;
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
        return new art_data_entry[0];
	}

	public void add_art (String newName, String newArtist, String newImagePath){
		try{
			connection = connect();
			String query= "INSERT INTO art (name, artist, image_path) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, newArtist);
			preparedStatement.setString(3, newImagePath);
			preparedStatement.executeUpdate();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
	}
}