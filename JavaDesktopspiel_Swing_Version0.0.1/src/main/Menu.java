package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import buttons.MenuButton;
import buttons.MenuSelection;
import game.GameControl;
import image.LoadBackground;
import konstanten.TextVars;
import textfields.MenuShowtext;
import textfields.MenuTextfield;

public class Menu implements ActionListener{	
	private Window window;
	private int button_width, button_height, button_difference, field_difference;
	private MenuButton load_cancel;
	private MenuButton[] start_buttons, option_buttons, create_buttons, load_buttons, ingame_buttons;
	private MenuTextfield[] create_fields;
	private MenuSelection o_windowscaleList;
	private String[] o_scales = {"800x450", "1024x576", "1920x1080"};
	private File file;
	private ArrayList<String> savetxt;
	private String readerString;
	private String[] savetxt_array, id, charaktername, spielstandname, itemstatus;
	private int[] xposition, yposition, rotation;
	private MenuShowtext[] load_showtext;
	private GameControl game;
	private BufferedReader br;
	private LoadBackground menu_bg;
	
	public Menu(Window window, String type) {
		this.window = window;
		this.start_buttons = new MenuButton[3];
		this.option_buttons = new MenuButton[3];
		this.create_buttons = new MenuButton[2];
		this.load_buttons = new MenuButton[6];
		this.create_fields = new MenuTextfield[2];
		this.load_showtext = new MenuShowtext[3];
		this.ingame_buttons = new MenuButton[3];
		
		this.file = new File("./src/files/save.txt");
		this.savetxt = new ArrayList<String>();
		
		this.button_width = 192;
		this.button_height = 32;
		this.button_difference = 64;
		this.field_difference = -128;
		
		this.game = new GameControl(window, this);
		window.add(game);						//TODO  -  Test
		initButtons();
		setMenu(type);
		
		if(savetxt.size() < 1)
			start_buttons[1].setEnabled(false);
		
		menu_bg = new LoadBackground(window, TextVars.menu_bg);
	}
	
	public void initButtons(){
		explodeArraylistItems();
		for(int i = 0; i < start_buttons.length; i++){
			start_buttons[i] = new MenuButton(TextVars.start_button_text[i], (i*button_difference)-button_difference, -1, button_width, button_height, window);
			start_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < option_buttons.length; i++){
			option_buttons[i] = new MenuButton(TextVars.option_button_text[i], (i*button_difference)-button_difference, -1, button_width, button_height, window);
			option_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_buttons.length; i++){
			create_buttons[i] = new MenuButton(TextVars.create_button_text[i], ((create_fields.length)*button_difference)-button_difference, i, button_width/2, button_height, window);
			create_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_fields.length; i++){
			create_fields[i] = new MenuTextfield(TextVars.create_fields_text[i], (i*button_difference)-button_difference, button_width, button_height, window);
		}
		for(int i = 0; i < load_buttons.length; i++){
			load_buttons[i] = new MenuButton(TextVars.load_button_text[i%2], ((i%2)*button_height+2*(i%2))+((int)Math.floor(i/2)-1)*2*button_difference-button_height, 2, button_width, button_height, window);
			load_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < load_showtext.length; i++){
			try{
				load_showtext[i] = new MenuShowtext(spielstandname[i], charaktername[i], id[i], i,(int)(button_width*1.5), button_height*2, field_difference, window);
			}catch(Exception e){
			}
		}
		for(int i = 0; i < ingame_buttons.length; i++){
			ingame_buttons[i] = new MenuButton(TextVars.ingame_button_text[i], (i*button_difference)-button_difference, -1, button_width, button_height, window);
			ingame_buttons[i].addActionListener(this);
		}
		this.load_cancel = new MenuButton(TextVars.create_button_text[0], 3*button_difference-button_height/2, -1, button_width*3, button_height, window);
		this.load_cancel.addActionListener(this);
		o_windowscaleList = new MenuSelection(o_scales, -button_difference, button_width, button_height, window, this);
	}
	
