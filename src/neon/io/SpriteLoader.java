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
			ArrayList<Point> points = new ArrayList<Point>();
			String[] lines = str.split("\n");
			float width = Float.parseFloat(lines[0]);
			float height = Float.parseFloat(lines[1]);
			for (int i = 3; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				float x = Float.parseFloat(parts[0]);
				float y = Float.parseFloat(parts[1]);
				Point p = new Point(x, y);
				points.add(p);
			}
			return new Sprite(points, width, height, file.getName().replace(".nspr", ""));
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
			} else if (list[i].getName().endsWith(".nspr")) {
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
