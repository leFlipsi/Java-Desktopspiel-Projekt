package image;

import java.util.ArrayList;

import main.Window;

public class LoadBackground {
	private ArrayList<LoadImage> images; 
	public LoadBackground(Window window, double[][] data){
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				try{
					images.add(new LoadImage(j*window.getWidth()/16, i*window.getHeight()/9, 64, (int)(data[i][j]%2) * 32, (int)Math.floor(data[i][j]/2)*32, 32, "test_sprite.jpg", window));
				}catch(Exception e){
					
				}
			}
		}
	}
}