	public void setMenu(String type){
		explodeArraylistItems();
		hideAll();
		if(type == "start"){	
			showButtons(start_buttons, 0);
		}else if(type == "options"){	
			showButtons(option_buttons, 0);
		}else if(type == "create_game"){
			showButtons(create_buttons, 0);
			showTextfields(create_fields);
		}else if(type == "load_game"){
			showButtons(id.length, load_buttons);
			showShowTexts(load_showtext);
			load_cancel.setVisible(true);
		}else if(type == "ingame"){
			game.start();
		}else if(type == "ingame_menu"){
			showButtons(ingame_buttons, 0);
			ingame_buttons[1].setVisible(false);
			game.stop();
		}
		window.getContentPane().repaint();
	}
	
	
	public void hideButtons(MenuButton[] button_list, int start){
		for(int i = start; i < button_list.length; i++){
			button_list[i].setVisible(false);
		}
	}
	public void showButtons(MenuButton[] button_list, int start){
		for(int i = start; i < button_list.length; i++){
			button_list[i].setVisible(true);
		}
	}
	public void showButtons(int stop, MenuButton[] button_list){
		for(int i = 0; i < stop*2; i++){
			button_list[i].setVisible(true);
		}
	}
	public void hideTextfields(MenuTextfield[] field_list){
		for(int i = 0; i < field_list.length; i++){
			field_list[i].setVisible(false);
		}
	}
	public void showTextfields(MenuTextfield[] field_list){
		for(int i = 0; i < field_list.length; i++){
			field_list[i].setVisible(true);
		}
	}
	public void hideShowTexts(MenuShowtext[] text, int start){
		for(int i = start; i < text.length; i++){
			if(load_showtext[i] != null){
				text[i].setVisible(false);
			}else{
				i += 3;
			}
		}
	}
	public void showShowTexts(MenuShowtext[] text){
		for(int i = 0; i < text.length; i++){
			if(load_showtext[i] != null){
				text[i].setVisible(true);
			}else{
				i += 3;
			}
		}
	}
	public void hideAll(){
		hideButtons(ingame_buttons, 0);
		hideButtons(option_buttons, 0);
		hideButtons(start_buttons, 0);
		hideButtons(create_buttons, 0);
		hideButtons(load_buttons, 0);
		hideTextfields(create_fields);
		hideShowTexts(load_showtext, 0);
		load_cancel.setVisible(false);
	}

