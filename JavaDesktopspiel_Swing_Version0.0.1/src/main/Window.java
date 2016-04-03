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
	
	/* ZU JComboBox MenuSelection
	public void setSizeVariables(String size){
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
	}
	*/
	
	
	public void resetWindowScale(int w, int h){
		this.window_width = w;
		this.window_height = h;
	}
	public void updateWindowSize(){
		this.setSize(window_width, window_height);
	}
	public void keepWindowSize(){
		this.window_width = this.getWidth();
		this.window_height = this.getHeight();
	}
}
