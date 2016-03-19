package main;

import konstanten.*;

public class Menu {	
	
	private int button_width = 192;
	private int button_height = 32;
	
	private MenuButton s_creategame;
	private MenuButton s_loadgame;
	private MenuButton s_options;
	
	private MenuSelection o_windowscale;
	private String[] o_scales = {
			"800x450",
			"1024x576",
			"1920x1080"
	};
	private MenuButton o_apply;
	private MenuButton o_back;
	
	public Menu(Window window, String type){
		if(type == "start"){	
			s_creategame = new MenuButton(TextVars.menu_creategame, -64, button_width, button_height, window);
			s_loadgame = new MenuButton(TextVars.menu_loadgame, 0, button_width, button_height, window);
			s_options = new MenuButton(TextVars.menu_options, 64, button_width, button_height, window);
		}
		if(type == "options"){	
			// o_windowscale = new MenuSelection(o_scales, -64, button_width, button_height, window);
			o_windowscale = new MenuSelection(o_scales, -64, button_width, button_height, window);
			o_apply = new MenuButton(TextVars.menu_loadgame, 0, button_width, button_height, window);
			o_back = new MenuButton(TextVars.menu_options, 64, button_width, button_height, window);
		}
	}
}