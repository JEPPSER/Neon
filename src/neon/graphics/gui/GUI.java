package neon.graphics.gui;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import neon.menu.SettingsMenu;

public class GUI {
	
	private static SettingsMenu settingsMenu;
	private static TrueTypeFont font;
	
	public static void init() {
		font = new TrueTypeFont(new Font("Helvetica", Font.BOLD, 20), true);
		settingsMenu = new SettingsMenu();
	}
	
	public static TrueTypeFont getFont() {
		return font;
	}
	
	public static SettingsMenu getSettingsMenu() {
		return settingsMenu;
	}
}
