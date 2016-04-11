package main;
// Kartoffelbrei
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import konstanten.*;

public class Menu implements ActionListener{	
	private Window window;
	private int button_width = 192;
	private int button_height = 32;
	private int button_difference = 64;
	private MenuButton[] start_buttons;
	private MenuButton[] option_buttons;
	private MenuButton[] create_buttons;
	private MenuTextfield[] create_fields;
	private MenuSelection o_windowscaleList;
	private String[] o_scales = {"800x450", "1024x576", "1920x1080"};
	private File file;
	private BufferedWriter bw;
	private BufferedReader br;
	private ArrayList<String> savetxt;
	private String readerString;
	private String[] savetxt_array;
	private String[] id;
	private String[] charactername;
	private String[] spielstandname;

	
	public Menu(Window window, String type) {
		start_buttons = new MenuButton[3];
		option_buttons = new MenuButton[3];
		create_buttons = new MenuButton[2];
		create_fields = new MenuTextfield[2];
		this.window = window;
		file = new File("./src/files/save.txt");
		initButtons();
		setMenu(type);
	}
	
	public void setMenu(String type){
		hideAllButtons();
		if(type == "start"){	
			showButtons(start_buttons, 0);
		}else if(type == "options"){	
			showButtons(option_buttons, 0);
		}else if(type == "create_game"){
			showButtons(create_buttons, 0);
			showTextfields(create_fields);
		}
		//Bild laden
		window.getContentPane().repaint();
	}
	public void hideAllButtons(){
		hideButtons(option_buttons, 0);
		hideButtons(start_buttons, 0);
		hideButtons(create_buttons, 0);
		hideTextfields(create_fields);
	}
	
	public void initButtons(){
		for(int i = 0; i < start_buttons.length; i++){
			start_buttons[i] = new MenuButton(TextVars.start_button_text[i], (i*button_difference)-button_difference, button_width, button_height, window);
			start_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < option_buttons.length; i++){
			option_buttons[i] = new MenuButton(TextVars.option_button_text[i], (i*button_difference)-button_difference, button_width, button_height, window);
			option_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_buttons.length; i++){
			create_buttons[i] = new MenuButton(TextVars.create_button_text[i], ((create_fields.length)*button_difference)-button_difference, i, button_width/2, button_height, window);
			create_buttons[i].addActionListener(this);
		}
		for(int i = 0; i < create_fields.length; i++){
			create_fields[i] = new MenuTextfield(TextVars.create_fields_text[i], (i*button_difference)-button_difference, button_width, button_height, window);
		}
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

	public void saveGame(String char_name, String game_name){
		create_buttons[0].setEnabled(false);
		try {
			loadToArraylist();
            bw = new BufferedWriter(new FileWriter(file)); 			//L�scht Inhalt!!!
            for(int i = 0; i < savetxt.size(); i++){
                bw.write(savetxt.get(i));
                bw.newLine();
            }
            bw.append((savetxt.size()+1) + "|" + char_name + "|" + game_name + "|");
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
		charactername = new String[savetxt.size()];
		spielstandname = new String[savetxt.size()];
		//TODO
		savetxt_array = savetxt.toArray(savetxt_array);
		for(int i = 0; i < savetxt_array.length; i++){
			id[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
			charactername[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
			spielstandname[i] = savetxt_array[i].substring(0, savetxt_array[i].indexOf("|"));
			shortSavetxt(i);
			
			System.out.println(id[i]);
			System.out.println(charactername[i]);
			System.out.println(spielstandname[i]);
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
	
	// EVENT LISTENERS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.start_buttons[0]){				//Spielstand ERSTELLEN
			setMenu("create_game");
        }else if(e.getSource() == this.start_buttons[1]){		//Spielstand LADEN
        	//TODO
        }else if(e.getSource() == this.start_buttons[2]){		//Optionen
            setMenu("options");
        }	
		
		if(e.getSource() == this.option_buttons[0]){			//Fenstergr��e:
			hideButtons(option_buttons, 1);
			o_windowscaleList.showList();
        }else if(e.getSource() == this.option_buttons[1]){		//Anwenden
        	window.updateWindowSize();		
        	start_buttons[0].resetBounds(start_buttons);
        	option_buttons[0].resetBounds(option_buttons);
        	create_buttons[0].resetBounds(create_buttons);
        	create_fields[0].resetBounds(create_fields);
        	o_windowscaleList.resetBounds();
        	this.setMenu("start");
        	window.setLocationRelativeTo(null);
        }else if(e.getSource() == this.option_buttons[2]){		//Zur�ck
            window.keepWindowSize();
            setMenu("start");
        }
		
		if(e.getSource() == this.create_buttons[0]){			//
			//create_fields[0].getText();
			saveGame(create_fields[0].getText(), create_fields[1].getText());
        }else if(e.getSource() == this.create_buttons[1]){		//Abbrechen
        	setMenu("start");
        }
	}
}