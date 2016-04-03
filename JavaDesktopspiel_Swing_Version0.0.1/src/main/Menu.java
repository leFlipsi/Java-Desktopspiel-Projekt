package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import konstanten.*;

public class Menu {	
	Window window;
	private int button_width = 192;
	private int button_height = 32;
	private MenuButton[] start_buttons;
	private MenuButton[] option_buttons;
	private MenuSelection o_windowscaleList;
	private String[] o_scales = {
			"800x450",
			"1024x576",
			"1920x1080"
	};
	
	public Menu(Window window, String type) {
		start_buttons = new MenuButton[3];
		option_buttons = new MenuButton[3];
		this.window = window;
		setMenu(type);
	}
	
	public void setMenu(String type){
		initButtons();
		if(type == "start"){	
			hideButtons(option_buttons, 0);
			showButtons(start_buttons, 0);
		}
		if(type == "options"){	
			hideButtons(start_buttons, 0);
			showButtons(option_buttons, 0);
		}
		window.repaint();
	}
	public void initButtons(){
		for(int i = 0; i < start_buttons.length; i++){
			start_buttons[i] = new MenuButton(TextVars.start_button_text[i], (i*64)-64, button_width, button_height, window);
		}
		for(int i = 0; i < option_buttons.length; i++){
			option_buttons[i] = new MenuButton(TextVars.option_button_text[i], (i*64)-64, button_width, button_height, window);
		}
		o_windowscaleList = new MenuSelection(o_scales, -64, button_width, button_height, window);
	}
	public void hideButtons(MenuButton[] button_list, int start){
		for(int i = start; i < button_list.length; i++){
			button_list[i].setVisible(false);
		}
	}
	public void showButtons(MenuButton[] button_list, int start){
		for(int i = start; i < button_list.length; i++){
			button_list[i].setVisible(true);
		}
	}

	// EVENT LISTENERS
	
}