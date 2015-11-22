/* CS-441 Software Engineering FINAL PROJECT
   File Name:          	ItemGui.java
   Programmer:         	Alex Fairhurst
   						John Leack
   						Will Nelson
   						Cody Solley
   						James Wakins
   Date Last Modified: 	Nov. 12 2015

   Problem Statement: Define a Gui interface which allows a user search a
   database of art items and submit condition reports for each item.
   condition reports will display a markable image of the item, display the
   organization's damage codes and have a field for general notes pertaining
   to the damage recorded.  When a user submits a condition report, the date,
   time, and user's username are appended to the condition report.  On exit
   the program must synchronize its local data to its main database.
   
GUI Components
   1. Create a main JFrame with a BorderLayout and a menuBar.
   2. Populate the menuBar with menus: File, Edit, Navigate, Search, and Help.
   3. The File menu enables user login, loading files, saving files, and printing
   	  reports.  The ability to Export files may be added in later releases.
   4. The Edit menu allows the viewing and creation of condition reports.
   5. Create a Panel to be applied to the CENTER region of the JFrame, divide
   the JPanel into 1 row and two columns.
   5. The search menu permits a search by accession number, artist or title.
   7. The navigation menu allows a user to move through all returned results.
//TODO - update the rest of the header
   7. The Right Column of the will be subdivided into two columns and 9 rows.
   These subdivision will be used for labeling and displaying the variables
   associated with the item displayed. One row will be used for displaying
   the total value of all assets.
   8. Create a panel for displaying JButtons allowing navigation through
   the PropertyList's elements.  Apply this panel to the main JFrame's SOUTH
   region.  Divide this Panel into 1 row and 5 columns.
   9. Create the JButtons for navigation. Buttons will allow navigation to
   the first, last, next and previous elements.
****End Of GUI components
*Methods
	1. Provide a method which launches a JOPtionPane Input dialog in order to
	collect and return the String name of a text file.
	2. Provide a method which launches a JOption Pane Input dialog to collect
	and return a user's search criteria.
	3. Provide a method to alert the user to illegal operations using a
	JOptionPane ERROR_MESSAGE dialog.
	4. Provide a method to alert the user of too many failed attempts at data
	entry using JOptionPane ERROR_MESSAGE dialog.
	5. Provide a method with uses a JOptionPane YES_NO dialog to allow the
	user to confirm deletions prior to execution.
	6. Specify a method which retrieves the name of the currentItem's ImageIcon
	file and then passes that name to be displayed on the main JFrame.
	7. Method createItem() will launch a new JFrame which has labels and
	textfields for the entry of information pertinent to the creation of a
	new Item. The JFrame will have a submission button and a cancel button.
	When one of the buttons is depressed, it fires an event to the
	AddItemListener class.
	8. Specify a method to allow the modification of existing Items.  The
	method behaves just like the createItem() method, except the modify()
	method calls up the variables of the Item currently displayed in the
	main JFrame.  The user modifies existing data and submits via submit or
	cancel buttons.
	9. Method updateJFrame() is used to update the information and ImageIcons
	displayed on the main JFrame.  It calls up the current element's
	variables and passes them to the JFrame's components.
	10. The clearJFrame() method uses a String parameter to overwrite the
	text fields of the main JFrame.
End of Methods***
*Inner Classes
	1. The text FileListener class is used for exporting the PropertyList
	to a text file. The class collects a String name for the file and then
	passes that name to PropertyList's printTextFile() method.
	2. Class OpenFileListener uses the JFileChooser class permitting the user
	to navigate folders and select an input file. Once the user selects a
	file, its information is loaded into a PropertyList ArrayList and the
	JFrame is updated to display element zero. The class also uses
	PropertyList's readFromFile() method for data I/O.
	3. SaveFileListner uses JFileChooser to allow the user to browse to the
	a desired destination folder and then specify a file name.  When the
	user inputs a file name and clicks save, the JFileChooser passes the
	file name to PropertyList's writeToFile() method.
	4. EditListener class is wholly associated with the Edit menu and
	facilitates the addition, removal, modification and erasure of Items of
	the	PropertyList.  The class requests user confirmation before any
	deletion, and passes all inputs to be verified against the try/catch
	criteria. The class relies on implementations of PropertyList's 
	ArrayList methods.
	5. Class SearchListener collects a search query as input from the user
	and then passes the query to PropertyList searchDataBase() method.
	6. The MovementListener class is responsible for user navigation through
	the PropertyList.  The buttons are associated to the numeric values of
	ArrayList elements and scale in response to additions and deletions.
	7. The AddItemListner class is responsible for ensuring data integrity
	and handel inputs from the createItem() and modifyItem() methods. The
	class collects data from the text fields of the create and modify JFrames
	using setText() and getText(). Once the inputs are collected they are
	temporarily assigned to variables and passed through a battery of test
	inside of try / catch blocks. If an input fails its test, an exception
	is triggered and the user is given one additional chance to correct the
	mistake. A subsequent failur terminates the modification or addition.
	If all data passes verification, then it is passed to either a method
	for modification of existing elements or a constructor for the creation
	of new Items.  New Items are added to the database and updateJFrame is
	invoked to display the addtion. The total value of all assets is 
	re-calculated and passed to the JFrame.
	
   Classes needed and Purpose (Input, Processing, Output)
   String - input, output					LineBorder - formatting (view)
   Integer - input, output					GridLayout - formatting (view)
   Double - input, output					BorderLayout - formatting (view)
   JFileChooser - input, output				BorderFactory - formatting (view)
   JFrame - input, output					JPanel - formatting (view)
   JTextField - input, output				Font - formatting (view)
   File - input, output						NumberFormat - output
   JButton - processing						JLabel - output
   JMenuBar - Processing					ImageIcon - output
   JMenu - Processing						JmenuItem - Processing
   ActionListener - processing
   JOptionPane - input, output, processing
   InvalidInputException - error handling
   PropertyList - input, output, processing
   Item - input, output, processing
   FileListener - output
   OpenFileListener - input
   SaveFileListener - output
   EditFileListener - processing
   SearchFileListener - output
   MovementFileListener - Processing
   AddItemListener - input
*/
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


