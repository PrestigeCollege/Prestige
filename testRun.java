import java.util.Date;
import java.sql.*;
public class testRun
{
	public static void main(String [] args)
	{
		boolean test= true;
		damage_model testDamage = new damage_model();
		testDamage.add_damage(1, 1, "testDamage", "fakeURI", test);
/*
		String file = "testdata2.dat";
		
		PropertyList list = new PropertyList();
*/
//		list.listAllRecords();

//		ItemGui window = new ItemGui();
/*		Item one = new Item("1234", "Leonardo da Vinci", "Mona Lisa", "monalisa.jpg" );
		Item two = new Item("1235", "Vincent van Gogh", "Starry Night", "starry.jpg" );
		Item three = new Item("1236", "Albert Bierstadt", "Sunset Over the River", "sunset.jpg" );
		Item four = new Item("1237", "Josephine Wall", "Bygone Summers", "surreal.jpg" );
		Item five = new Item("1238", "Claude Monet", "Water Lillies", "lillies.png" );

		one.addConditionReport(new ConditionReport("Alabama", "Red", "Taco", new Date()) );
*/
//		ConditionReport report1 = new ConditionReport("Alabama", "Red", "Taco", new Date());
//		ConditionReport report2 = new ConditionReport("Banana", "Parrot", "Taco", new Date());
//		ConditionReport report3 = new ConditionReport("Spider", "Hair", "Dog", new Date());

		//Item (String accesNum, String maker, String description, String Filename
/*		Item one = new Item("1234", "Leonardo da Vinci", "Mona Lisa", "monalisa.jpg" );
		Item two = new Item("1235", "Vincent van Gogh", "Starry Night", "starry.jpg" );
		Item three = new Item("1236", "Albert Bierstadt", "Sunset Over the River", "sunset.jpg" );
		Item four = new Item("1237", "Josephine Wall", "Bygone Summers", "surreal.jpg" );
		Item five = new Item("1238", "Claude Monet", "Water Lillies", "lillies.png" );
*/
/*		Item one = new Item("Automobile", "SL-350", "Mercedes", "1235",
		1, 2008, 40000, "mercedes.jpg");
		Item two = new Item("Camera", "ABC-200", "Nikon", "1236", 1, 2010, 300, "nikon.jpg");
		Item three = new Item("Refrigerator", "Cool-One", "LG", "AB1234", 1, 2009, 1600,
		"refrigerator.jpg");
		Item four = new Item("Television", "Viao", "Sony", "GF89X", 1, 2010, 700,
		"television.jpg"); 
*/
/*		
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
*/		
//		list.openFile(file);
//		list.addRecords();
//		list.closeFile();


		


		
//		list.printTextFile("List.txt");
//		list.writeToFile(file);
		
	//	list.removeItem("Camera");
		
	//	System.out.println(list.getDataBaseSize());
	//	System.out.println(list.getTotalValue());
	//	list.searchByName("Camera");
		
	//	list.listAllRecords();*/
	}
}
