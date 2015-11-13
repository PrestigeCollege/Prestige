/*	CS 441 Final Project
	File Name:			Item.java
	Programmer:			Alexander Fairhurst
	Date Last Modified: 11/12/2015	 	
*/
import java.io.Serializable;
import java.util.Stack;


public class Item implements Serializable
{
	private Stack<ConditionReport> CR = new Stack<ConditionReport>();

	//The line below this was public
	private String accessionNumber, artist, title, pictureFileName;
	
	public Item()
	{
		accessionNumber = null;
		artist = null;
		title = null;
		pictureFileName = null;
		//myreports
		CR = null;
	}

	//Extend this constructor so that it takes a condition report and pushes it to the stack
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
	
	//add method to pop an element
	//add method to verify if stack is empty, return boolean
	
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