//imports for bringing in Demo10 as a method
import java.io.File;
import java.util.Scanner;
import java.util.Date;

//libraries created by Jacob Dreyer
//http://www.java2s.com/Code/Java/Advanced-Graphics/DragandDrawDemo.htm
import no.geosoft.cc.geometry.Geometry;
import no.geosoft.cc.graphics.*;

//public class ItemGui extends JFrame
public class ItemGui extends JFrame
{
	public static void main(String [] args)
	{
		ItemGui window = new ItemGui();
	}
//GUI Components
//	private JFrame inputWindow, reportWindow;
//	private JFrame reportWindow;
	private JDialog reportWindow;
	private JPanel textPanel, centerPanel, buttonPanel;
	private JButton first, second, third, fourth;
	private JTextField description, make, model, serial, year, price, qty;
	private JTextField descriptionText, makeText, modelText, serialText,
					   pictureText;
	private JLabel imageLabel;
	private LineBorder trim;
	private JMenuBar menuBar;
	private JMenu file, edit, search, navigate, help, other;
	private ImageIcon currentImage;
//	private BufferedImage markupImage;  //Allows creation of markable raster overlays
	private Font labelFont, itemFont;
//variables used in program	
	private final int WIDTH = 700, SMALL_WIDTH = 375;
	private final int HEIGHT = 500, SMALL_HEIGHT = 300;
	private PropertyList localList = null;
	private Item currentItem;
	private int location;
	private String fileName;
	public String loginName; //user's login name for timestamping purposes
	public boolean loggedIn = false; //determines whether or not a user is logged in
	public int layoutGap = 10; //variable for adjusting gap settings in Gridlayout
	private static int numberOfConditions = 63 ;//supplied by client
	public String conditionSourceFile = "DamageCodes.txt";  //input file for damage condition
	
	private GScene scene_;
	private GSegment  route_, segment_;
	private int[]     xy_;
	private String selectedCondition; //selected condition from damage codes
	public JTextArea textArea;	//input for notes on condition reports


	
	public ItemGui()
	{

		super("Condition Report System");

	//	super("Inventory Management");
		setTitle("San Diego Museum of Art - Condition Report System");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true); //allows window to be resized
		setLocationRelativeTo(null);
		
		BorderLayout manager = new BorderLayout();
		setLayout(manager);
		
//layout for center panel
		//rows, columns, hgap, vgap
		GridLayout centerLayout = new GridLayout(1,2,layoutGap,layoutGap);
//layout for button panel along bottom of JFrame
		GridLayout buttonLayout = new GridLayout(1, 4, layoutGap, layoutGap);
		GridLayout textAreaLayout = new GridLayout(6,1,(layoutGap /2), (layoutGap/2)); //layout for text output fields and titles
//set a font profile for JFrame
		labelFont = new Font("COURIER", Font.PLAIN, 12); //font style for text headings
		itemFont = new Font("COURIER", Font.BOLD, 14); //font style for text output
		trim = (LineBorder)BorderFactory.createLineBorder(Color.BLACK, 2);
		

//Create menuBars, menus and menu Items, attach ActionListeners to each
//menuItem and add the manuItems to the menu
		
