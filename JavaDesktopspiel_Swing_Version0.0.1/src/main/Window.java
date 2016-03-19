package main;

import javax.swing.JFrame;

public class Window extends JFrame{
	private int window_width = 1024;
	private int window_height = 576;
	
	public Window(String title){
		this.setTitle(title);
		this.updateWindowSize();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public void setSize(String size){
		if(size == "1024x576"){
			this.window_width = 1024;
			this.window_height = 576;
		}
		if(size == "800x450"){
			this.window_width = 800;
			this.window_height = 450;
		}
		if(size == "1920x1080"){
			this.window_width = 1920;
			this.window_height = 1080;
		}
		this.updateWindowSize();
	}
	
	public void updateWindowSize(){
		this.setSize(window_width, window_height);
	}
	
	public int getWindowWidth(){
		return this.window_width;
	}
	public int getWindowHeight(){
		return this.window_height;
	}
}
