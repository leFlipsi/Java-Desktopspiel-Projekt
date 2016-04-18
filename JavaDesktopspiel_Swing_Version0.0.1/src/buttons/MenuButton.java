package buttons;

import java.awt.Color;
import javax.swing.JButton;

import main.Window;

@SuppressWarnings("serial")
public class MenuButton extends JButton {
	private int y_difference, button_width, button_height, lr;
	private Window window;
	
	public MenuButton(String text, int y_difference, int button_width, int button_height, Window window){
		this.lr = -1;
		this.setPrivateVars(y_difference, button_width, button_height, window);
		this.resetBounds();
		this.setStandards(text, window);
	}
	public MenuButton(String text, int y_difference, int lr, int button_width, int button_height, Window window){
		this.lr = lr;
		this.setPrivateVars(y_difference, button_width, button_height, window);
		this.resetBounds();
		this.setStandards(text, window);
	}
	public void setPrivateVars(int y_difference, int button_width, int button_height, Window window){
		this.y_difference = y_difference;
		this.button_width = button_width;
		this.button_height = button_height;
		this.window = window;
	}
	private void setStandards(String text, Window window){
		this.setText(text);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		//this.setOpaque(false);
		//this.setContentAreaFilled(false);
		window.add(this);
	}

	public void resetBounds(MenuButton[] list){		//Reset MenuButton list bounds
		for(int i = 0; i < list.length; i++){
			if(lr == -1){
				list[i].setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+list[i].y_difference, button_width, button_height);
			}else{
				resetBoundsHalfButtons(list[i]);
			}
		}
	}
	public void resetBounds(){						//Reset single MenuButton bounds
		if(lr == -1){
			this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+this.y_difference, button_width, button_height);
		}else{
			resetBoundsHalfButtons(this);
		}
	}
	public void resetBoundsHalfButtons(MenuButton button){
		if(button.lr == 0){
			button.setBounds((window.getWidth()/2)-(button_width/2)-button_width/2, (window.getHeight()/2)-(button_height/2)+button.y_difference, button_width, button_height);
		}else if(button.lr == 1){
			button.setBounds((window.getWidth()/2)-(button_width/2)+button_width/2, (window.getHeight()/2)-(button_height/2)+button.y_difference, button_width, button_height);
		}
	}
}