		//File Menu
		file = new JMenu("File");
		JMenuItem fileLogin = new JMenuItem("Login");
		fileLogin.addActionListener(new LoginListener());
		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.addActionListener(new OpenFileListener());
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(new FileSaveListener());
		JMenuItem fileExport = new JMenuItem("Export");
		fileExport.addActionListener(new TextFileListener());
		JMenuItem filePrinter = new JMenuItem("Print");
		filePrinter.addActionListener(new PrintFileListener());
		
		file.add(fileLogin);
		file.add(fileOpen);
		file.add(fileSave);
		file.add(fileExport);
		file.add(filePrinter);
		
		
		//Edit Menu
		edit = new JMenu("CR");
		JMenuItem editAdd = new JMenuItem("New CR");
		editAdd.addActionListener(new ReportListener());
		JMenuItem editView = new JMenuItem("View CR");
		editView.addActionListener(new EditListener());
		
		edit.add(editAdd);
		edit.add(editView);
		
		
		//Search Menu
		/* Create a Search menu for JMenuBar.  Add a menu item to search against
		 * all available fields.  Search results are loaded int to interface for
		 * review / manipulation
		 */
		search = new JMenu("Search");
		JMenuItem searchAll = new JMenuItem("Search");
		searchAll.addActionListener(new SearchListener());
		
		search.add(searchAll);
		
		//Navigate Menu
		/* Create Navigate Menu for JMenuBar.  Add menu items for traversing
		 * results returned from query.  
		 */		
		navigate = new JMenu("Navigate");
		JMenuItem navigateFirst = new JMenuItem("First");
		navigateFirst.addActionListener(new MovementListener());
		JMenuItem navigatePrevious = new JMenuItem("Previous");
		navigatePrevious.addActionListener(new MovementListener());
		JMenuItem navigateNext = new JMenuItem("Next");
		navigateNext.addActionListener(new MovementListener());
		JMenuItem navigateLast = new JMenuItem("Last");
		navigateLast.addActionListener(new MovementListener());
		
		navigate.add(navigateFirst);
		navigate.add(navigatePrevious);
		navigate.add(navigateNext);
		navigate.add(navigateLast);
		
		
		//Other Menu
		other = new JMenu("Other");
		JMenuItem otherHelp = new JMenuItem("Help"); //assist with program usage
		otherHelp.addActionListener(new OtherListener());
		JMenuItem otherAbout = new JMenuItem("About");  //version info
		otherAbout.addActionListener(new OtherListener());
		
		other.add(otherHelp);
		other.add(otherAbout);
		
//creates menubar and adds pulldown menus to it		
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(search);
		menuBar.add(navigate);
		menuBar.add(other);
		
//create a label for displaying ImageIcons on the JFrame		
		currentImage = new ImageIcon("welcome.jpg");
		imageLabel = new JLabel();
		imageLabel.setIcon(currentImage);
		
//create a panel to display information for the current item selected
//set panel layout, add text fields to panel
		textPanel = new JPanel();
		textPanel.setLayout(textAreaLayout);

//creates the JLabels and text fields used to display information about an
//Item.
		//Serial Number == Accession Number this project
		serial = new JTextField(20);
		serial.setEditable(false);
		JLabel serialLabel = new JLabel("Accession Number");
		serialLabel.setFont(labelFont);
		
		//Description == Title of Art this project
		description = new JTextField(20);
		description.setEditable(false);
		JLabel descriptionLabel = new JLabel("Title:");
		descriptionLabel.setFont(labelFont);
		
