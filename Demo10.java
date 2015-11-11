import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
//import javax.swing.AbstractButton;
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
//  static variables for use with BorderBagLayout
  final static boolean shouldFill = true;
  final static boolean shouldWeightX = true;
  final static boolean RIGHT_TO_LEFT = false;
  private static int numberOfConditions = 62 ;//supplied by client
  public static Color ink = new Color(255,0,0);

   
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
    GObject object = new TestObject (imageFile);
    scene_.add (object);
    
    //Create a JPanel for condition pulldown and Radio Buttons
    JPanel inputPanel = new JPanel(new BorderLayout());
    
   	JPanel menuPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    JRadioButton redRadio = new JRadioButton("red", true);
  	redRadio.addActionListener(new ButtonListener());
    JRadioButton yellowRadio = new JRadioButton("yellow");
 	yellowRadio.addActionListener(new ButtonListener());
    JRadioButton greenRadio = new JRadioButton("green");
  	greenRadio.addActionListener(new ButtonListener());
   	ButtonGroup myButtons = new ButtonGroup();
   	myButtons.add(redRadio);
    myButtons.add(yellowRadio);
    myButtons.add(greenRadio);
    
    
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
    buttonPanel.add(redRadio);
    buttonPanel.add(yellowRadio);
    buttonPanel.add(greenRadio);

    //TODO add event for changing markup color {red, yellow, green}
     
     
    //Read in damage codes from DamageCodes.txt, populate to an array, display array
    //as a JComboBox.
    String [] conditions = new String[numberOfConditions];
    try{
    	Scanner input = new Scanner(new File ("DamageCodes.txt"));
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
   // inputPanel.add(menuPanel, BorderLayout.PAGE_START);
    
    JPanel textPanel = new JPanel();
   	textPanel.add(new JLabel("Enter damage remarks here."), BorderLayout.PAGE_START);
   	JTextArea textarea = new JTextArea(5, 40);
   	textPanel.add(textarea);

   	
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
  private class TestObject extends GObject
  {
    private GSegment segment_;
    
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
   	  routeStyle.setForegroundColor (new Color (255, 0, 0));
	  routeStyle.setForegroundColor(ink);
      routeStyle.setLineWidth (4);
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
    			case "green":
    				System.out.println("Green event fired");
    				ink = new Color(0, 255, 0);
    				break;
    			case "red":
    				System.out.println("Red event fired");
    				ink = new Color(255, 0, 0);
    				break;
    			case "yellow":
    				System.out.println("Yellow event fired");
    				ink = new Color(255, 255, 0);
    				break;
    			case "Submit":
    				System.out.println("Submit event fired");
    				//TODO add a method to capture data from condition codes, text area, sysemt
    				//user and date
    				break;
    			case "Cancel":
    				System.out.println("Cancel event fired");
    				dispose();
    				//dispose of CR window
    				break;
    			default:
    				System.out.println("Someting Wong");
    			
    		}//end switch
    	}//end actionPerformed()
    }//end colorListener

}
