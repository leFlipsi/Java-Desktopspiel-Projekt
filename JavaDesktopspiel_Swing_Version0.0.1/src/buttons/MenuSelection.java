package buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import konstanten.TextVars;
import main.Menu;
import main.Window;

/**
 * MenuSelection Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class MenuSelection implements TextVars, ActionListener{
	
	private MenuButton[] list;
	private Window window;
	private Menu menu;
	private int y_difference, button_width, button_height;
	
	/**
	 * Konstruktor - legt Standards fest / erstellt die Auswahl
	 * 
	 * @param text Text der auf dem Button angezeigt wird
	 * @param y_difference abstand zu den anderen Auswahlmöglichkeiten (in Y-Richtung)
	 * @param button_width Breite des Buttons
	 * @param button_height Höhe des Buttons
	 * @param window Fensterobjekt
	 * @param menu Menüobjekt
	 */
	public MenuSelection(String[] text, int y_difference, int button_width, int button_height, Window window, Menu menu){
		this.window = window;
		this.menu = menu;
		this.y_difference = y_difference;
		this.button_width = button_width;
		this.button_height = button_height;
		list = new MenuButton[text.length];
		for(int i = 0; i < text.length; i++){
			list[i] = new MenuButton(text[i], y_difference+(3+i)+((i+1)*button_height), -1, button_width, button_height, window);
			list[i].addActionListener(this);
		}
		hideList();
	}
	
	/**
	 * showList - Methode zeigt die komplette Auswahl
	 */
	public void showList(){
		for(int i = 0; i < list.length; i++){
			list[i].setVisible(true);
		}
	}
	/**
	 * hideList - Methode versteckt die komplette Auswahl
	 */
	public void hideList(){
		for(int i = 0; i < list.length; i++){
			list[i].setVisible(false);
		}
	}
	
	/**
	 * resetBounds - Methode positioniert die Auswahl neu
	 */
	public void resetBounds(){
		for(int i = 0; i < list.length; i++){
			list[i].setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+(this.y_difference+(3+i)+((i+1)*button_height)), button_width, button_height);
		}
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == list[0]){
			window.resetWindowScale(800, 450);
		}else if(e.getSource() == list[1]){
			window.resetWindowScale(1024, 576);
		}else if(e.getSource() == list[2]){
			window.resetWindowScale(1920, 1080);
		}
		this.hideList();
		menu.setMenu("options");
	}
}
