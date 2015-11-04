import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class db_master {
	public Connection connection = null;
	private String StoredHost ="a";
	private String StoreduName = "c";
	private String StoreduPass = "b";	
	public db_master(){	}
	public Connection connect() {
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(StoredHost, StoreduName, StoreduPass);
		}

		catch (Exception e) {
			System.err.println("Got an exception!");
		    System.err.println(e.getMessage());
        }

        finally {
            return conn;
        }
	}
}