package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import image.LoadBackground;
import konstanten.TextVars;
import main.Menu;
import main.Window;

/**
 * GameControl-Klasse
 * 
 * @author Philipp Röhlicke, Tim Ziegelbauer, Cedric Röhr
 * @version 1.0
 */
public class GameControl extends Canvas implements Runnable, KeyListener {
	private boolean active, way_h_free, way_v_free, e_pressed;
	private String id, charaktername, spielstandname, item_status;
	private int player_pos_x, player_pos_y, player_rotation, width, running, bg_border_x, bg_border_y, locx, locy;
	private double bg_unwalkable, rechts, links, unten, oben;
	private Window window;
	private Charakter[] charakter;
	private Inventar inventar;
	private ArrayList<Item> inv_items;
	private LoadBackground bg;
	private int[] walking, lastActive;
	private Thread game;
	private Menu menu;

	/**
	 * Konstruktor Erstellt das Spiel und legt Standards fest
	 * 
	 * @param window Fensterobjekt
	 * @param menu Menüobjekt
	 */
	public GameControl(Window window, Menu menu) {
		this.menu = menu;
		Dimension dim = new Dimension(window.getWidth(), window.getHeight());
		inv_items = new ArrayList<Item>();
		inv_items.add(new Item(window, 16));
		inv_items.get(0).setVisible(false);
		inv_items.add(new Item(window, 32));
		inv_items.get(1).setVisible(false);
		this.walking = new int[4];
		this.walking[0] = 0;
		this.walking[1] = 0;
		this.walking[2] = 0;
		this.walking[3] = 0;
		this.lastActive = new int[2];
		this.lastActive[0] = 0;
		this.lastActive[1] = 0;
		this.running = 1;
		this.setPreferredSize(dim);
		this.active = false;
		this.window = window;
		this.bg_border_x = 8;
		this.bg_border_y = 4;
		this.bg_unwalkable = 32.0;
		this.width = 64;
		charakter = new Charakter[20];
		for (int i = 0; i < 20; i++) {
			charakter[i] = new Charakter(window, (i % 10) * 16, (i / 10) * 16);
			charakter[i].setVisible(false);
		}
		inventar = new Inventar(window);
		inventar.setVisible(false);
	}

	/**
	 * setActive - Methode, wird ausgeführt, wenn ein Spiel geladen oder
	 * erstellt wird
	 * 
	 * @param id abgespeicherte ID des Spielstandes
	 * @param charaktername	abgespeicherter Charaktername
	 * @param spielstandname abgespeicherter Spielstandname
	 * @param item_status abgespeicherter Item-Status
	 * @param player_x X-Position des Spielers
	 * @param player_y Y-Position des Spielers
	 * @param player_rotation Rotation des Spielers
	 * @param bg Hintergrundobjekt
	 */
	public void setActive(String id, String charaktername, String spielstandname, String item_status, int player_x,
			int player_y, int player_rotation, LoadBackground bg) {
		this.bg = bg;
		bg.resetPosition(-32 - 256, -128);
		this.id = id;
		this.charaktername = charaktername;
		this.spielstandname = spielstandname;
		this.item_status = item_status;
		this.player_pos_x = player_x;
		this.player_pos_y = player_y;
		this.player_rotation = player_rotation;
		this.charakter[0].setVisible(true);
		this.inventar.setVisible(true);
		this.addKeyListener(this);
		this.setFocusable(true);
		window.setTitle(TextVars.window_title + " | Charakter: " + charaktername + " | Spielstand: " + spielstandname);
	}

	/**
	 * start - Methode, startet den "game"-Thread
	 */
	public synchronized void start() {
		this.active = true;
		game = new Thread(this, "Game");
		game.start();
	}

	/**
	 * stop - Methode, stopt den "game"-Thread
	 */
	public synchronized void stop() {
		this.active = false;
		try {
			game.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 120.0;
		double delta = 0;

		int updates = 0;
		int renders = 0;

		while (active) {
			update();
			updates++;
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				render();
				renders++;
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.setTitle(TextVars.window_title + " | Spielstand: " + id + " | Charakter: " + charaktername
						+ " | Spielstand: " + spielstandname + " | FPS: " + renders + " | UPS: " + updates);
				renders = 0;
				updates = 0;
			}
		}
	}

