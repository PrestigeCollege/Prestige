import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.Date;
import javax.swing.ImageIcon;
//import ItemGui.OtherListener; //commented out due to error during "New CR"

import java.util.Scanner;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//libraries created by Jacob Dreyer
import no.geosoft.cc.geometry.Geometry;
import no.geosoft.cc.graphics.*;

/**
 * G demo program. Demonstrates:
 *
 * <ul>
 * <li>Simple image handling
 * <li>Simple drawing interaction
 * </ul>
 * 
 * @author <a href="mailto:jacob.dreyer@geosoft.no">Jacob Dreyer</a>
 * source of class and libraries:
 * http://www.java2s.com/Code/Java/Advanced-Graphics/DragandDrawDemo.htm
 */   
public class Demo10 extends JFrame
  implements GInteraction
{
  private GScene    scene_;
  private GSegment  route_;
  private int[]     xy_;
  public String selectedCondition; //condition selected in drop down box
//  static variables for use with BorderBagLayout
	private JTextArea textArea;

  private static int numberOfConditions = 62 ;//supplied by client
  public String conditionSourceFile = "DamageCodes.txt";  //input file for damage condition
   
  public Demo10 (String imageFileName)
  {
  	super ("Create markup for condition report");
    String imageFile = imageFileName;     
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE); 	
    getContentPane().setLayout (new BorderLayout());
    getContentPane().add (new JLabel ("Draw line on map using mouse button 1"), BorderLayout.PAGE_START);
     
    // Create the graphic canvas
    GWindow window = new GWindow();
    getContentPane().add (window.getCanvas(), BorderLayout.CENTER);
    
    // Create scene with default viewport and world extent settings
    scene_ = new GScene (window);
  
    // Create a graphic object
    GObject object = new TestObject (imageFileName);
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
    catch(IOException e)
    {
    	System.out.println("An input error has occurred. Demo10.");
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
   	JButton button2 = new JButton("Cancel");
   	button2.addActionListener(new ButtonListener());
   	buttonPanel2.add(button1, BorderLayout.LINE_START);
  	buttonPanel2.add(button2, BorderLayout.LINE_END);

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
  }
  
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
  }
  /**
   * Defines the geometry and presentation for a sample
   * graphic object.
   */   
  public class TestObject extends GObject
  {
    public GSegment segment_;
    
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
  }
  
   private class ButtonListener implements ActionListener
    {
    	
    	public void actionPerformed(ActionEvent e)
    	{
    		String junk = e.getActionCommand();
    		switch(junk)
    		{
    			/*On submit, collect user input from JFRame; use to create 
    			 *new ConditionReport object.  Push CR to current Item's 
    			 *stack of CRs. */
    			case "Submit": 
    				//TODO - no event registered if condition not changed "accretion"
    				System.out.println(selectedCondition);
    				
    				String notes = textArea.getText();
    					if(notes == null)
    						notes = "No notes provided";
    				
    				ConditionReport thisReport = new ConditionReport( selectedCondition,
    				"username", notes , new Date(), scene_);
    				//TODO - capture username as a string
    				//TODO - port into ItemGui
    				//TODO - after port, implement push to stack with method
    					//addConditionReport(ConditionReport report)
    				//TODO - VERIFY ALL INPUTS CAPTURED - HIGH
    				break;
    			case "Cancel": //close window without saving changes
    				dispose();
    				break;
    			default:
    				System.out.println("Something went wrong.");
    			
    		}//end switch
    	}//end actionPerformed()
    }//end ButtonListener

 /** ColorListener allows a user to select the color type to draw with
  * inside the condition report image area.
  * @author Jonathan
  */
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
   	}//end actionPerformed()
   }//end DamagesListener()
   
}