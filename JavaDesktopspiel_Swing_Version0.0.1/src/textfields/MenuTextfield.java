package textfields;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

import main.Window;

/**
 * MenuTextfield Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MenuTextfield extends JTextField {
	private Window window;
	private int field_width, field_height, y_difference, border;
	
	/**
	 * Erstellt das Objekt und legt Standards fest
	 * 
	 * @param default_text Standart-Text
	 * @param y_difference Abstand
	 * @param field_width Breite
	 * @param field_height Höhe
	 * @param window Fensterobjekt
	 */
	public MenuTextfield(String default_text, int y_difference, int field_width, int field_height, Window window){
		super(default_text);
		this.window = window;
		this.y_difference = y_difference;
		this.field_width = field_width;
		this.field_height = field_height;
		this.border = 5;
		this.setCaretColor(Color.WHITE);
		this.resetBounds(this);
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(border,2*border,border,border)));
		window.add(this);
	}
	
	/**
	 * Legt die Bounds für eine Liste aus MenuTextfield-Objekten fest
	 * 
	 * @param field 
	 */
	public void resetBounds(MenuTextfield[] field){
		for(int i = 0; i < field.length; i++){
			resetBounds(field[i]);
		}
	}
	
	/**
	 * Legt die Bounds für ein einzelnes Objekt fest
	 * 
	 * @param field MenuTextfield Objekt
	 */
	public void resetBounds(MenuTextfield field){
		field.setBounds((window.getWidth()/2)-(field_width/2), (window.getHeight()/2)-(field_height/2)+field.y_difference, field_width, field_height);
	}
}
