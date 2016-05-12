package konstanten;

public interface TextVars {
	/*
	 * Fenstervariablen
	 */
	public String window_title = "Java Desktopspiel Projekt";
	/*
	 * Menüvariablen
	 */
	public String[] start_button_text = {"Neuen Spielstand erstellen", "Spielstand laden", "Optionen"};
	public String[] option_button_text = {"Fenstergröße:", "Anwenden", "Zurück"};
	public String[] create_button_text = {"Abbrechen", "Erstellen"};
	public String[] create_fields_text = {"Charaktername", "Spielstandname"};
	public String[] load_button_text = {"Laden" , "Löschen"};
	
	/*
	 * BackgroundData
	 */
		public double[] menu_bg1 = {1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 0.0, 0.0, 0.0};
		public double[] menu_bg2 = {2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		public double[] menu_bg3 = {3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 0.0, 0.0, 0.0};
		public double[] menu_bg4 = {0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 1.0, 2.0, 3.0, 0.0, 0.0, 0.0};
	public double[][] menu_bg = {menu_bg1, menu_bg2, menu_bg3, menu_bg4, menu_bg1, menu_bg2, menu_bg3, menu_bg4, menu_bg1};
}
