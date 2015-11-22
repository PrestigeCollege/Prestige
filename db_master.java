import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class db_master {
	public Connection connection = null;
	private String StoredHost ="jdbc:mysql://sql3.freemysqlhosting.net:3306/sql397372?&relaxAutoCommit=true";
	private String StoreduName = "sql397372";
	private String StoreduPass = "nI3!rL7!";	
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