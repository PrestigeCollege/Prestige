import java.util.Date;
import java.sql.*;
public class testRun
{
	public static void main(String [] args)
	{
		
		
	
/*		
		//request paintings by Artist
		art_model art = new art_model();
		art_data_entry[] query = art.getByArtist("TestArtist");
		String name = query[0].get_name();
		System.out.println(name);
	
		
		//add painting to DB
		art_model art = new art_model();
		art.add_art("testName", "TestArtist", "Fake img Path");
		
		//Pull Data For Art Damage
		damage_model damages = new damage_model();
		damage_data_entry query = damages.getDamageByID(1);
		String name = query.get_damage_type();
		System.out.println(name);
		//Pull data for Users
		user_model testUser = new user_model();
		user_data_entry query= testUser.getUserByID(1);
		String name = query.get_name();
		System.out.println(name);
		//Add users to DB
		user_model testUser = new user_model();
		testUser.add_user("testAdmin", "fakePass", 1);
		testUser.add_user("testUser", "fakePass", 2);
		testUser.add_user("testGuest", "fakePass", 3);
		*/

		String file = "testdata2.dat";
		PropertyList list = new PropertyList();

		Item one = new Item("1234", "Leonardo da Vinci", "Mona Lisa", "monalisa.jpg" );
		Item two = new Item("1235", "Vincent van Gogh", "Starry Night", "starry.jpg" );
		Item three = new Item("1236", "Albert Bierstadt", "Sunset Over the River", "sunset.jpg" );
		Item four = new Item("1237", "Josephine Wall", "Bygone Summers", "surreal.jpg" );
		Item five = new Item("1238", "Claude Monet", "Water Lillies", "lillies.png" );

		one.addConditionReport(new ConditionReport("Blister", "Curator1", "3 in. sq. Upper Right", new Date()) );
		one.addConditionReport(new ConditionReport("Fading", "Curator2", "Very faint, but noticeable", new Date()) );
		one.addConditionReport(new ConditionReport("Peeling", "Curator1", "Damage area = 6\" square", new Date()) );
		one.addConditionReport(new ConditionReport("Repaint", "Curator1", "Colors don't match.", new Date()) );
		
		list.addToDataBase(one);
		list.addToDataBase(two);
		list.addToDataBase(three);
		list.addToDataBase(four);
		list.addToDataBase(five);
		
		
		list.writeToFile(file);
		
		System.out.println("Clearing ArrayList");
		list.clearDataBase();
		System.out.println("ArrayList is empty? " + list.listIsEmpty());
		
		System.out.println("Reading in file: " + file);
		list.readFromFile(file);
	
		System.out.println("ArrayList is empty? " + list.listIsEmpty());
		list.listAllRecords();
/*		
		list.openFile(file);
		list.addRecords();
		list.closeFile();
*/
//		list.printTextFile("List.txt");
//		list.writeToFile(file);
	}
}
