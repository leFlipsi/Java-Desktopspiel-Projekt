package main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

public class MenuSelection /*extends JSlider*/{
	/*
	public MenuSelection(String[] text, int y_difference, int button_width, int button_height, Window window){
		this.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		this.setMinimum(0);
		this.setMaximum(text.length-1);
		this.setValue(0);
		this.setBackground(Color.DARK_GRAY);
		window.add(this);
	}
	*/
	
	private JComboBox selection_box;
	
	public MenuSelection(String[] text, int y_difference, int button_width, int button_height, int selected_index, Window window){
		selection_box = new JComboBox(text);
		
		/*
		selection_box.setBackground(Color.DARK_GRAY);
		selection_box.setFocusable(false);
		selection_box.setForeground(Color.WHITE);
		for (int i = 0; i < selection_box.getComponentCount(); i++) {
			if (selection_box.getComponent(i) instanceof JComponent) {
		        ((JComponent) selection_box.getComponent(i)).setBorder(null);
		    }
		}
		*/
		
		selection_box.setBounds((window.getWidth()/2)-(button_width/2), (window.getHeight()/2)-(button_height/2)+y_difference, button_width, button_height);
		selection_box.setSelectedIndex(selected_index);
		selection_box.addItemListener(
			new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						window.setSize(text[selection_box.getSelectedIndex()]);
					}
				}
			}
		);
		window.add(selection_box);
		window.validate();
	}
}
