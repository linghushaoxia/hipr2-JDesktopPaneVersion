package net.joshuahughes.hipr2.lower;

import java.io.IOException;
import java.io.Serializable;
import java.io.StreamTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.joshuahughes.hipr2.upper.ConSmooth;


public class conservativeSmooth extends operator2DDouble implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
ConSmooth conSmoothOp = new ConSmooth();
  static int number=0;
  String type = new String("ConservativeSmoothing");

  public conservativeSmooth(){
  }

  public conservativeSmooth(JPanel panel, linkData links){
    ++number;
    setName(type+"_"+number);
    setParameters();
    setType(type);
    setBox(panel,links,2,1);
    box.getIn2().setText("K2");
  }
/**
   * Loads the required parameters (number of iterations)
   * from the input stream, so the operator can be recreated in an 
   * identical state to when it was saved.
   * @param tokenizer the input stream split into tokens
   * @throws IOException if error occurs during token retrieval
   */
  public void loadParameters(StreamTokenizer tokenizer) throws IOException{
    //int tokenType;
    //tokenType = tokenizer.nextToken();
    //int value = (int) tokenizer.nval;
    //to be continued..
  }
  /**
   * Returns a String representing all the parameters for this operator
   * for the purpose of saving the system setup (and being able to load
   * it again at a future date).
   */
  public String saveParameters(){
    String saveData = new String();
    
    //Add any parameters to the string
    
    return saveData;
  }
   void setParameters(){
    /**
     *This function is used to set up the parameters window. This window
     *should contain enough parameters to be able to run the operator
     *although parameters like scaling and offset are not required as there
     *is an operator already defined to do this. The interface components
     *should be added to the parameters frame. In this example a single panel
     *is created which is used to hold a label saying there are no paramters.
     *In general this will not be true of most operators.
     */
    parameters = new JFrame(name);
    //Change from here
    panel = new JPanel();
    JLabel label = new JLabel("No Parameters");
    panel.add(label);

    parameters.getContentPane().add(panel);
    //To here
    parameters.pack();
    parameters.setVisible(false);
  }

 
  public void go(){
    
    System.out.println(name);
    if(getInput1()!=null && getInput2()!=null) {
       if (getInput1().getWidth()>=getInput2().getWidth()
                && getInput1().getHeight()>=getInput2().getHeight()){

	   System.out.println("Input Width 1 "+getInput1().getWidth());
	   System.out.println("Input Width 2 "+getInput2().getWidth());
	   
	   try {
	       if(box.links.getLink(this,box.getIn2()).linkFrom.getType().startsWith("Kernel3x3TwoState")) {
		   output1 = 
		       new image2DDouble(input1.getWidth(),
					 input1.getHeight(),
					 conSmoothOp.smoothImage(input1.getValues(),
								 input1.getWidth(),
								 input1.getHeight(),
								 input2.getValues(),
								 input2.getWidth(),
								 input2.getHeight()));
		   
		   System.out.println("Output width "+getOutput1().getWidth());
		   
		   propagate();
	       }
	       else {
		   JOptionPane.showMessageDialog(null,("You should use a Kernel3x3TwoState"),("Error!"), JOptionPane.WARNING_MESSAGE);
	       }
	   } catch (NullPointerException e) {
	       JOptionPane.showMessageDialog(null,("You should use a Kernel3x3TwoState, not an image or output from an operator in K2"),("Error!"), JOptionPane.WARNING_MESSAGE);
	   }}
    }
  }
}
