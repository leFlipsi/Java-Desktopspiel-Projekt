package textfields;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import main.Window;

@SuppressWarnings("serial")
public class MenuShowtext extends JTextArea{
	private int width, height, x, y, border_y, difference, i;
	private Window window;
	public MenuShowtext(String spielstand, String charakter, String id, int i, int field_width, int field_height, int difference, Window window){
		this.width = field_width;
		this.height = field_height;
		this.border_y = 8;
		this.window = window;
		this.difference = difference;
		this.i = i;
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(border_y,5,5,5)));
		this.setText("Spielstand: " + id + " - " + spielstand + "\n\nCharaktername: " + charakter);
		this.setEditable(false);
		this.resetBounds();
		window.add(this);
	}
	public void setPosition(){
		this.x = (window.getWidth()/2)-(this.width/2)+difference-2*border_y;
		this.y = (window.getHeight()/2)-(this.height/2)-difference*(i-1)-border_y*2;
	}
	public void resetBounds(){
		this.setPosition();
		this.setBounds(this.x, this.y, width, height);
	}
}
