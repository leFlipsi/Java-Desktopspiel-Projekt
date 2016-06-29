package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import konstanten.TextVars;

/**
 * Window Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Window extends JFrame {
	private int window_width = 1024;
	private int window_height = 576;
	private Menu menu;

	/**
	 * Konstruktor - Erstellt das Fenster
	 * 
	 * @param title Fenstertitel
	 */
	public Window(String title) {
		this.setResizable(false);
		this.setTitle(title);
		this.updateWindowSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		setMenu(new Menu(this, "start"));
	}

	/**
	 * Ändert die Werte für die Breite und Höhe -> NICHT die Breite und Höhe selbst
	 * 
	 * @param w Breite
	 * @param h Höhe
	 */
	public void resetWindowScale(int w, int h) {
		this.window_width = w;
		this.window_height = h;
	}

	/**
	 * Nimmt die Werte für die Breite und Höhe und wendet sie an!
	 */
	public void updateWindowSize() {
		this.setSize(window_width, window_height);
	}

	/**
	 * Setzt die Werte für die Breite und Höhe auf die Momentanen Abmessungen zurück
	 */
	public void keepWindowSize() {
		this.window_width = this.getWidth();
		this.window_height = this.getHeight();
	}

	/**
	 * Startmethode -> RUN
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window window = new Window(TextVars.window_title);
			}
		});
	}

	/**
	 * @return Menu-Objekt
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu legt das Menü-Objekt fest
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
