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
	public Stack<ConditionReport> myReports;
	
	public Item()
	{
		accessionNumber = null;
		artist = null;
		title = null;
		pictureFileName = null;
		myReports = null;
	}

// this constructor creates a new item with an empty condition report stack
	public Item(String accessNum, String maker, String description, String pictureFile)
	{
		accessionNumber = accessNum;
		artist = maker;
		title = description;
		pictureFileName = pictureFile;
		myReports = null;
	}
	public Item(String accessNum, String maker, String description, String pictureFile,
				Stack<ConditionReport> reports)
	{
		accessionNumber = accessNum;
		artist = maker;
		title = description;
		pictureFileName = pictureFile;
		myReports = reports;		
	}
					
	public Item(Item arg)
	{
		accessionNumber = arg.getAccessionNumber();
		artist = arg.getArtist();
		title = arg.getTitle();
		pictureFileName = arg.getPic();
		myReports = null;  //TODO Refine later to copy in a stack of CR
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
		return ("Accession Number: \t"+accessionNumber+"\nArtist: \t"+artist+"\nTitle: \t"+
			title+"\nPicture File: \t"+pictureFileName+"\n");
	}
// Methods for interacting with the stack of condition reports

	/* Returns the top element from ConditionReport stack without removing
	 * the element from the stack.  Must verify that stack is not empty 
	 * before calling this method.
	 */
	 //TODO determine if top element will always be the new
	public ConditionReport viewTopCR()
	{
			return myReports.peek();
	}
	/*  This method returns a sting representation of each CR for an Item.
	 *  Images are not included in this method.  Method iterates through
	 *  stack and uses Vector get to retrieve data without altering contents
	 *  stack.  
	 */
	public String conditionReportSummary()
	{
		String junk = null;
		int sizeOf = myReports.size();
		ConditionReport thisReport = new ConditionReport();
		
		if(!myReports.empty())
		{
			junk = "Summary of condition reports for item " + accessionNumber + '\n';
			for (int i = 0; i < sizeOf; i++)
			{
				thisReport = myReports.get(i);
				junk += "Date of Report " + thisReport.getSubmitDate() + '\n';
				junk += "Submitted by \t" + thisReport.getUserInfo() + '\n';
				junk += "Damage condtion " + thisReport.getDamageCode() + '\n';
				junk += "Notes~ " + thisReport.getComments() + '\n';	
			}
			junk += '\n';
		}
		else
			junk = "Item " + accessionNumber + " has no reports.\n";
			
			return junk;
	}
	/* Pushes a new conditionreport to the stack of CRs for this item.  Can 
	 * use any of CondionReports constructors for this method.  
	 */
	public void addConditionReport(ConditionReport report)
	{
		myReports.push(report);
	}

}
