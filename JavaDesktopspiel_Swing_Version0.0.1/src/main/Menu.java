package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import konstanten.*;

public class Menu implements ActionListener{	
	Window window;
	private int button_width = 192;
	private int button_height = 32;
	private MenuButton[] start_buttons;
	private MenuButton[] option_buttons;
	private MenuButton[] create_buttons;
	private MenuTextfield[] create_fields;
	private MenuSelection o_windowscaleList;
	private String[] o_scales = {
			"800x450",
			"1024x576",
			"1920x1080"
	};
	
	public Menu(Window window, String type) {
		start_buttons = new MenuButton[3];
		option_buttons = new MenuButton[3];
		create_buttons = new MenuButton[2];
		create_fields = new MenuTextfield[2];
		this.window = window;
		initButtons();
		setMenu(type);
	}
	
	public void setMenu(String type){
		hideButtons(option_buttons, 0);
		hideButtons(start_buttons, 0);
		hideButtons(create_buttons, 0);
		hideTextfields(create_fields);
		if(type == "start"){	
			showButtons(start_buttons, 0);
		}else if(type == "options"){	
			showButtons(option_buttons, 0);
		}else if(type == "create_game"){
			showButtons(create_buttons, 0);
			showTextfields(create_fields);
		}
		window.repaint();
	}
	public void initButtons(){
		for(int i = 0; i < start_buttons.length; i++){
			start_buttons[i] = new MenuButton(TextVars.start_button_text[i], (i*64)-64, button_width, button_height, window);
			start_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < option_buttons.length; i++){
			option_buttons[i] = new MenuButton(TextVars.option_button_text[i], (i*64)-64, button_width, button_height, window);
			option_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_buttons.length; i++){
			create_buttons[i] = new MenuButton(TextVars.create_button_text[i], ((create_fields.length)*64)-64, i, button_width/2, button_height, window);
			create_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_fields.length; i++){
			create_fields[i] = new MenuTextfield(TextVars.create_fields_text[i], (i*64)-64, button_width, button_height, window);
		}
		o_windowscaleList = new MenuSelection(o_scales, -64, button_width, button_height, window, this);
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
	public void hideTextfields(MenuTextfield[] field_list){
		for(int i = 0; i < field_list.length; i++){
			field_list[i].setVisible(false);
		}
	}
	public void showTextfields(MenuTextfield[] field_list){
		for(int i = 0; i < field_list.length; i++){
			field_list[i].setVisible(true);
		}
	}

	// EVENT LISTENERS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.start_buttons[0]){				//Spielstand ERSTELLEN
			setMenu("create_game");
        }else if(e.getSource() == this.start_buttons[1]){		//Spielstand LADEN
        	//TODO
        }else if(e.getSource() == this.start_buttons[2]){		//Optionen
            setMenu("options");
        }	
		if(e.getSource() == this.option_buttons[0]){			//Fenstergröße:
			hideButtons(option_buttons, 1);
			o_windowscaleList.showList();
        }else if(e.getSource() == this.option_buttons[1]){		//Anwenden
        	window.updateWindowSize();
        	this.hideButtons(option_buttons, 0); 										//TODO WAS IST DA LOS?
        	this.initButtons();
        	this.setMenu("start");
        }else if(e.getSource() == this.option_buttons[2]){		//Zurück
            window.keepWindowSize();
            setMenu("start");
        }
		if(e.getSource() == this.create_buttons[0]){			//Fenstergröße:
			//TODO
        }else if(e.getSource() == this.create_buttons[1]){		//Anwenden
        	setMenu("start");
        }
	}
}