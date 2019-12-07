package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import neon.overworld.entity.World;
import neon.settings.InputSettings;

public class WorldLoader {

	public static World readFile(String path) {
		try {
			File file = new File(path);
			String str = new String(Files.readAllBytes(Paths.get(path)));
			str = str.replaceAll("\r", "");
			String[] lines = str.split("\n");
			
			World world = new World(100, 100);
			world.setTrigger("PRESS " + InputSettings.getKeyboardBinds().get("action") + " TO ENTER", 0, 0);
			world.setName(file.getName().replace(".nwrld", ""));
			
			Image img = new Image(lines[0]);
			world.setImage(img);
			
			String[] parts = lines[1].split(",");
			world.setX(Float.parseFloat(parts[0]));
			world.setY(Float.parseFloat(parts[1]));
			
			ArrayList<String> levels = new ArrayList<String>();
			for (int i = 2; i < lines.length; i++) {
				levels.add(lines[i]);
			}
			world.setLevels(levels);
			return world;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