	/**
	 * render - Methode, wird 120x/Sekunde von der run-Methode aufgerufen
	 * 
	 * @see #run()
	 */
	private void render() {
		locx = bg.getLocation().x;
		locy = bg.getLocation().y;
		nextStepBackground();
		walkingXCheckY();
		walkingYCheckX();
		freeWay();
		if (!e_pressed) {
			if (way_h_free && way_v_free) {
				if (walking[2] - walking[3] != 0 && walking[0] - walking[1] != 0
						&& (bg.getLocation().x - 32) % width == 0 && (bg.getLocation().y % width) == 0) {
					bg.resetPosition(running * (walking[2] - walking[3]), 0);
				} else {
					bg.resetPosition(running * (walking[2] - walking[3]), running * (walking[0] - walking[1]));
				}
			} else if (way_v_free) {
				bg.resetPosition(0, running * (walking[0] - walking[1]));
			} else if (way_h_free) {
				bg.resetPosition(running * (walking[2] - walking[3]), 0);
			}
		}
		finishStep();
	}

	/**
	 * update - Methode wird so oft wie möglich bei laufendem Spiel von der
	 * run-Methode aufgerufen
	 * 
	 * @see #run()
	 */
	private void update() {
		animation();
		nextStepBackground();
		walkingXCheckY();
		walkingYCheckX();
		freeWay();
	}