	public void saveGame(String char_name, String game_name, int delete_id){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file)); 			//Löscht Inhalt!!!
            for(int i = 0; i < savetxt.size(); i++){
            	if(i != delete_id){
            		if(i > delete_id && delete_id >= 0){
            			savetxt.set(i, i + "|" + charaktername[i] + "|" + spielstandname[i] + "|" + 
            					itemstatus[i] + "|" + xposition[i] + "|" + yposition[i] + "|" + rotation[i] + "|");
            		}
            		bw.write(savetxt.get(i));
	                bw.newLine();
            	}
            }
            if(delete_id < 0){
            	bw.append((savetxt.size()+1) + "|" + char_name + "|" + game_name + "|" + "" + "|" + 0 + "|" + 0 + "|" + 0 + "|");
            }
            bw.flush();
            bw.close();
            //game.setActive(""+savetxt.size()+1, char_name, game_name, "", 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
		}
	}
	public void overrideGame(int number, String itemstatus, int xposition, int yposition, int rotation){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file)); 			//Löscht Inhalt!!!
            for(int i = 0; i < savetxt.size(); i++){
            	if(i == number){
	    			savetxt.set(i, i + "|" + 
	    					charaktername[i] + "|" + 
	    					spielstandname[i] + "|" + 
	    					itemstatus + "|" + 
	    					xposition + "|" + 
	    					yposition + "|" +
	    					rotation + "|"
	    				);
            	}
                bw.write(savetxt.get(i));
                bw.newLine();
    		}
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
		}
	}
	public void explodeArraylistItems(){
		loadToArraylist();
		savetxt_array = new String[savetxt.size()];
		id = new String[savetxt.size()];
		charaktername = new String[savetxt.size()];
		spielstandname = new String[savetxt.size()];
		itemstatus = new String[savetxt.size()];
		xposition = new int[savetxt.size()];
		yposition = new int[savetxt.size()];
		rotation = new int[savetxt.size()];
		//TODO -> Alle weiteren Speicherdaten
		savetxt_array = savetxt.toArray(savetxt_array);
		for(int i = 0; i < savetxt_array.length; i++){
			id[i] = substringSavetxt(i);
			shortSavetxt(i);
			charaktername[i] = substringSavetxt(i);
			shortSavetxt(i);
			spielstandname[i] = substringSavetxt(i);
			shortSavetxt(i);
			itemstatus[i] = substringSavetxt(i);
			shortSavetxt(i);
			xposition[i] = Integer.parseInt(substringSavetxt(i));
			shortSavetxt(i);
			yposition[i] = Integer.parseInt(substringSavetxt(i));
			shortSavetxt(i);
			rotation[i] = Integer.parseInt(substringSavetxt(i));
			shortSavetxt(i);
		}
	}
	public String substringSavetxt(int i){
		return savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
	}
	public void shortSavetxt(int i){
		savetxt_array[i] = savetxt_array[i].substring(savetxt_array[i].indexOf("|")+1, savetxt_array[i].length());
	}
	public void loadToArraylist(){
		try{
			br = new BufferedReader(new FileReader(file));
			savetxt.clear();
            while ((readerString = br.readLine()) != null) {
            	savetxt.add(readerString);
			}
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame(int i){
		explodeArraylistItems();
		game.setActive(id[i], charaktername[i], spielstandname[i], itemstatus[i], xposition[i], yposition[i], rotation[i], menu_bg);
		setMenu("ingame");
	}
	public void deleteGame(int i){
		saveGame("", "", i);
	}
	
	/*__________________________________________ EVENT LISTENERS ____________________________________________________________*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*____________________Startmenü____________________*/
		if(e.getSource() == this.start_buttons[0]){				//Spielstand ERSTELLEN
			setMenu("create_game");
        }else if(e.getSource() == this.start_buttons[1]){		//Spielstand LADEN
        	loadToArraylist();
        	if(id[0] != null){
        		setMenu("load_game");
        	}else{
        		setMenu("start");
        	}
        }else if(e.getSource() == this.start_buttons[2]){		//Optionen
            setMenu("options");
        }	
		
		/*____________________Optionenmenü____________________*/
		if(e.getSource() == this.option_buttons[0]){			//Fenstergröße:
			hideButtons(option_buttons, 1);
			o_windowscaleList.showList();
        }else if(e.getSource() == this.option_buttons[1]){		//Anwenden
        	window.updateWindowSize();		
        	start_buttons[0].setButtonBounds(start_buttons);
        	option_buttons[0].setButtonBounds(option_buttons);
        	create_buttons[0].setButtonBounds(create_buttons);
        	create_fields[0].resetBounds(create_fields);
        	load_cancel.setButtonBounds();
        	o_windowscaleList.resetBounds();
        	for(int i = 0; i < load_showtext.length; i++){
        		try{
        			load_showtext[i].resetBounds();
        			int j = 2*i;
        			load_buttons[j].setButtonBounds();
        			j++;
        			load_buttons[j].setButtonBounds();
        		}catch(Exception exc){
        			
        		}
        	}
        	setMenu("start");
        	window.setLocationRelativeTo(null);
        }else if(e.getSource() == this.option_buttons[2]){		//Zurück
            window.keepWindowSize();
            setMenu("start");
        }
		
		/*____________________Erstellenmenü____________________*/
		if(e.getSource() == this.create_buttons[0]){			//Abbrechen
			setMenu("start");
        }else if(e.getSource() == this.create_buttons[1]){		//Erstellen
        	create_buttons[1].setEnabled(false);
        	loadToArraylist();
    		if(savetxt.size() <= 2){
    			
    			saveGame(create_fields[0].getText(), create_fields[1].getText(), -1);
    			loadGame(savetxt.size());
    			setMenu("ingame");
    		}else{
    			setMenu("start");
    		}
        }
		
		/*____________________Lademenü____________________*/
		if(e.getSource() == this.load_buttons[0]){
			loadGame(0);
		}else if(e.getSource() == this.load_buttons[1]){
			deleteGame(0);
			setMenu("start");
		}else if(e.getSource() == this.load_buttons[2]){
			loadGame(1);
		}else if(e.getSource() == this.load_buttons[3]){
			deleteGame(1);
			setMenu("start");
		}else if(e.getSource() == this.load_buttons[4]){
			loadGame(2);
		}else if(e.getSource() == this.load_buttons[5]){
			deleteGame(2);
			setMenu("start");
		}
		if(e.getSource() == this.load_cancel){
			setMenu("start");
		}
		
		/*____________________Ingamemenü____________________*/
		if(e.getSource() == this.ingame_buttons[0]){
			setMenu("ingame");
		}
		if(e.getSource() == this.ingame_buttons[2]){
			System.exit(0);
		}
	}
}