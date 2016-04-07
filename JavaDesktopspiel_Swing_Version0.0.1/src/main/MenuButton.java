package main;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MenuButton extends JButton {
	private int y_difference, button_width, button_height;
	private Window window;
	public MenuButton(String text, int y_difference, int button_width, int button_height, Window window){
		this.y_difference = y_difference;
		this.button_width = button_width;
		this.button_height = button_height;
		this.window = window;
		this.resetBounds();
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
	public int getYDifference(){
		return this.y_difference;
	}

	public void resetBounds(MenuButton[] list){
		for(int i = 0; i < list.length; i++){
			list[i].setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+list[i].y_difference, button_width, button_height);
		}
	}
	public void resetBounds(){
		this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+this.y_difference, button_width, button_height);
	}
	public void resetBounds(boolean lr){
		
	}
}