	/**
	 * nextStepBackground - Methode erkennt die Hintergrunddaten (double) für
	 * die Hintergrundfelder direkt neben dem Charakter
	 */
	public void nextStepBackground() {
		rechts = bg.getData(bg_border_y + player_pos_y, bg_border_x + player_pos_x + 1);
		links = bg.getData(bg_border_y + player_pos_y, bg_border_x + player_pos_x - 1);
		unten = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x);
		oben = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x);
	}

	/**
	 * walkingXCheckY - Methode erkennt die Hintergrunddaten (double) für die
	 * Hintergrundfelder direkt über und unter dem Charakter, wenn der Charakter
	 * sich gerade bewegt, sich dementsprechend also zwischen 2
	 * Hintergrundfeldern (horizontal) befindet
	 */
	public void walkingXCheckY() {
		if (locx % width == -32) {
			player_pos_x = -1 * (locx + 32) / width;
		} else {
			if (-1 * (locx - 32) / width > player_pos_x) {
				if (unten < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1)) {
					unten = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1);
				}
				if (oben < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1)) {
					oben = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1);
				}
			} else if (-1 * (locx + 32) / width < player_pos_x) {
				if (unten < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1)) {
					unten = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1);
				}
				if (oben < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1)) {
					oben = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1);
				}
			}
		}
	}

	/**
	 * walkingYCheckX - Methode erkennt die Hintergrunddaten (double) für die
	 * Hintergrundfelder direkt links und rechts vom Charakter, wenn der
	 * Charakter sich gerade bewegt, sich dementsprechend also zwischen 2
	 * Hintergrundfeldern (vertikal) befindet
	 */
	public void walkingYCheckX() {
		if (locy % width == 0) {
			player_pos_y = -1 * locy / width;
		} else {
			if (-1 * locy / width < player_pos_y) {
				if (rechts < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1)) {
					rechts = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x + 1);
				}
				if (links < bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1)) {
					links = bg.getData(bg_border_y + player_pos_y - 1, bg_border_x + player_pos_x - 1);
				}
			} else if (-1 * locy / width + 1 > player_pos_y) {
				if (rechts < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1)) {
					rechts = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x + 1);
				}
				if (links < bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1)) {
					links = bg.getData(bg_border_y + player_pos_y + 1, bg_border_x + player_pos_x - 1);
				}

			}
		}
	}

	/**
	 * freeWay - Methode hinterlegt in Variablen, ob der Charakter sich
	 * horizontal/vertikal bewegen darf
	 */
	public void freeWay() {
		if (((locx >= -32 || links >= bg_unwalkable) && walking[2] > 0) || rechts >= bg_unwalkable && walking[3] > 0) {
			way_h_free = false;
		} else {
			way_h_free = true;
		}
		if (((locy >= 0 || oben >= bg_unwalkable) && walking[0] > 0) || unten >= bg_unwalkable && walking[1] > 0) {
			way_v_free = false;
		} else {
			way_v_free = true;
		}
	}

	/**
	 * finishStep - Methode beendet die Bewegung, wenn der Charakter sich
	 * zwischen Hintergrundfeldern befindet und die Bewegungstasten losgelassen
	 * wurden
	 */
	public void finishStep() {
		if (-1 * locx % width != 32 && walking[2] - walking[3] == 0) {
			if (lastActive[1] == 1) {
				bg.resetPosition((lastActive[1] - walking[3]), 0);
			} else if (lastActive[1] == -1) {
				bg.resetPosition((walking[2] + lastActive[1]), 0);
			}
		} else {
			lastActive[1] = 0;
		}
		if (locy % width != 0 && walking[0] - walking[1] == 0) {
			if (lastActive[0] == 1) {
				bg.resetPosition(0, (lastActive[0] - walking[1]));
			} else if (lastActive[0] == -1) {
				bg.resetPosition(0, (walking[0] + lastActive[0]));
			}
		} else {
			lastActive[0] = 0;
		}
	}

	/**
	 * animation - Methode erkennt ob der Spieler sich gerade bewegt oder nicht
	 * bei keiner Bewegung werden die standbilder des Charakters sichtbar bei
	 * Bewegung werden die Animations-Methoden aufgerufen
	 * 
	 * @see #walkAnimationH()
	 * @see #walkAnimationV()
	 */
	public void animation() {
		if (walking[0] == 0 && walking[1] == 0 && walking[2] == 0 && walking[3] == 0 && lastActive[1] == 0
				&& lastActive[0] == 0) {
			if (this.player_rotation == 0) {
				animationVisibility(15);
			} else if (this.player_rotation == 2) {
				animationVisibility(14);
			} else if (this.player_rotation == 1) {
				animationVisibility(5);
			} else if (this.player_rotation == 3) {
				animationVisibility(4);
			}
		} else {
			walkAnimationH();
			walkAnimationV();
		}
	}

	/**
	 * walkAnimationH - Methode lädt die Animationsbilder bei horizontaler
	 * Bewegung bei Bewegung in horizontaler und vertikaler Richtung ist die
	 * Animation für die horizontale Bewegung dominant
	 */
	public void walkAnimationH() {
		boolean links = walking[2] == 1
				|| (walking[3] == 0 && lastActive[1] == 1 && walking[0] == 0 && walking[1] == 0);
		boolean rechts = walking[3] == 1
				|| (walking[2] == 0 && lastActive[1] == -1 && walking[0] == 0 && walking[1] == 0);
		if (0 <= -1 * ((locx + 32) % width) && -1 * ((locx + 32) % width) <= 15) {
			if (links)
				animationVisibility(0);
			if (rechts)
				animationVisibility(6);
		} else if (16 <= -1 * ((locx + 32) % width) && -1 * ((locx + 32) % width) <= 31) {
			if (links)
				animationVisibility(1);
			if (rechts)
				animationVisibility(7);
		} else if (32 <= -1 * ((locx + 32) % width) && -1 * ((locx + 32) % width) <= 47) {
			if (links)
				animationVisibility(2);
			if (rechts)
				animationVisibility(8);
		} else if (48 <= -1 * ((locx + 32) % width) && -1 * ((locx + 32) % width) <= width) {
			if (links)
				animationVisibility(3);
			if (rechts)
				animationVisibility(9);
		}
	}

	/**
	 * walkAnimationV - Methode lädt die Animationsbilder bei vertikaler
	 * Bewegung
	 */
	public void walkAnimationV() {
		boolean hoch = (walking[0] == 1);
		boolean runter = (walking[1] == 1);
		if (walking[2] == 0 && walking[3] == 0) {
			if (0 <= -1 * (locy % width) && -1 * (locy % width) <= 15) {
				if (runter)
					animationVisibility(10);
				if (hoch)
					animationVisibility(16);
			} else if (16 <= -1 * (locy % width) && -1 * (locy % width) <= 31) {
				if (runter)
					animationVisibility(11);
				if (hoch)
					animationVisibility(17);
			} else if (32 <= -1 * (locy % width) && -1 * (locy % width) <= 47) {
				if (runter)
					animationVisibility(12);
				if (hoch)
					animationVisibility(18);
			} else if (48 <= -1 * (locy % width) && -1 * (locy % width) <= width) {
				if (runter)
					animationVisibility(13);
				if (hoch)
					animationVisibility(19);
			}
		}
	}

	/**
	 * animationVisibility - Methode zeigt das Charakterbild am Parameter t und
	 * versteckt alle anderen Charakterbilder
	 * 
	 * @param t index des Charakterbildes, dessen Visibility auf TRUE gesetzt werden soll
	 */
	private void animationVisibility(int t) {
		charakter[t].setVisible(true);
		for (int i = 0; i < charakter.length; i++) {
			if (i != t)
				charakter[i].setVisible(false);
		}
	}

	/**
	 * pickUpItem - Methode sorgt je nach Position und Itemstatus für das
	 * Aufheben/Benutzen von Items
	 */
	public void pickUpItem() {
		if (this.player_rotation == 0) { // W -> von UNTEN
			if (Math.floor((oben - Math.floor(oben)) * 10) / 10 == 0.1) {
				item_pickUpStone();
			}
			if (Math.floor((oben - Math.floor(oben)) * 10) / 10 == 0.2 && this.item_status == "stein") {
				item_openChest();
			}
		} else if (this.player_rotation == 1) { // D -> von RECHTS
			if (Math.floor((rechts - Math.floor(rechts)) * 10) / 10 == 0.1) {
				item_pickUpStone();
			}
			if ((Math.floor((rechts - Math.floor(rechts)) * 10) / 10 == 0.7
					|| Math.floor((rechts - Math.floor(rechts)) * 10) / 10 == 0.9) && this.item_status == "key") {
				System.out.println("OPEN");
				item_openDoor();
			}
		} else if (this.player_rotation == 2) { // S -> von OBEN
			if (Math.floor((unten - Math.floor(unten)) * 10) / 10 == 0.1) {
				item_pickUpStone();
			}
		} else if (this.player_rotation == 3) { // A -> von LINKS
			if (Math.floor((links - Math.floor(links)) * 10) / 10 == 0.1) {
				item_pickUpStone();
			}
		}
	}

	/**
	 * item_pickUpStone - Methode hebt den Stein auf
	 */
	public void item_pickUpStone() {
		bg.setBackupTexture(0);
		inv_items.get(0).setVisible(true);
		this.item_status = "stein";
	}

	/**
	 * item_openChest - Methode öffnet die Truhe
	 */
	public void item_openChest() {
		bg.setBackupTexture(1);
		inv_items.get(0).setVisible(false);
		inv_items.get(1).setVisible(true);
		this.item_status = "key";
	}

	/**
	 * item_openDoor - Methode öffnet die Türen
	 */
	public void item_openDoor() {
		bg.setBackupTexture(2);
		bg.setBackupTexture(3);
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (active) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				walking[0] = 1;
				lastActive[0] = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				walking[1] = 1;
				lastActive[0] = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				walking[2] = 1;
				lastActive[1] = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				walking[3] = 1;
				lastActive[1] = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				int ran_x = (int) (-32 - Math.random() * 2400);
				int ran_y = (int) (-Math.random() * 2400);
				bg.setPosition(ran_x, ran_y);
			}
			if (e.getKeyCode() == KeyEvent.VK_E) {
				e_pressed = true;
				pickUpItem();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (active) {
				menu.setMenu("ingame_menu");
			} else {
				menu.setMenu("ingame");
			}
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (active) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				walking[0] = 0;
				lastActive[0] = 1;
				this.player_rotation = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				walking[1] = 0;
				lastActive[0] = -1;
				this.player_rotation = 2;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				walking[2] = 0;
				lastActive[1] = 1;
				this.player_rotation = 3;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				walking[3] = 0;
				lastActive[1] = -1;
				this.player_rotation = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				running = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_E) {
				e_pressed = false;
			}
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}
}
