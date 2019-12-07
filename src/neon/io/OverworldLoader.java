package neon.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import neon.overworld.OverworldFile;
import neon.overworld.World;

public class OverworldLoader {
	
	public static OverworldFile readFile(String path) {
		try {
			String str = new String(Files.readAllBytes(Paths.get(path)));
			str = str.replaceAll("\r", "");
			String[] lines = str.split("\n");
			
			OverworldFile owf = new OverworldFile();
			Image img = new Image(lines[0]);
			owf.setBackground(img);
			
			ArrayList<World> worlds = new ArrayList<World>();
			for (int i = 1; i < lines.length; i++) {
				World w = WorldLoader.readFile(lines[i]);
				worlds.add(w);
			}
			owf.setWorlds(worlds);
			return owf;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
