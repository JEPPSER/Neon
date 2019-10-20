package neon.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import neon.graphics.Point;

public class SpriteLoader {
	
	public ArrayList<Point> readFile(String path) {
		try {
			String str = new String(Files.readAllBytes(Paths.get(path)));
			str = str.replaceAll("\r", "");
			ArrayList<Point> result = new ArrayList<Point>();
			String[] lines = str.split("\n");
			for (int i = 0; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				float x = Float.parseFloat(parts[0]);
				float y = Float.parseFloat(parts[1]);
				Point p = new Point(x, y);
				result.add(p);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
