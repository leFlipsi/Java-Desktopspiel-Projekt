package buttons;

import java.awt.Color;
import javax.swing.JButton;
import main.Window;

@SuppressWarnings("serial")
public class MenuButton extends JButton {
	private int x, y, width, height, lr, y_difference;
	private Window window;
	
	public MenuButton(String text, int y_difference, int button_width, int button_height, Window window){
		this.lr = -1;
		this.setStandards(text, button_width, button_height, window, y_difference);
		this.setButtonBounds();
	}
	public MenuButton(String text, int y_difference, int lr, int button_width, int button_height, Window window){
		this.lr = lr;
		this.setStandards(text, button_width, button_height, window, y_difference);
		this.setButtonBounds();
	}
	private void setStandards(String text, int button_width, int button_height, Window window, int y_difference){
		this.y_difference = y_difference;
		this.window = window;
		this.width = button_width;
		this.height = button_height;
		this.setText(text);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		//this.setOpaque(false);
		//this.setContentAreaFilled(false);
		window.add(this);
	}
	public void setPosition(){
		if(lr == -1){
			this.x = (window.getWidth()/2)-(width/2);
		}else if(lr == 0){
			this.x = (window.getWidth()/2)-(width/2)-width/2;
		}else if(lr == 1){
			this.x = (window.getWidth()/2)-(width/2)+width/2;
		}
		this.y = (window.getHeight()/2)-(height/2)+y_difference;
	}
	public void setYPosition(int i){
		this.y = y-y_difference*i;
	}
	public void setButtonBounds(){
		this.setPosition();
		this.setBounds(x, y, width, height);
	}
	public void setButtonBounds(MenuButton[] list){
		for(int i = 0; i < list.length; i++){
			list[i].setPosition();
			list[i].setYPosition(i);
			list[i].setBounds(x, y-y_difference*i, width, height);
		}
	}
}