		//Make == Artist this project
		make = new JTextField(20);
		make.setEditable(false);
		JLabel makeLabel = new JLabel("Artist:");
		makeLabel.setFont(labelFont);
		
//adds lables and text fields to the JPanel.
		textPanel.add(serialLabel);
		textPanel.add(serial);
		textPanel.add(descriptionLabel);
		textPanel.add(description);
		textPanel.add(makeLabel);
		textPanel.add(make);
		

//add the ImageIcon panel and text panel to the center panel
		centerPanel = new JPanel();
		centerPanel.setLayout(centerLayout);
		centerPanel.setBorder(trim);
//		centerPanel.add(ImageIconPanel);
		centerPanel.add(imageLabel);
		centerPanel.add(textPanel);		
		
//create the button Panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
//create buttons and actionListeners for each button
		
//TODO modify listener functions to support
		first = new JButton("Login");
		first.addActionListener(new LoginListener());
		second = new JButton("Search");
		second.addActionListener(new SearchListener());
		third = new JButton("New CR");
		third.addActionListener(new ReportListener());
		fourth = new JButton("Print");
		fourth.addActionListener(new PrintFileListener());
//add the buttons to the button panel		
		buttonPanel.add(first);
		buttonPanel.add(second);
		buttonPanel.add(third);
		buttonPanel.add(fourth);
//add all components to the JFrame		
		add(buttonPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
		setJMenuBar(menuBar);
		setVisible(true);
	}	
//prompts user to input a file name for saving output	
	private String setTextFile()
	{
		fileName = JOptionPane.showInputDialog(null, "Enter the " +
		"destination file (.txt)", "Text Output", JOptionPane.PLAIN_MESSAGE);
		
		return fileName;
	}

/*Searches database by accession number. Return any records matching the query 
 * in any member value.
 * Returns a string value to calling function.
 */
	private String setQuery()
	{
		String query = JOptionPane.showInputDialog(null, "Enter the Accession "+
		"Number to search.\n", "Search Input", JOptionPane.PLAIN_MESSAGE);
		
		return query;
	}
/*This method throws a JOption pain in response to an invalid selection, such as trying
to search before any data is loaded into the program's data structure.*/
	private void alert()
	{
		JOptionPane.showMessageDialog(null, "You must open a file before "+
		"attempting this operation.\n","Illegal Operation", 
		JOptionPane.ERROR_MESSAGE);
	}
	private void alert(String arg)
	{
		JOptionPane.showMessageDialog(null, arg,"Illegal Operation", 
		JOptionPane.ERROR_MESSAGE);
	}
	private void closeWindow(JFrame arg)
	{
		arg.dispose();
	}
	
/*Method invoked in response to an exception being thrown and being handled
 * in one of the catch blocks.  Presents a JOptionPane dialog window. Handled by
 * Invalid Input Exception.
 */
	private void failure()
	{  
	 	JOptionPane.showMessageDialog(null, "A failure has occurred "+
		"aborting data entry.", "Invalid Input Exception",
		JOptionPane.ERROR_MESSAGE);
	}
	
/*Calls an Item's ImageIcon by filename. ImageIcon is passed to the ImageIcon class
 * constructor and displayed using the ImageIconLabel class.
 */
	public void setImageIcon(String ImageIconName)
	{	
		currentImage = new ImageIcon(ImageIconName);
		imageLabel.setIcon(currentImage);
	}
	
/**Updates JFrame display with new input data.  Parameter location refers to
 * 	an element number in data structure.
 */
	private void updateJFrame(int arg)
	{
		System.out.println("value of element item in update JFrame: " + arg);
		currentItem = localList.getItem(arg);

		serial.setText(currentItem.getAccessionNumber());
		make.setText(currentItem.getArtist());
		description.setText(currentItem.getTitle());
		setImageIcon(currentItem.getPic());
	}
	
/* Prints selected data to a text file when user calls registered events.
 * UserResponse is the filename to be created.  Action only performed if the
 * data structure contains objects and a file name is provided.   
 */
	private class TextFileListener extends JFrame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String userResponse = setTextFile();
			System.out.println(userResponse);
//if the PropertyList is not null, and the user specifies a name, output
//propertyList to a user specified file.			
			if((localList != null)&&(userResponse != null)&&
				(!userResponse.equals("")))
				localList.printTextFile(userResponse);
			else if (localList == null)					
				alert();
		}
	}//end private inner class TextFileListener
/**  OpenFileListener launches a JFileChooser window allowin user to navigate
 *  to the file to load.  Filename and path returned to the calling object.
 *  Filename and path are validated to prevent blank and / or empty values.
 *  Objects from file are loaded into the data structure for manipulation.
 * @author James
 */
	private class OpenFileListener extends JFrame implements ActionListener
	{
		private File fileName;
		
		public void actionPerformed(ActionEvent e)
		{
			/*if (loggedIn == false)
			{
				 alert("You must log-in first.");
	             return;
			}*/
			
			analyzePath();
		}//end of Open
/*		hardcoding in file name for development
 *		public File getFileOrDirectory()
		{//instantiation of JFileChooser for browsing of file structure,
		//FILES_AND_DIRECTORIES constant allows user to navigate / view
		//both files and directories.
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//sets the JFileChooser to be configured for filesaving
			int result = fileChooser.showOpenDialog(this);
			
			if (result == JFileChooser.CANCEL_OPTION)
				dispose();
			else
			{
				fileName = fileChooser.getSelectedFile();
				
				if((fileName == null)||(fileName.getName().equals("")))
				{
					JOptionPane.showMessageDialog(this, "Invalid Name",
					"Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
			return fileName;
		} */ //hard coding file name for testing remove later
		public void analyzePath()
		{

/*			HARD CODING FILE NAME	
 *			File name = getFileOrDirectory();
	

			if(name != null)
			{
				if(name.exists())
				{
/*if the name is valid, and the name exists, load the date into a
 *ProperyList, set the current location to slot 0 and update the display.
 *calculate the value of the PropertyList.*/
				localList = new PropertyList();
				localList.readFromFile("testdata2.dat");  //TODO remove later
//				localList.readFromFile(name.getName());	
				updateJFrame(0);
				location = 0;
//				}
//			}//end outer if 	
/*	HARDCODING		
			else //is not a file directory, or user cancels,
			// generate error message
			{
				JOptionPane.showMessageDialog(this,"Aborting request",
				"Open File Aborted", JOptionPane.ERROR_MESSAGE);
			}
*/ //HARDCODING
		}		
	}//end private inner class OpenFileListener
	
