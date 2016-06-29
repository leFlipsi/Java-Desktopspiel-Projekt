package buttons;

import java.awt.Color;
import javax.swing.JButton;
import main.Window;

/**
 * MenuButton Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MenuButton extends JButton {

	private int x, y, width, height, lr, y_difference;
	private Window window;
	
	/**
	 * Konstruktor - erstellt den Button
	 * 
	 * @param text Text der auf dem Button angezeigt wird
	 * @param y_difference abstand zu den anderen Buttons (in Y-Richtung)
	 * @param lr Werte: -1 für normalen Button, 0 oder 1 bei nebeneinander liegenden Buttons (0 links, 1 rechts), 2 für spezielle Buttons 
	 * @param button_width Breite des Buttons
	 * @param button_height Höhe des Buttons
	 * @param window Fensterobjekt
	 */
	public MenuButton(String text, int y_difference, int lr, int button_width, int button_height, Window window){
		this.lr = lr;
		this.setStandards(text, button_width, button_height, window, y_difference);
		this.setButtonBounds();
	}
	/**
	 * setStandards - Methode initialisiert Variablen standardgemäß
	 * 
	 * @param text Text der auf dem Button angezeigt wird
	 * @param button_width Breite des Buttons
	 * @param button_height Höhe des Buttons
	 * @param window Fensterobjekt
	 * @param y_difference abstand zu den anderen Buttons (in Y-Richtung)
	 */
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
	/**
	 * setPosition - Methode legt die Position des Buttons fest
	 */
	public void setPosition(){
		this.y = (window.getHeight()/2)-(height/2)+y_difference;
		if(lr == -1){
			this.x = (window.getWidth()/2)-(width/2);
		}else if(lr == 0){
			this.x = (window.getWidth()/2)-width;
		}else if(lr == 1){
			this.x = (window.getWidth()/2);
		}else if(lr == 2){
			this.x = (window.getWidth()/2)+width/2;
		}
	}
	/**
	 * setYPosition - Methode setzt die YPosition eines bestimmten Buttons fest.
	 * 
	 * @param i Zählvariable bei mehreren Buttons, die UNTEREINANDER angeordnet sind
	 */
	public void setYPosition(int i){
		this.y = y-y_difference*i;
	}
	/**
	 * setButtonBounds - Methode legt die Position und Größe fest
	 */
	public void setButtonBounds(){
		this.setPosition();
		this.setBounds(x, y, width, height);
	}
	/**
	 * setButtonBounds - Methode legt die Position und Größe für eine LISTE von Buttons fest
	 * 
	 * @param list Liste von MenuButton-Instanzen
	 */
	public void setButtonBounds(MenuButton[] list){
		for(int i = 0; i < list.length; i++){
			list[i].setPosition();
			if(list[i].lr != 0 && list[i].lr != 1){
				list[i].setYPosition(i);
				list[i].setBounds(list[i].x, y-y_difference*i, list[i].width, list[i].height);
			}else{
				list[i].setBounds(list[i].x, list[i].y, list[i].width, list[i].height);
			}
			
		}
	}
}
