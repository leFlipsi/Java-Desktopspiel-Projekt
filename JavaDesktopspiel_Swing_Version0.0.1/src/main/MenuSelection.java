package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import konstanten.TextVars;

public class MenuSelection implements TextVars, ActionListener{
	
	private MenuButton[] list;
	private Window window;
	private Menu menu;
	private int y_difference, button_width, button_height;
	
	public MenuSelection(String[] text, int y_difference, int button_width, int button_height, Window window, Menu menu){
		this.window = window;
		this.menu = menu;
		this.y_difference = y_difference;
		this.button_width = button_width;
		this.button_height = button_height;
		list = new MenuButton[text.length];
		for(int i = 0; i < text.length; i++){
			list[i] = new MenuButton(text[i], y_difference+(3+i)+((i+1)*button_height), button_width, button_height, window);
			list[i].addActionListener(this);
		}
		hideList();
	}
	
	public void showList(){
		for(int i = 0; i < list.length; i++){
			list[i].setVisible(true);
		}
	}
	public void hideList(){
		for(int i = 0; i < list.length; i++){
			list[i].setVisible(false);
		}
	}
	
	public void resetBounds(){
		for(int i = 0; i < list.length; i++){
			list[i].setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+(this.y_difference+(3+i)+((i+1)*button_height)), button_width, button_height);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == list[0]){
			window.resetWindowScale(800, 450);
		}else if(e.getSource() == list[1]){
			window.resetWindowScale(1024, 576);
		}else if(e.getSource() == list[2]){
			window.resetWindowScale(1920, 1080);
		}
		this.hideList();
		menu.setMenu("options");
	}
}
