package main;

import java.awt.Color;
import javax.swing.JSlider;

public class MenuSelection extends JSlider {
	public MenuSelection(String[] text, int y_difference, int button_width, int button_height, Window window){
		this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		this.setMinimum(0);
		this.setMaximum(text.length-1);
		this.setValue(0);
		this.setBackground(Color.DARK_GRAY);
		window.add(this);
	}
}
