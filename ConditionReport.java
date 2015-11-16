import java.util.Date;
import no.geosoft.cc.graphics.GScene;

/** Hold data for an Item's damage conditions
 * @(#)ConditionReport.java
 * Last updated by James Watkins
 *
 * @author Alex Fairhurst
 * @version 1.10 2015/11/15
 */


public class ConditionReport 
{
	private String damage; //one of the organization's supplied damage codes
	private String username; //the username of the submitting individual
	private String comments; //captures comments entered into the free-text area
	private Date submitDate; //the date and time that a report was submited
	private GScene markupImage; //an image of the item with damage markups
	//TODO determine if ImageIcon is the best class for image capture.
	
	
    public ConditionReport()
    {
		damage = null;
		username = null;
		comments = null;
		submitDate = null;
		markupImage = null;
    }
    
    public ConditionReport(String newDamage, String thisUser, String notes, Date reportDate, 
    		GScene damageImage)
    {
    	damage = newDamage;
		username = thisUser;
		comments = notes;
		submitDate = reportDate;
		markupImage = damageImage;
    	
    }
    public void viewConditionReport()
    {
    	//TODO implement a method that output a CR's String values
    	//and returns it's image
    	//for use in printing and viewing CRs for an item
    }
    public String getDamageCode()
    {
    	return damage;
    }
    public void setDamageCode(String arg)
    {
    	damage = arg;
    }
    public String getUserInfo()
    {
    	return username;
    }
    public void setUserInfo(String arg)
    {
    	username = arg;
    }
    public String getSubmitDate()
    {
    	//should be calling Date's toString method
    	return submitDate.toString();
    }
    public void setSubmitDate(Date today)
    {
    	submitDate = today;
    }
    public String getComments()
    {
    	return comments;
    }
    public void setComments(String notes)
    {
		comments = notes;    	
    }
    public GScene getDamageArea()
    {
    	return markupImage;
    }
    public void setDamageArea(GScene damage)
    {
    	markupImage = damage;
    }
	//TODO is there any use for a toString() for this class?
}