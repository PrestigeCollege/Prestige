import java.io.*;

public class testRun
{
	public static void main(String [] args)
	{
		String file = "testdata.dat";
		
		PropertyList list = new PropertyList();
//		list.readFromFile(file);
//		list.listAllRecords();

//		ItemGui window = new ItemGui();
		// //CR_SDMA2//Files//pics//filename
		//Item (String accesNum, String maker, String description, String Filename
		Item one = new Item("1234", "Picablo", "Trains", "refrigerator.jpg" );
		Item two = new Item("1235", "Dantel", "My Pride", "mercedes.jpg");
		Item three = new Item("1236", "Ralphael", "Boats and Hos","nikon.jpg" );
		Item four = new Item("1237", "Pickasshole", "Rim and Tip", "splash.jpg" );
		Item five = new Item("1238", "Monetpolicy", "99 Problems", "television.jpg" );
		Item six = new Item ("1239", "Michaelangelhair", "Buy This", "none.jpg" );
		
		list.addToDataBase(one);
		list.addToDataBase(two);
		list.addToDataBase(three);
		list.addToDataBase(four);
		list.addToDataBase(five);
		list.addToDataBase(six);
		
		int size = list.getDataBaseSize();
		
		System.out.println("Size of array: " + size  );
		
		for (int i = 0; i < size; i++)
		{
			Item myItem = list.getItem(i);
			System.out.println(myItem.getTitle() + "\t" + myItem.getPic());
		}
	
		list.writeToFile(file);
	}//end main	
	
}//end class	
/*		
		
		Item one = new Item("Automobile", "SL-350", "Mercedes", "1235",
		1, 2008, 40000, "mercedes.jpg");
		Item two = new Item("Camera", "ABC-200", "Nikon", "1236", 1, 2010, 300, "nikon.jpg");
		Item three = new Item("Refrigerator", "Cool-One", "LG", "AB1234", 1, 2009, 1600,
		"refrigerator.jpg");
		Item four = new Item("Television", "Viao", "Sony", "GF89X", 1, 2010, 700,
		"television.jpg"); 
*/	
;
		
/*		list.clearDataBase();
		list.listAllRecords();
		
		
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

