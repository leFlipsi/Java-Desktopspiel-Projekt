package main;

import konstanten.*;

import java.awt.Color;

import javax.swing.*;

public class Menu {
	private int button_width = 128;
	private int button_height = 32;
	
	private MenuButton b_creategame;
	private MenuButton b_loadgame;
	private MenuButton b_options;
	
	
	public Menu(Window window, String type){
		if(type == "start"){	
			b_creategame = new MenuButton(TextVars.menu_creategame, -64, window);
			b_loadgame = new MenuButton(TextVars.menu_loadgame, 0, window);
			b_options = new MenuButton(TextVars.menu_options, 64, window);
		}
	}
}
