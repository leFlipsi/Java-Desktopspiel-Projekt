package main;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MenuTextfield extends JTextField {
	public MenuTextfield(String default_text, int y_difference, int field_width, int field_height, Window window){
		super(default_text);
		this.setBounds((window.getWidth()/2)-(field_width/2), (window.getHeight()/2)-(field_height/2)+y_difference, field_width, field_height);
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5,5,5,5)));
		window.add(this);
	}
}
