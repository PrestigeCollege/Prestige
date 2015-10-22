/*	CS 441 Final Project
	File Name:			Item.java
	Programmer:			Alexander Fairhurst
	Date Last Modified: 10/20/2015	 	
*/

public class Item 
{
	private String accessionNumber, artist, title, pictureFileName;
	
	public Item()
	{
		accessionNumber = null;
		artist = null;
		title = null;
		pictureFileName = null;
	}

	public Item(String accessNum, String maker, String description, String pictureFile)
	{
		accessionNumber = accessNum;
		artist = maker;
		title = description;
		pictureFileName = pictureFile;
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
		return ("Accession Number: \t"+accessionNumber+"\nArtist: \t"+artist+"\nTitle: \t"+title+"Picture File"+pictureFileName+"\n");
	}
}
