package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import main.Window;

public class GameControl extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private boolean active;
	private String id, charaktername, spielstandname, item_status;
	private int player_pos_x, player_pos_y, player_rotation;
	private Window window;

	private Thread game;
	private Dimension size;

	public GameControl(Window window) {
		this.active = false;
		this.window = window;
		canvasPreferredSize();
	}

	public void canvasPreferredSize() {
		size = new Dimension(window.getWidth(), window.getHeight());
		setPreferredSize(size);
	}

	public void setActive(String id, String charaktername, String spielstandname, String item_status, int player_x,
			int player_y, int player_rotation) {
		this.id = id;
		this.charaktername = charaktername;
		this.spielstandname = spielstandname;
		this.item_status = item_status;
		this.player_pos_x = player_x;
		this.player_pos_y = player_y;
		this.player_rotation = player_rotation;

	}

	public synchronized void start() {
		this.active = true;
		game = new Thread(this, "Game");
		game.start();
	}

	public synchronized void stop() {
		this.active = false;
		try {
			game.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (active) {
			update();
			render();
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//Graphic changes
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 200, 200);
		//
		g.dispose();
		bs.show();
		System.out.println("Running...");
	}

	private void update() {

	}
}
