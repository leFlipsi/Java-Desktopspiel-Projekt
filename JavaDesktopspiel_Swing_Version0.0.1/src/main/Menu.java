package main;

import javax.swing.JComboBox;
import konstanten.*;

public class Menu {	
	private MenuButton b_creategame;
	private MenuButton b_loadgame;
	private MenuButton b_options;
	
	//TODO
	private MenuButton b_apply;
	private MenuButton b_back;
	
	public Menu(Window window, String type){
		if(type == "start"){	
			b_creategame = new MenuButton(TextVars.menu_creategame, -64, window);
			b_loadgame = new MenuButton(TextVars.menu_loadgame, 0, window);
			b_options = new MenuButton(TextVars.menu_options, 64, window);
		}
		if(type == "options"){	
			// TODO
			b_apply = new MenuButton(TextVars.menu_loadgame, 0, window);
			b_back = new MenuButton(TextVars.menu_options, 64, window);
		}
	}
}
