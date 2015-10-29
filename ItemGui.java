/* CS-112 FINAL PROJECT
   File Name:          	ItemGui.java
   Programmer:         	James Watkins
   Date Last Modified: 	May 19, 2012

   Problem Statement: Define a Gui interface which allows a user to track
   and view items of class Item.  Use a PropertyList ArrayList for
   managing and containing the Items.  User must be able to navigate through,
   add to, and remove items from the PropertyList ArrayList.  User must be
   protected from program failures through input filtering and exception
   handling.
   
GUI Components
   1. Create a main JFrame with a BorderLayout and a menuBar.
   2. Populate the menuBar with menus: File, Edit and Search.
   3. The File menu will allow the user to load files, save files and export
   the contents of a file to a .txt file.
   4. The Edit menu must allow the user to add Items to the database, remove
   single Items from the database, modify existing Items and clear the entire
   database.
   5. Create a Panel to be applied to the CENTER region of the JFrame, divide
   the JPanel into 1 row and two columns.
   6. On the left column, add a JLable for displaying pictures of Items.
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
	6. Specify a method which retrieves the name of the currentItem's image
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
	9. Method updateJFrame() is used to update the information and images
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
import java.text.NumberFormat;



public class ItemGui extends JFrame
{
	public static void main(String [] args)
	{
		ItemGui window = new ItemGui();
	}
//GUI Components
	private JFrame inputWindow, reportWindow;
	private JPanel textPanel, centerPanel, buttonPanel;
	private JButton first, second, third, fourth;
	private JTextField description, make, model, serial, year, price, qty;
	private JTextField descriptionText, makeText, modelText, serialText,
					   pictureText;
	private JLabel imageLabel;
	private LineBorder trim;
	private JMenuBar menuBar;
	private JMenu file, edit, search, help;
	private ImageIcon currentImage;
	private BufferedImage markupImage;  //Allows creation of markable raster overlays
	private Font labelFont, itemFont;
//variables used in program	
	private final int WIDTH = 700, SMALL_WIDTH = 375;
	private final int HEIGHT = 500, SMALL_HEIGHT = 300;
	private PropertyList localList = null;
	private Item currentItem;
	private int location;
	private String fileName;
	private boolean modifyNotAdd = false; //not sure that this is required any more
	public String userName; //user's login name for timestamping purposes
	public boolean loggedIn = false; //determines whether or not a user is logged in
	public int layoutGap = 10; //variable for adjusting gap settings in Gridlayout
	


//	NumberFormat money = NumberFormat.getCurrencyInstance();
	
	public ItemGui()
	{
		super("Inventory Management");
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
//		GridLayout buttonLayout = new GridLayout(1,5, layoutGap, layoutGap);
//		GridLayout buttonLayoutCenter = new GridLayout(1,2, layoutGap, layoutGap); //layout for CR markup and save
//layout for text area
//		GridLayout textAreaLayout = new GridLayout(9,2,5,5);  old
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
		edit = new JMenu("Edit");
		JMenuItem editAdd = new JMenuItem("Create CR");
		editAdd.addActionListener(new EditListener());
		JMenuItem editView = new JMenuItem("View CR");
		editView.addActionListener(new EditListener());
		JMenuItem editNew = new JMenuItem("Add Item"); //For testing
		editNew.addActionListener(new EditListener()); //For testing
		
		edit.add(editAdd);
		edit.add(editView);
		edit.add(editNew);
		
		//Search Menu
		search = new JMenu("Search");
		JMenuItem searchAll = new JMenuItem("Search");
		searchAll.addActionListener(new SearchListener());
		
		search.add(searchAll);

		//Help Menu
		help = new JMenu("help");
		JMenuItem helpItem = new JMenuItem("Help"); //launches help.html
		JMenuItem helpAbout = new JMenuItem("About");  //version info
		
		help.add(helpItem);
		help.add(helpAbout);
		
//creates menubar and adds pulldown menus to it		
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(search);
		menuBar.add(help);
		
//create a lable for displaying ImageIcons on the JFrame		
//		currentImage = new ImageIcon("splash.jpg");
//TODO image isn't displaying
		currentImage = new ImageIcon("none.jpg");
//		currentImage = new
		imageLabel = new JLabel();
		imageLabel.setIcon(currentImage);
		
//create a panel to dispaly information for the current item selected
//set panel layout, add text fields to panel
		textPanel = new JPanel();
		textPanel.setLayout(textAreaLayout);

//creates the JLabels and text fields used to display information about an
//Item.
		//Serial Number == Accession Number this project
		serial = new JTextField(20);
		serial.setEditable(false);
		//serial.setBackground(Color.WHITE);
		JLabel serialLabel = new JLabel("Accession Number");
		serialLabel.setFont(labelFont);
		
		//Description == Title of Art this project
		description = new JTextField(20);
		description.setEditable(false);
		//description.setBackground(Color.WHITE);
		JLabel descriptionLabel = new JLabel("Title:");
		descriptionLabel.setFont(labelFont);
		
		//Make == Artist this project
		make = new JTextField(20);
		make.setEditable(false);
		//make.setBackground(Color.WHITE);
		JLabel makeLabel = new JLabel("Artist:");
		makeLabel.setFont(labelFont);

		
//adds lables and text fields to the JPanel.
		textPanel.add(serialLabel);
		textPanel.add(serial);
		textPanel.add(descriptionLabel);
		textPanel.add(description);
		textPanel.add(makeLabel);
		textPanel.add(make);
		

//add the image panel and text panel to the center panel
		centerPanel = new JPanel();
		centerPanel.setLayout(centerLayout);
		centerPanel.setBorder(trim);
//		centerPanel.add(imagePanel);
		centerPanel.add(imageLabel);
		centerPanel.add(textPanel);		
		
//create the button Panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
//create buttons and actionListeners for each button
		
//TODO mofify listener functions to support
		first = new JButton("Search");
//		first.addActionListener(new MovementListener());
		first.addActionListener(new SearchListener());
		second = new JButton("View Mark-Up");
		second.addActionListener(new MovementListener());
		third = new JButton("New CR");
//		third.addActionListener(new MovementListener());
		third.addActionListener(new ReportListener());
//		third.addActionListener(new AddItemListener() );
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
	
	private String setTextFile()
	{
//allows user to specify the name of a text output file
		fileName = JOptionPane.showInputDialog(null, "Enter the " +
		"destination file (.txt)", "Text Output", JOptionPane.PLAIN_MESSAGE);
		
		return fileName;
	}
/*Searches database by accession number.  Will need to know if any specific error /content
 * checking is required. i.e. is there a specific format for Accession Reports.
 * 
 * Returns a string value to calling function.
 */
	
	private String setQuery()
	{
//Collect information from a user to use in searches and deletions.		

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
/*	This function is not required for this project.
 * 
 * private int confirmDeletion()
	{
//Creates a JOptionPane dialog box to get user confirmation before deleting
//item(s)		
		String message = "To continue select \"OK\"";
		String title = "Confirm Deleletion";
		
		int answer = JOptionPane.showConfirmDialog(null, message, title,
					JOptionPane.OK_CANCEL_OPTION);
					
		return answer;
	}
*/
	
/*Calls an Item's image by filename. Image is passed to the ImageIcon class
 * constructor and displayed using the ImageLabel class.
 */
	public void setImage(String imageName)
	{	
		currentImage = new ImageIcon(imageName);
		imageLabel.setIcon(currentImage);
	}

/*Creates a new CR for item currently displayed.  Launches a a new JFrame for
 * image markup using class (xxxx).  Present's user with a list of condition
 * codes loaded from the database.  Condition report saved to database as a
 * marked up image, condition codes and any comments.
 */  
//TODO modify to accept current item's attributes as Parameters
//TODO implement image overlay and markup
//TODO determine correct return type for database --probably CR Class
//	private void createItem()
//	{
/**Creates a JFrame for modification of existing Items.  JFrame displays
 *blank textfields for user data entry.  Information is passed to the
 *try/catch blocks for analysis and verification before being accepted.*/
/*		int rows = 5;
		int columns =2;
		
		inputWindow = new JFrame("Add an Item");
		inputWindow.setSize(SMALL_WIDTH, SMALL_HEIGHT);
		inputWindow.setResizable(false);
		inputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputWindow.setLocationRelativeTo(null);
		inputWindow.setVisible(true);
		
//Specify a layout for the input window and apply it to the window
		GridLayout inputWindowLayout = new GridLayout(rows, columns,layoutGap, layoutGap);
		inputWindow.setLayout(inputWindowLayout);
//TODO Assign Fields to panels, pannels to JFrame		
//Create two new JPanels to hold the text and image fields
		JPanel inputPanel = new JPanel();
		JPanel imagePanel = new JPanel(); //must be markable
		
//create a label to identity data fields
		JLabel serialLabel = new JLabel("Accession Number");
		serialText = new JTextField(30); //TODO must load from current item
		
		JLabel makeLabel = new JLabel("Title");
		makeText = new JTextField(30);
		
		JLabel modelLabel = new JLabel("Artist");
		modelText = new JTextField(30);
		
		JLabel pictureLabel = new JLabel("Enter the picture file's name");
		pictureText = new JTextField(30);
//		JButton addFileName = new JButton("Choose File");
//		addFileName.addActionListener(new AddItemListner());
		
//		JTextField blankField = new JTextField(0);
		
		JButton addButton = new JButton("Submit");
		addButton.addActionListener(new AddItemListener());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new AddItemListener());
		
		inputWindow.add(serialLabel);
		inputWindow.add(serialText);
		inputWindow.add(makeLabel);
		inputWindow.add(makeText);
		inputWindow.add(modelLabel);
		inputWindow.add(modelText);
		inputWindow.add(pictureLabel);
		inputWindow.add(pictureText);
		inputWindow.add(addButton);
		inputWindow.add(cancelButton);
	}//end of createItem()
*/	
	/**Creates a JFrame for modification of existing Items.  JFrame loads the
	 *data of the Item displayed in the main window.  User is then able to modify
	 *the existing Item's variables.  Modifications are passed to the try/catch
	 *blocks for analysis and verification before being accepted.*/	

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
		setImage(currentItem.getPic());
	}
	
	//Takes an Image object and converts it to a Buffered Image, which has
	//an alpha channel and markup tools
	private BufferedImage convertImage(ImageIcon arg)
	{
		
			
			BufferedImage myBuffImage = new BufferedImage(arg.getIconWidth(),
			arg.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

		
		return myBuffImage;
	}
//TODO investigate why string being passed as parameter.
	//Declare wipe as local variable?  Do we still need this function?
	
/**  Prints selected data to a text file when user calls registered events.
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
				localList.readFromFile("testdata.dat");  //TODO remove later
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
			String junk = e.getActionCommand();
			
			if(localList != null) //if data structure exists already
			{
				switch(junk)
				{
					case "Create CR":
						//stub until defined
						JOptionPane.showMessageDialog(null,"Create CR not defined yet.",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
						break;
					case "View CR":
						JOptionPane.showMessageDialog(null,"View CR not defined yet.",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
						break;
//					case "Add Item":
//						createItem();
//						break;
					default:
						JOptionPane.showMessageDialog(null,"Something Unexpected Happened",
						"Edit Menu Stub", JOptionPane.ERROR_MESSAGE);
				}//end switch
			}
			else
				alert("No Editing before loading data");
//This condition creates a new data structure to hold the created item, then creates the item.
/*			else 
			{
				if (junk.equals("Add Item"))
				{
					localList = new PropertyList();
					createItem();
				}
				else
					alert("I'm falling through");
			}	*/
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
	
/**  Event listener for scrolling through the data structure.  
 *  Click on main window's buttons trigger an event that alters the
 * 	content of the JFrame.
 * @author James
 */
//TODO Adjust MovementListener condition strings to represent new condition.	
	private class MovementListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String junk = e.getActionCommand();
//If arraylist is empty / null, the user cannot use movement buttons			
			if((localList != null)&&(localList.listIsEmpty()!=true))
			{
				//change to case switch
				if (junk.equals("|<<First"))
				{
					location = 0;
					updateJFrame(location);
				}
				else if (junk.equals("<Previous"))
				{
					if (location != 0)
					{
						location--;
						updateJFrame(location);
					}
				}
				else if (junk.equals("Next>"))
				{
					if (location != (localList.getDataBaseSize()-1))
					{
						location++;
						updateJFrame(location);
					}
				}
				else //last element
				{
					location = localList.getDataBaseSize()-1;
					updateJFrame(location);
				}
			}//end outer if
			else
				alert();
		}	
	} //end movementlistener class
	