/**  Launches JFileChooser permitting allowing browsing to the desired save location
 *  and specification of the file name. File save only permitted if the path is valid
 *  (i.e. not blank) and a file name provided.
 * @author James
 */
 
	public class FileSaveListener extends JFrame implements ActionListener
	{
		private File fileName;
		private int result;
 
		public void actionPerformed(ActionEvent e)
		{
			if(localList != null)
				analyzePath();
			else
				alert();
		}
		private File getFileOrDirectory()
		{
		//instantiates an instance of JFileChooser, allowing user to select
		//destination file and folder 
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode
										(JFileChooser.FILES_AND_DIRECTORIES);
			//sets JFile Chooser to a save file format
			result = fileChooser.showSaveDialog(this);			
			
			if (result == JFileChooser.CANCEL_OPTION)//allows cancelation
				dispose();
			else
			{
				fileName = fileChooser.getSelectedFile();
			//prevents user from saving files without a name	
				if((fileName == null)||(fileName.getName().equals("")))
				{
					JOptionPane.showMessageDialog(this, "Invalid Name", 
					"Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
			return fileName; //returns class File to calling method
		}
		public void analyzePath()
		{
			File name = getFileOrDirectory();
		//if the user chooses to save the file, get its name	
			if(result == JFileChooser.APPROVE_OPTION)
    			localList.writeToFile(name.getName());
				
			else //is not a file directory, or user cancels,
			// generate error message
			{
				JOptionPane.showMessageDialog(this,"Aborting Request",
				"Save file aborted", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//end private inner class FileSaveListener
	

	private class EditListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			/*if (loggedIn == false)
			{
				 alert("You must log-in first.");
	             return;
			}*/
			
			String junk = e.getActionCommand();
			
			if(localList != null) //if data structure exists already
			{
				switch(junk)
				{
					case "New CR":
						new ReportListener();
						JOptionPane.showMessageDialog(null,"New CR not defined yet.",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
						break;
					case "View CR":
						JOptionPane.showMessageDialog(null,"View CR not defined yet.",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
						break;
					default:
						JOptionPane.showMessageDialog(null,"Something Unexpected Happened",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
				}//end switch
			}
			else
				alert("No Editing before loading data");
		}
	}//end private inner class EditListener	

/**  SearchListener is the observer for search requests.  Query performed
 *  using a string provided by the user.  Location of the item is returned
 * 	as an integer.  An alert is thrown if the object is not found.
 * @author James
 */
	private class SearchListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{
			/*if (loggedIn == false)
			{
				 alert("You must log-in first.");
	             return;
			}*/
			
			if (localList != null)
			{//uses JOptionPane to collect user's search criteria
				String userInput = setQuery();
				int temp;
				
				if((userInput != null)&&(!userInput.equals("")))
				{
					temp = localList.searchDataBase(userInput);
					
					if (temp >= 0)
						updateJFrame(temp);
					else
						JOptionPane.showMessageDialog(null, 
						"Item was not found", "Search Result",
						JOptionPane.PLAIN_MESSAGE);						
				}
			}	
			else
				alert("Item not found");
		}
	}//end private inner class SearchListener
	
/*  Event listener for scrolling through the data structure.  
 *  Click on navigation button to trigger an event that alters the
 * 	content of the JFrame.
 * @author Jonathan & James
 */
//TODO Adjust MovementListener condition strings to represent new condition.	
	private class MovementListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
	        {
				String navSelection = e.getActionCommand();
				
				if((localList != null)&&(localList.listIsEmpty()!=true)) 
				{
					if (navSelection.equals("First"))
					{
						location = 0;
						updateJFrame(location);
					}
					else if (navSelection.equals("Previous"))
					{
						if (location != 0)
						{
							location--;
							updateJFrame(location);
						}
						else
						{
							location = localList.getDataBaseSize()-1;
							updateJFrame(location);
						}
					}
					else if (navSelection.equals("Next"))
					{
						if (location != (localList.getDataBaseSize()-1))
						{
							location++;
							updateJFrame(location);
						}
						else
						{
							location = 0;
							updateJFrame(location);
						}
					}
					else //last element
					{
						location = localList.getDataBaseSize()-1;
						updateJFrame(location);
					}	
				}
				else
				{
					alert("Please open a database file before using navigation.");
				}
	        }
	} //end movementlistener class
	
	/** PrintListener allows printing of CRs
	 * Implements Java Graphics 2D API to print and access system default printer.
	 * @author Cody Solley
	 * **/
	private class PrintFileListener implements ActionListener, Printable
	{
		public int print(Graphics g, PageFormat pf, int page) throws PrinterException
		{
			//Limits to one page, page count starts at 0
			if (page > 0)
			{
				return NO_SUCH_PAGE;
			}
		
			//Need to define print area, so must declare graphic then set coordinates via pageformat to show where to print on the page
			Graphics2D g2d = (Graphics2D)g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());
			
			//render what we want to print
			g.drawString("Accession Number:", 100, 100);
			g.drawString(currentItem.getAccessionNumber(), 207, 100);
			g.drawString("Artist:", 100, 125);
			g.drawString(currentItem.getArtist(), 135, 125);
			g.drawString("Title:", 100, 150);
			g.drawString(currentItem.getTitle(), 130, 150);
			g.drawString("Picture File:", 100, 175);
			g.drawString(currentItem.getPic(), 167, 175);
		
			//Must return to indicate that the object is part of the printed document
			return PAGE_EXISTS;
		}
	
		public void actionPerformed(ActionEvent e)
		{
			String junk = e.getActionCommand();
			if(junk.equals("Print"))
			{
				//Set the print job for the CR record, pop up the Print option window
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(this);
				
				//Check for user to click ok, boolean only true if OK clicked, everything else is false
				boolean ok = job.printDialog();
				
				//Execute Print
				if(ok)
				{
					try
					{
						job.print();
					}
					catch (PrinterException ex)
					{
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Error Executing Print Request", "Printer Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		}
	}//end PrintFileListener
	
	/**  LoginListener allows a user to authenticate before interacting with
	*  the Condition Reports system. Use of other functions will be
	*  disabled until a user has logged in.
	* @author Jonathan
	*/
	    private class LoginListener extends JFrame implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	//alert user if already logged in
	        	if(loggedIn == true)
	        	 {
	                JOptionPane.showMessageDialog(this, "You have been logged out. You can now log in with a new username.", "Alert",
	                        JOptionPane.ERROR_MESSAGE);
	                loggedIn = false;
	            }
	        	
	        	//request username
	        	loginName = JOptionPane.showInputDialog(new JFrame(), "Username:");
	            
	            //loginname validity check
	            if(loginName.length() < 5)
	            {
	                JOptionPane.showMessageDialog(this, "Username was missing or too short. Please try again.", "Alert",
	                        JOptionPane.ERROR_MESSAGE);
	                loggedIn = false;
	                return;
	            }
	            
	            //request password
	            String password = JOptionPane.showInputDialog(new JFrame(), "Password:");
	        
	            //password validity check
	            if(password.length() < 5)
	            {
	                JOptionPane.showMessageDialog(this, "Password was missing or too short. Please try again.", "Alert",
	                        JOptionPane.ERROR_MESSAGE);
	                loggedIn = false;
	                return;
	            }

	            loggedIn = true; //set user to logged in state
	            return;
	        }  
	    }

/*  ReportListener launches a new JFrame window displaying an ImageIcon of the current
 * item on display, along with an ImageIcon overlay of all previously reported damage.
 * The ImageIcon and overlay are in the right JPane, text fields for anotating new damage
 * are on the left.  When the CR is submitted, it saves to the data structure housing
 * other CRs for the same Item.
 */
 	private class ReportListener extends JFrame implements ActionListener, GInteraction
	{
		public void actionPerformed(ActionEvent e) //new Condition Report requested
	    {

			reportWindow = new JDialog();
			setTitle("Create a condition report");
 			setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); 
     		getContentPane().setLayout (new BorderLayout());
    		getContentPane().add (new JLabel ("Draw line on map using mouse button 1"),
    						 BorderLayout.PAGE_START);
    	
    	 	// Create the graphic canvas
    		GWindow window = new GWindow();
    		getContentPane().add (window.getCanvas(), BorderLayout.CENTER);
    
    		// Create scene with default viewport and world extent settings
    		scene_ = new GScene (window);
  
	    	// Create a graphic object
	    	GObject object = new TestObject (currentItem.getPic());
	    	scene_.add (object);
    	
    	    //Create a JPanel for condition pulldown and Radio Buttons
		    JPanel inputPanel = new JPanel(new BorderLayout());
		   	JPanel menuPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		    JRadioButton redRadio = new JRadioButton("Red", true);
		  	redRadio.addActionListener(new ColorListener());
		    JRadioButton yellowRadio = new JRadioButton("Yellow");
		 	yellowRadio.addActionListener(new ColorListener());
		    JRadioButton greenRadio = new JRadioButton("Green");
		  	greenRadio.addActionListener(new ColorListener());
		   	ButtonGroup myButtons = new ButtonGroup();
		   	myButtons.add(redRadio);
		    myButtons.add(yellowRadio);
		    myButtons.add(greenRadio);
	       
		    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		    buttonPanel.add(redRadio);
		    buttonPanel.add(yellowRadio);
		    buttonPanel.add(greenRadio);
		
		    //Read in damage codes from DamageCodes.txt, populate to an array, display array
		    //as a JComboBox.
		    String [] conditions = new String[numberOfConditions];
		    try{
		    	Scanner input = new Scanner(new File (conditionSourceFile));
		    	int counter = 0;
		    	while(input.hasNext())
		    	{
		    		conditions[counter] = input.nextLine();
		    		counter++;
		    	} 
		    	input.close();   	
		    }
		    catch(IOException msg)
		    {
		    	System.out.println("An input error has occurred.");
		    }
		    
		    JComboBox damages = new JComboBox(conditions);
		 	menuPanel.add(buttonPanel);
		 	menuPanel.add(damages);
		 	damages.addActionListener(new DamagesListener());
		    //inputPanel.add(menuPanel, BorderLayout.PAGE_START);
		    
		    JPanel textPanel = new JPanel();
		   	textPanel.add(new JLabel("Enter damage remarks here:"), BorderLayout.PAGE_START);
		   	//TODO - figure out how to limit text input to the space provided, or enforce
		   	// a char limit
		   	textArea = new JTextArea(5, 40);
		   	textArea.setLineWrap(true);
		   	textArea.setTabSize(5);
		   	textPanel.add(textArea);
		 	
		  	JPanel buttonPanel2 = new JPanel(new GridLayout(1,2, 10, 10));
		   	JButton button1 = new JButton("Submit");
		   	button1.addActionListener(new ButtonListener());
		   	buttonPanel2.add(button1, BorderLayout.LINE_START);
		
			//add the radio buttons and the jcombo box to the JPanel	
		    inputPanel.add(menuPanel, BorderLayout.PAGE_START);
		   	inputPanel.add(textPanel, BorderLayout.CENTER);
		   	inputPanel.add(buttonPanel2, BorderLayout.PAGE_END);
		   	  	
		   	add(inputPanel, BorderLayout.SOUTH);
		     
			//pack the contents into the jframe and make visible
		    pack();
		    setSize (new Dimension (500, 500));
		    setVisible (true);
	
	   		window.startInteraction (this);

/** TODO List:
 *	Figure out how to return CR inputs to the database
 *  Fix Cancel Button operation --Done
 *	Add Action Listener to Cancel Button -- No longer Required
 *  Add action Listener to the Submit button that writes any changes to the Item's Queue 
 *  Add a vertical scroll bar to the JFrame. --Maybe			
*/	   			
	    		
	}//end ActionListener
		
 //Captures mouse events on the canvas inside of createCR() JFrame.
		public void event (GScene scene, int event, int x, int y)
	  	{
		    switch (event) {
		      case GWindow.BUTTON1_DOWN :
		        xy_ = new int[] {x, y};
		        route_.setGeometry (xy_);
		        scene_.refresh();
		        break;
		
		      case GWindow.BUTTON1_DRAG :
		        int[] a = new int[xy_.length + 2];
		        System.arraycopy (xy_, 0, a, 0, xy_.length);
		        a[a.length-2] = x;
		        a[a.length-1] = y;
		        xy_ = a;
		        route_.setGeometry (xy_);
		        scene_.refresh();
		        break;
			}
	  	} //end event()
	    public void draw()
	    {
	      segment_.setGeometry (0, 0);
	      route_.setGeometry (xy_);
	    }  	
  		
	    	
	}//end ReportListener
	    
/*  OtherListener provides the user with one of two options: help and about.
* Help details the functionality of the program by opening an html file. Meanwhile, about
* displays program information, such as the version number.
* @author Jonathan
*/
	    private class OtherListener extends JFrame implements ActionListener
	    {
	    	public void actionPerformed(ActionEvent e) //new Condition Report requested
	    	{
	    		String navSelection = e.getActionCommand();
				
				if (navSelection.equals("Help"))
				{
					try
					{
						Desktop.getDesktop().open(new File("help.html"));
					}
					catch (IOException ex) 
					{
					}
				}
				else if (navSelection.equals("About"))
				{
					JOptionPane.showMessageDialog(null,"Version Number:\nv1.00\n\nCreated By:\nJames Watkins\nJonathan Leack\nCody Solley\nWilliam Nelson\nAlexander Fairhurst");
				}
				else
				{
					alert("An issue was encountered in the Other menu.");
				}
	    	}
	    }//end HelpListener
 // Creates markup shape on the CR image file.  
    private class TestObject extends GObject
  	{
    	TestObject (String imageFileName)
    	{
      		segment_ = new GSegment();
      		addSegment (segment_);

	     	 GImage image = new GImage (new File (imageFileName));
	     	 image.setPositionHint (GPosition.SOUTHEAST);
		      segment_.setImage (image);
		
		      route_ = new GSegment();
		      addSegment (route_);
		
		      GStyle routeStyle = new GStyle();
			  routeStyle.setForegroundColor(Color.RED);
		      routeStyle.setLineWidth (2);
		      routeStyle.setAntialiased (true);
		      route_.setStyle (routeStyle);
		}
   	public void draw()
    {
      	segment_.setGeometry (0, 0);
      	route_.setGeometry (xy_);
    }
  	}//end TestObject class
/* ButtonListener is called within the CreateCR JFrame.  This class
 * handles color change requests, JComboBox changes, report submission
 * and cancelation of reports. On submit, a new ConditionReport object
 * is created and pushed to the current Item's stack of condition
 * reports.  A cancel disposes of the window.
 */
   private class ButtonListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		String junk = e.getActionCommand();
    		switch(junk)
    		{
    			//On submit, collect user input and push to item stack
    			case "Submit": 
    				System.out.println(selectedCondition);
    				
    				String notes = textArea.getText();
    					if(notes == null)
    						notes = "No notes provided";
    					else
    						System.out.println(notes);
    	//TODO - update as login name change
    				if(loginName == null)
    					loginName = "not logged in";
    					
    				Date now = new Date();
    				if (now == null)
    					System.out.println("Date is null");
    				else
    					System.out.println(now.toString());
    //Collect all user information from the Condition Report window and push to a CR 
    //constructor.  Add the ConditionReport to the Item's stack.				
    				ConditionReport thisReport = new ConditionReport( selectedCondition,
    				loginName, notes , now, scene_);
  		    		currentItem.addConditionReport(thisReport);
  		    		//TODO - force window to close on submit or cancel
  		    //		reportWindow.dispatchEvent(new WindowEvent(reportWindow, WindowEvent.WINDOW_CLOSING));
  		    			//TODO - after port, implement push to stack with method
    					//addConditionReport(ConditionReport report)
    				//TODO - VERIFY ALL INPUTS CAPTURED - HIGH
    				break;
    			default:
    				System.out.println("Something went wrong.");
    		}//end switch
    	}//end actionPerformed()
    }//end ButtonListener  	
  	
      public class ColorListener implements ActionListener
      {
      	
      	public void actionPerformed(ActionEvent e)
      	{
      		String colorSelect = e.getActionCommand();
    		switch(colorSelect)
    		{
    			case "Green":
    				System.out.println("Green event fired");
    			    GStyle style1 = new GStyle();
    			    style1.setForegroundColor(Color.GREEN);
    			    style1.setLineWidth (2);
    			    style1.setAntialiased (true);
    			    route_.setStyle (style1);
    				break;
    			case "Red":
    				System.out.println("Red event fired");
    			    GStyle style2 = new GStyle();
    			    style2.setForegroundColor(Color.RED);
    			    style2.setLineWidth (2);
    			    style2.setAntialiased (true);
    			    route_.setStyle (style2);
    				break;
    			case "Yellow":
    				System.out.println("Yellow event fired");
    			    GStyle style3 = new GStyle();
    			    style3.setForegroundColor(Color.YELLOW);
    			    style3.setLineWidth (2);
    			    style3.setAntialiased (true);
    			    route_.setStyle (style3);
    				break;
    			default:
    				System.out.println("Something went wrong.");
    			
    		}//end switch
      	}//end actionPerformed()
      }//end ColorListener()
   
/** DamagesListener allows a user to select the type of condition or damages
 * have occurred at the selected spot from a list of 62 items in a drop
 * down box.
 * @author Jonathan
 */
   private class DamagesListener implements ActionListener
   {
   	
   	public void actionPerformed(ActionEvent e)
   	{
   		JComboBox cb = (JComboBox)e.getSource();
   		String sc = (String)cb.getSelectedItem();
   		selectedCondition = sc;	
   		System.out.println(sc);	
   	}//end actionPerformed()
   }//end DamagesListener()  	
  	
  	
}//end ItemGui
