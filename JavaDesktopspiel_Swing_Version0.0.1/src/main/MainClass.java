package main;

import konstanten.TextVars;

public class MainClass {
	private Window main_window;
	private Menu menu;
	
	public static void main(String[] args) {
		MainClass start = new MainClass();
	}
	public MainClass(){
		main_window = new Window(TextVars.window_title);
		loadMenu("options");
	}
	public void loadMenu(String menu_status){
		menu = new Menu(main_window, menu_status);
	}
}