import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.*;

public class damage_model extends db_master{
	public damage_model(){
		super();	
	}

	public damage_data_entry getDamageByID(int input_id){
		damage_data_entry result;
		try{
			connection = connect();
			Statement stmt = connection.createStatement();
			ResultSet query = stmt.executeQuery("SELECT * FROM damage WHERE id=input_id");
			
			//converts SQL date to Java Date.
			java.sql.Date sqlDate = query.getDate("edit_date");
			java.util.Date javaDate = new java.util.Date(sqlDate.getTime());

			result = new damage_data_entry(
					query.getInt("id"),
					query.getInt("painting_id"),
					query.getInt("user_id"),
					query.getString("damage_type"),
					query.getString("layer_path"),
					javaDate,
					query.getInt("archived")
			);
			connection.close();
            return result;
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }

        return new damage_data_entry(0, 0, 0, "fail", "fail", new java.util.Date(), 0);
	}

	public damage_data_entry[] getByPaintingId(int input_id){
		try{
			connection = connect();
			Statement stmt = connection.createStatement();
			ResultSet query = stmt.executeQuery("SELECT * FROM damage WHERE painting_id=input_id");
			ArrayList<damage_data_entry> list= new ArrayList<damage_data_entry>();
			while (query.next()) {
				//converts SQL date to Java Date.
				java.sql.Date sqlDate = query.getDate("edit_date");
				java.util.Date javaDate = new java.util.Date(sqlDate.getTime());

			    list.add(new damage_data_entry(
					query.getInt("id"),
					query.getInt("painting_id"),
					query.getInt("user_id"),
					query.getString("damage_type"),
					query.getString("layer_path"),
					javaDate,
					query.getInt("archived")
				));   
			}

			damage_data_entry[] result = new damage_data_entry[list.size()];
			result = list.toArray(result);
			connection.close();
            return result;
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
        return new damage_data_entry[0];
	}

	public void add_damage (int newUserId, int newPaintingId, String newDamageType, String newLayerPath, boolean archiveStatus){
		java.util.Date javaDate;
		try{
			connection = connect();
			String query= "INSERT INTO damage (user_id, painting_id, damage_type, layer_path, edit_date, archived) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, newUserId);
			preparedStatement.setInt(2, newPaintingId);
			preparedStatement.setString(3, newDamageType);
			preparedStatement.setString(4, newLayerPath);
			javaDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
			preparedStatement.setDate(5, sqlDate);
			preparedStatement.setBoolean(6, archiveStatus);
			preparedStatement.executeUpdate();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }
	}
}
