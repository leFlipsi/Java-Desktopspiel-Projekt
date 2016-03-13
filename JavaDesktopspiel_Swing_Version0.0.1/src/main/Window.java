package main;

import javax.swing.JFrame;

public class Window extends JFrame{
	private int window_width = 1024;
	private int window_height = 576;
	
	public Window(String title){
		this.setTitle(title);
		this.setSize(this.window_width, this.window_height);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public int getWindowWidth(){
		return this.window_width;
	}
	public int getWindowHeight(){
		return this.window_height;
	}
	public void setWindowWidth(int w){
		this.window_width = w;
	}
	public void setWindowHeight(int h){
		this.window_height = h;
	}
}
