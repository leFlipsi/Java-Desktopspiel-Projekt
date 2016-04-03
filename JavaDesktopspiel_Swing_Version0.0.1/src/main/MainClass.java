package main;

import javax.swing.SwingUtilities;

import konstanten.TextVars;

public class MainClass {
	static Window main_window;
	static Menu menu;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
	        	start();
			}
		});
	}
	static void start(){
		main_window = new Window(TextVars.window_title);
		loadMenu("start");
	}
	static void loadMenu(String menu_status){
		menu = new Menu(main_window, menu_status);
	}
}