/*	CS 441 Final Project
	File Name:			Item.java
	Programmer:			Alexander Fairhurst
	Date Last Modified: 11/3/2015	 	
*/
import java.io.Serializable;
import java.util.Stack;


public class Item implements Serializable
{
	public String accessionNumber, artist, title, pictureFileName;
	//public Stack<ConditionReport> myReports;
//	private Stack<ConditionReport> CR = new Stack<ConditionReport>();

	//The line below this was public
//	private String accessionNumber, artist, title, pictureFileName;
	
	public Item()
	{
		accessionNumber = null;
		artist = null;
		title = null;
		pictureFileName = null;
		//myreports
	}

	public Item(String accessNum, String maker, String description, String pictureFile)
	{
		accessionNumber = accessNum;
		artist = maker;
		title = description;
		pictureFileName = pictureFile;
	}
	public Item(Item arg)
	{
		accessionNumber = arg.getAccessionNumber();
		artist = arg.getArtist();
		title = arg.getTitle();
		pictureFileName = arg.getPic();
	}
		
	public void setAccessionNumber(String accessNum)
	{
		accessionNumber = accessNum;
	}
	
	public String getAccessionNumber()
	{
		return accessionNumber;
	}
	
	public void setArtist(String maker)
	{
		artist = maker;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	//add method to pop an element
	//ad method to verify if stack is empty, return boolean
	
	public void setTitle(String description)
	{
		title = description;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setPic(String pictureFile)
	{
		pictureFileName = pictureFile;
	}
	
	public String getPic()
	{
		return pictureFileName;
	}

	public String toString()
	{
		return ("Accession Number: \t"+accessionNumber+"\nArtist: \t"+artist+"\nTitle: \t"+title+"\nPicture File: \t"+pictureFileName+"\n");
	}	
}
