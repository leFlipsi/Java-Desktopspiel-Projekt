package main;

import java.awt.Color;

import javax.swing.JButton;

public class MenuButton extends JButton {
	private int button_width_s;
	public MenuButton(String text, int y_difference, int button_width, int button_height, Window window){
		this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		this.setStandards(text, window);
	}
	public MenuButton(String text, int y_difference, int lr, int button_width, int button_height, Window window){
		if(lr == 0){
			this.setBounds((window.getWidth()/2)-(button_width/2)-button_width/2, (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		}else{
			this.setBounds((window.getWidth()/2)-(button_width/2)+button_width/2, (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		}
		this.setStandards(text, window);
	}
	private void setStandards(String text, Window window){
		this.setText(text);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		//this.setOpaque(false);
		//this.setContentAreaFilled(false);
		window.add(this);
	}
}
