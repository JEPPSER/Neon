package neon.overworld;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import neon.overworld.entity.World;

public class OverworldModel {
	
	private ArrayList<World> worlds;
	private Image background;
	
	public OverworldModel(ArrayList<World> worlds, Image background) {
		this.worlds = worlds;
		this.background = background;
	}
	
	public OverworldModel() {
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
