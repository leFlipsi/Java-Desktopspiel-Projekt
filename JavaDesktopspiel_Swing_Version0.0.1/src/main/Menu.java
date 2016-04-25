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
import konstanten.*;
import textfields.MenuShowtext;
import textfields.MenuTextfield;

public class Menu implements ActionListener{	
	private Window window;
	private int button_width, button_height, button_difference, field_difference;
	private MenuButton load_cancel;
	private MenuButton[] start_buttons, option_buttons, create_buttons, load_buttons;
	private MenuTextfield[] create_fields;
	private MenuSelection o_windowscaleList;
	private String[] o_scales = {"800x450", "1024x576", "1920x1080"};
	private File file;
	private BufferedWriter bw;
	private BufferedReader br;
	private ArrayList<String> savetxt;
	private String readerString;
	private String[] savetxt_array, id, charactername, spielstandname;
	private MenuShowtext[] load_showtext;
	private GameControl game;
	
	public Menu(Window window, String type) {
		this.start_buttons = new MenuButton[3];
		this.option_buttons = new MenuButton[3];
		this.create_buttons = new MenuButton[2];
		this.load_buttons = new MenuButton[6];
		this.create_fields = new MenuTextfield[2];
		this.load_showtext = new MenuShowtext[3];
		this.window = window;
		this.file = new File("./src/files/save.txt");
		
		this.button_width = 192;
		this.button_height = 32;
		this.button_difference = 64;
		this.field_difference = -128;
		
		this.game = new GameControl(false);
		
		initButtons();
		setMenu(type);
	}
	
	public void setMenu(String type){
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
			
		}
		
		window.getContentPane().repaint();
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
				load_showtext[i] = new MenuShowtext(spielstandname[i], charactername[i], id[i], i,(int)(button_width*1.5), button_height*2, field_difference, window);
			}catch(Exception e){
			}
		}
		this.load_cancel = new MenuButton(TextVars.create_button_text[0], 3*button_difference-button_height/2, -1, button_width*3, button_height, window);
		this.load_cancel.addActionListener(this);
		o_windowscaleList = new MenuSelection(o_scales, -button_difference, button_width, button_height, window, this);
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
            bw = new BufferedWriter(new FileWriter(file)); 			//L�scht Inhalt!!!
            for(int i = 0; i < savetxt.size(); i++){
            	if(i != delete_id){
            		if(i > delete_id && delete_id >= 0){
            			savetxt.set(i, i + "|" + charactername[i] + "|" + spielstandname[i] + "|");
            		}
	                bw.write(savetxt.get(i));
	                bw.newLine();
            	}
            }
            if(delete_id < 0){
            	bw.append((savetxt.size()+1) + "|" + char_name + "|" + game_name + "|");
            }
            bw.flush();
            bw.close();
            game.setActive(""+savetxt.size()+1, char_name, game_name);
        } catch (Exception e) {
            e.printStackTrace();
		}
	}
	public void explodeArraylistItems(){
		loadToArraylist();
		savetxt_array = new String[savetxt.size()];
		id = new String[savetxt.size()];
		charactername = new String[savetxt.size()];
		spielstandname = new String[savetxt.size()];
		//TODO -> Alle weiteren Speicherdaten
		savetxt_array = savetxt.toArray(savetxt_array);
		for(int i = 0; i < savetxt_array.length; i++){
			id[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
			charactername[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
			spielstandname[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
		}
	}
	public void shortSavetxt(int i){
		savetxt_array[i] = savetxt_array[i].substring(savetxt_array[i].indexOf("|")+1, savetxt_array[i].length());
	}
	public void loadToArraylist(){
		try{
			savetxt = new ArrayList<String>();
			br = new BufferedReader(new FileReader(file));
            while ((readerString = br.readLine()) != null) {
            	savetxt.add(readerString);
			}
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame(int i){
		game.setActive(id[i], charactername[i], spielstandname[i]);
	}
	public void deleteGame(int i){
		saveGame("", "", i);
	}
	
	// EVENT LISTENERS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.start_buttons[0]){				//Spielstand ERSTELLEN
			setMenu("create_game");
        }else if(e.getSource() == this.start_buttons[1]){		//Spielstand LADEN
        	if(id[0] != null){
        		setMenu("load_game");
        	}else{
        		start_buttons[1].setEnabled(false);
        	}
        }else if(e.getSource() == this.start_buttons[2]){		//Optionen
            setMenu("options");
        }	
		
		if(e.getSource() == this.option_buttons[0]){			//Fenstergr��e:
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
        }else if(e.getSource() == this.option_buttons[2]){		//Zur�ck
            window.keepWindowSize();
            setMenu("start");
        }
		
		if(e.getSource() == this.create_buttons[0]){			//Abbrechen
			setMenu("start");
        }else if(e.getSource() == this.create_buttons[1]){		//Erstellen
        	create_buttons[1].setEnabled(false);
    		loadToArraylist();
    		if(savetxt.size() <= 2){
    			saveGame(create_fields[0].getText(), create_fields[1].getText(), -1);
    			setMenu("ingame");
    		}else{
    			setMenu("start");
    		}
        }
		
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
	}
}