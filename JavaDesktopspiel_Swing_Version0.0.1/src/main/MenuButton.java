package main;

import java.awt.Color;
import javax.swing.JButton;

public class MenuButton extends JButton{	
	public MenuButton(String text, int y_differenz, int button_width, int button_height, Window window){
		this.setText(text);
		this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+y_differenz, button_width, button_height);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		window.add(this);
	}
}
