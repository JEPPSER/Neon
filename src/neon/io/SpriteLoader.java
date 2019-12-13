package neon.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import neon.graphics.Point;
import neon.graphics.Sprite;

public class SpriteLoader {
	
	private static ArrayList<Sprite> sprites;
	
	public SpriteLoader() {
		sprites = new ArrayList<Sprite>();
	}
	
	private Sprite readFile(String path) {
		try {
			File file = new File(path);
			String str = new String(Files.readAllBytes(Paths.get(path)));
			str = str.replaceAll("\r", "");
			String[] lines = str.split("\n");
			return new Sprite(file.getName().replace(".png", ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void searchFolder(String path) {
		File file = new File(path);
		File[] list = file.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				searchFolder(list[i].getAbsolutePath());
			} else if (list[i].getName().endsWith(".png")) {
				sprites.add(readFile(list[i].getAbsolutePath()));
			}
		}
	}
	
	public static Sprite getSprite(String name) {
		for (int i = 0; i < sprites.size(); i++) {
			if (sprites.get(i).getName().equals(name)) {
				return sprites.get(i);
			}
		}
		return null;
	}
}
