package main;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MenuShowtext extends JTextArea{
	private String[] loadText;
	private int width, height, y_difference, x_difference;
	private Window window;
	public MenuShowtext(String[] loadText, int field_width, int field_height, int y_difference, int x_difference, Window window){
		this.loadText = loadText;
		this.width = field_width;
		this.height = field_height;
		this.y_difference = y_difference;
		this.x_difference = x_difference;
		this.window = window;
		
		this.setText("Spielstand: " + loadText[0] + " | " + loadText[2]);
		this.setText("Charaktername: " + loadText[1]);
		
		this.setEditable(false);
	}
	
	public void resetBounds(){
		this.setBounds((window.getWidth()/2)-(this.width/2)-x_difference, (window.getHeight()/2)-(this.height/2)-y_difference, width, height);
	}
}
