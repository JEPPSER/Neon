package neon.overworld;

import java.util.ArrayList;

import org.newdawn.slick.Image;

public class OverworldFile {
	
	private ArrayList<World> worlds;
	private Image background;
	
	public OverworldFile(ArrayList<World> worlds, Image background) {
		this.worlds = worlds;
		this.background = background;
	}
	
	public OverworldFile() {
	}
	
	public ArrayList<World> getWorlds() {
		return worlds;
	}
	
	public void setWorlds(ArrayList<World> worlds) {
		this.worlds = worlds;
	}
	
	public Image getBackground() {
		return background;
	}
	
	public void setBackground(Image background) {
		this.background = background;
	}
}
