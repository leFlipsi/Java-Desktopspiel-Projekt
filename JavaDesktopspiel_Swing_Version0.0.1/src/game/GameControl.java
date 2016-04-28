package game;

public class GameControl {
	private boolean active;
	private String id, charaktername, spielstandname, item_status;
	private int player_pos_x, player_pos_y, player_rotation;
	
	
	public GameControl(){
		this.active = false;
	}
	
	public void setActive(String id, String charaktername, String spielstandname, String item_status, int player_x, int player_y, int player_rotation){
		this.id = id;
		this.charaktername = charaktername;
		this.spielstandname = spielstandname;
		this.item_status = item_status;
		this.player_pos_x = player_x;
		this.player_pos_y = player_y;
		this.player_rotation = player_rotation;
		this.active = true;
	}
}