/** AddItemListener is called when user selects create "Condition Report."
 *  All input fields are required.  Users are given three attempts to 
 *  correct any exceptions that are thrown.  
 * @author James
 *
 */
//TODO modularize try catch blocks with a function
//TODO update case variables in blocks
	private class AddItemListener implements ActionListener
	{
		String itemDescr, itemMake, itemSerial , itemPic;
		
		//, itemModel; 
		//int itemYear, itemQty;
		//double itemPrice;

		public void actionPerformed(ActionEvent e)
		{
			String junk = e.getActionCommand();
			
			if (junk.equals("Add Item"))
			{
//set the user's input as the textfield's text		
				serialText.setText(serialText.getText());
				descriptionText.setText(descriptionText.getText());
				makeText.setText(makeText.getText());
				pictureText.setText(pictureText.getText().toLowerCase());
				
 
//adjusting indent for this section because of long line entries
		String temp, secondAttempt, fileExtension;
//		int anInt;
//		double aDouble;
		boolean acceptableInput = true;
		
/*logic for each of the following try-catch pairs is as follows:
A user's input is collected from the text field and copied to a type
appropriate variable.  The input is analyzed agains an acceptable range of
responses. If the input does not satisfy the input criteria, a exception is
thrown with a specific message identifying the error.  The exception is
passed to a JOption dialog box which allows the user one attempt to correct
their mistake.  If the user again fails to satisfy the input requirements,
the input sesion is terminated and the information is discarded. If the user
meets all requirement, the information is fed into a constructor and added
to the PropertyList ArrayList and the current assets value is updated. The
JFrame will update to display the recent addition.
*/
	try
	{	
		temp = descriptionText.getText();
		if((temp == null)||(temp.equals("")))
			throw new InvalidInputException("Description may not be blank");
		else
			itemDescr = temp;
	}
	catch (InvalidInputException itemException)
	{
		secondAttempt = JOptionPane.showInputDialog(null, 
		itemException.getMessage(),"Invalid Input", 
		JOptionPane.PLAIN_MESSAGE);
		
		if((secondAttempt == null)||(secondAttempt.equals("")))
		{
			failure();
			inputWindow.dispose();
			acceptableInput = false;
		}
		else
			itemDescr = secondAttempt;
	}	
	try
	{
		temp = makeText.getText();
		if((temp == null)||(temp.equals("")))
			throw new InvalidInputException("Make may not be "+
			"blank. If none or unknown, state \"none\"");
		else
			itemMake = temp;
	}
	catch(InvalidInputException itemException)
	{
		secondAttempt = JOptionPane.showInputDialog(null, 
		itemException.getMessage(),"Invalid Input", 
		JOptionPane.PLAIN_MESSAGE);
		
		if((secondAttempt == null)||(secondAttempt.equals("")))
		{
			failure();
			inputWindow.dispose();
			acceptableInput = false;
		}
		else
			itemMake = secondAttempt;
	}
	try
	{
		temp = serialText.getText();
		if((temp == null)||(temp.equals("")))
			throw new InvalidInputException("Serial number may not "+
			"be blank. If none or unknown, state \"none\"");
		else
			itemSerial = temp;	
	}
	catch(InvalidInputException itemException)
	{
		secondAttempt = JOptionPane.showInputDialog(null, 
		itemException.getMessage(),"Invalid Input", 
		JOptionPane.PLAIN_MESSAGE);
		
		if((secondAttempt== null)||(secondAttempt.equals(" ")))
		{
			failure();
			inputWindow.dispose();
			acceptableInput = false;
		}
		else
			itemSerial = secondAttempt;
	}
	try
	{
		temp = pictureText.getText();
			
		if((temp!=null)&&(!temp.equals("")))
		{
			if(temp.equalsIgnoreCase("none"))
				temp = temp.toLowerCase()+".jpg";
		
			fileExtension = temp.substring(temp.length()-3, temp.length());
			
			if((fileExtension.equals("jpg"))||(fileExtension.equals("gif"))
			||(fileExtension.equals("png")))
				itemPic = temp;
				
			else
				throw new InvalidInputException("File must be in format"+
				" .jpg, .gif, or .png");	
		}
		else
			throw new InvalidInputException("Picture file cannot be "+
			"blank. If no picture, state \"none\".");
	}
	catch(InvalidInputException itemException)
	{
		secondAttempt = JOptionPane.showInputDialog(null, 
		itemException.getMessage(),"Invalid Input", 
		JOptionPane.PLAIN_MESSAGE);
		
		if((secondAttempt!=null)&&(!secondAttempt.equals("")))
		{		
			if (secondAttempt.equalsIgnoreCase("none"))
				secondAttempt = secondAttempt.toLowerCase()+".jpg";
				
			fileExtension = secondAttempt.substring(secondAttempt.length()-3,
													 secondAttempt.length());
			
			if((fileExtension.equals("jpg"))||(fileExtension.equals("gif"))
			||(fileExtension.equals("png")))
				itemPic = secondAttempt;								
				
			else
			{
				failure();
				inputWindow.dispose();
				acceptableInput = false;				
			}
		}
		else
		{
			failure();
			inputWindow.dispose();
			acceptableInput = false;			
		}
	}//last catch
	
	if((acceptableInput)&&(modifyNotAdd == false))
	{
		Item newItem = new Item(itemDescr, itemMake, itemSerial,
		itemPic); 
		
		localList.addToDataBase(newItem);
		updateJFrame(localList.getDataBaseSize()-1);
	}

	
	inputWindow.dispose();
	
	}//end if statement
		else
			inputWindow.dispose();
	}//end method
	
	}//end private inner additem class
	
	
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
			
			g.drawString(currentItem.toString(), 100, 100);
		
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
						//generate error popup window
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
	            //username and password login options (username is global)
	            userName = JOptionPane.showInputDialog(new JFrame(), "Username:");
	            String password = JOptionPane.showInputDialog(new JFrame(), "Password:");
	        
	            //username and password validity check
	            if(userName.length() < 3)
	            {
	                JOptionPane.showMessageDialog(this, "Username was missing or too short. Please try again.", "Error",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            else if(password.length() < 3)
	            {
	                JOptionPane.showMessageDialog(this, "Password was missing or too short. Please try again.", "Error",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            else
	            {
	                loggedIn = true; //set user to logged in state
	                return;
	            }
	        }  
	    }
	    
	    private class CancelListener extends JFrame implements ActionListener
	    {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		System.out.println("In the Cancel Listener");
	    		dispose();
	    	}
	    }
/**  ReportListener launches a new JFrame window displaying an image of the current
 * item on display, along with an image overlay of all previously reported damage.
 * The image and overlay are in the right JPane, text fields for anotating new damage
 * are on the left.  When the CR is submitted, it saves to the data structure housing
 * other CRs for the same Item.
 */
	    private class ReportListener extends JFrame implements ActionListener
	    {
	    	public void actionPerformed(ActionEvent e) //new Condition Report requested
	    	{
	    		if (localList != null)
	    		{
	    			reportWindow = new JFrame();
	    			setTitle("Create Condition Report");
	    			setSize(WIDTH, HEIGHT);
	    			setResizable(true);
	    			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    			setLocationRelativeTo(null);
	    		
	    		//need to set a layout	
	    		
	 //Create a JPanel for holding damage conditions, a notes field, and submit/ cancel buttons
		    		JPanel inputPanel = new JPanel(); // for annotations
		    		inputPanel.setLayout(new GridLayout(4, 1, 2 *layoutGap, layoutGap) );
	    		
	    			JTextField damageLabel = new JTextField("Select any newly discovered damages");
	    			damageLabel.setEditable(false);
	    			damageLabel.setFont(itemFont);
	    			String[] conditions = {"","condition1", "condition2", "condition3", "condition4", "condition5"}; 
	    			JComboBox damageCondition = new JComboBox(conditions);
	    			damageCondition.setFont(itemFont);
	    			damageCondition.setEditable(false);
	    		//TODO figure out how to return selected conditions
	    		
	    			JTextField notesLabel = new JTextField("Enter notes for new damages:");
	    			notesLabel.setEditable(false);
	    			notesLabel.setFont(itemFont);
	    		//JTextArea(rows, col)
		    		JTextArea notesField = new JTextArea("type here", 6, 40);
	    //		JScrollPane scroller = new JScrollPane();
		    		notesField.getPreferredSize();
	    			notesField.setFont(labelFont);
	    			notesField.setLineWrap(true);
	    			notesField.setTabSize(5);
	    		//returns input in textarea as String?
		    		String notes = notesField.getText();
	    		//for testing output
		    		System.out.println(notes);
	    		
	    			inputPanel.add(damageLabel);
	    			inputPanel.add(damageCondition);
		    		inputPanel.add(notesLabel);
		    		inputPanel.add(notesField);

	    		
	 //Create a panel for displaying image and hosting markup layer   		
	    	
	  		  		JPanel imagePanel = new JPanel(); //TODO call up the current Item's image and layered markup
	    			imagePanel.setLayout( new GridLayout(1,1,0,0) );
/*Working on creating a writeable, transparent overlay for the displayed item.  Markup image is of type Buffered
 *Image.  	    		
	    http://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html#TYPE_4BYTE_ABGR
	    http://docs.oracle.com/javase/tutorial/2d/images/drawonimage.html
	    https://www.java.net/node/646586
*/	    
	    			markupImage = new BufferedImage(currentImage.getIconWidth(), currentImage.getIconHeight(), 6 )	;
			//	markupImage.    
	    		
	    		
	    		//TODO Add item Image from currently displayed item
	    			imagePanel.add(imageLabel);
//	    		imagePanel.add(markupImage);

	//Create buttons for marking up an image, submitting a Condition Report and canceling the transaction

	    			JButton submit = new JButton("Submit");
	    			submit.setPreferredSize(new Dimension(200, 100));
	    		//TODO write new report to Item's queue ....
		    		JButton cancelButton = new JButton("Cancel");
		    		cancelButton.setPreferredSize(new Dimension(200, 100));
	    			cancelButton.addActionListener(new CancelListener());
	    			JButton markupButton = new JButton("Mark Up");
	    			markupButton.setPreferredSize(new Dimension(200, 100));
	    		//TODO create an actionlistener for markup of images.
	    			JPanel myButtonPanel = new JPanel();
	    			myButtonPanel.setLayout( new GridLayout(1,3, 2 * layoutGap, layoutGap) );
	    			myButtonPanel.add(markupButton, BorderLayout.LINE_START);
	    			myButtonPanel.add(submit, BorderLayout.CENTER);
	    			myButtonPanel.add(cancelButton, BorderLayout.LINE_END);
	    		
	 //Add all the three panels to the main panel for (formatting purposes)   		
	    			JPanel mainPanel = new JPanel(new GridLayout(1,2, layoutGap, 0));
	    			mainPanel.add(imagePanel, BorderLayout.LINE_START);
	    			mainPanel.add(inputPanel, BorderLayout.LINE_END);
	    		

	//Add the main panel to the JFrame and make it visible
	    			add(mainPanel, BorderLayout.PAGE_START);
	    			add(myButtonPanel, BorderLayout.PAGE_END);
//	    		mainPanel.add(myButtonPanel, BorderLayout.PAGE_END);
					pack();
	    			setVisible(true);	
	    		}
	    		else
	    			alert("Must load data file first");
	    		

/** TODO List:
 *	Figure out how to return CR inputs to the database
 *  Fix Cancel Button operation
 *	Add action Listener to the Markup button, will call up drawing tools
 *  Add action Listener to the Submit button that writes any changes to the Item's Queue 
 *  Add a vertical scroll bar to the JFrame.			
*/	   			
	    		
	    	}//end ActionListener
	    }//end ReportListener
}//end ItemGui
