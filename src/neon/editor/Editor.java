package neon.editor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import neon.entity.Entity;
import neon.entity.controllable.Player;
import neon.entity.terrain.BouncingGround;
import neon.entity.terrain.Bounds;
import neon.entity.terrain.Ground;
import neon.entity.terrain.movable.MovableGround;
import neon.entity.terrain.movable.MovableSpikes;
import neon.graphics.Point;
import neon.io.LevelLoader;
import neon.level.Level;

public class Editor extends BasicGameState {
	
	public static int id = 2;
	
	private Level level;
	private String levelPath;

	private Ground placing;
	private boolean isPlacing;
	
	private Point mousePos;
	private Point prevMousePos;
	private Point cursor;
	
	private float width = 1000;
	private float height = 500;
	private float gridSize = 50;
	private float scale = 1f;
	
	private Point[] corners;
	private int changingCorner = -1;
	private boolean isChangingSize;
	
	private float x = 100;
	private float y = 100;

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		level = new Level();
		corners = new Point[] { new Point(x, y), new Point(x + width, y), new Point(x + width, y + height), new Point(x, y + height) };
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		g.scale(scale, scale);
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
		g.setLineWidth(1);
		for (int i = 0; i < width / gridSize; i++) {
			g.drawLine(i * gridSize + x, y, i * gridSize + x, height + y);
		}
		for (int i = 0; i < height / gridSize; i++) {
			g.drawLine(0 + x, i * gridSize + y, width + x, i * gridSize + y);
		}
		
		g.setLineWidth(2);
		
		// Border
		g.drawOval(x - 10, y - 10, 20, 20);
		g.drawOval(x + width - 10, y - 10, 20, 20);
		g.drawOval(x + width - 10, y + height - 10, 20, 20);
		g.drawOval(x - 10, y + height - 10, 20, 20);
		g.setColor(Color.green);
		for (int i = 0; i < corners.length - 1; i++) {
			g.setColor(Color.white);
			g.drawOval(corners[i].getX() - 10, corners[i].getY() - 10, 20, 20);
			g.setColor(Color.green);
			g.drawLine(corners[i].getX(), corners[i].getY(), corners[i + 1].getX(), corners[i + 1].getY());
		}
		g.setColor(Color.white);
		g.drawOval(corners[3].getX() - 10, corners[3].getY() - 10, 20, 20);
		g.setColor(Color.green);
		g.drawLine(corners[3].getX(), corners[3].getY(), corners[0].getX(), corners[0].getY());
		
		// Cursor
		g.setColor(Color.green);
		g.drawRect(x + cursor.getX() - 5, y + cursor.getY() - 5, 10, 10);
		
		// Objects
		for (int i = 0; i < level.getObjects().size(); i++) {
			Entity e = level.getObjects().get(i);
			if (!(e instanceof Bounds)) {
				e.render(g, x, y);
			}
		}
		
		g.setColor(Color.green);
		g.drawString("cursor: " + cursor.getX() + ", " + cursor.getY(), 50, 50);
		g.scale(1 / scale, 1 / scale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		prevMousePos = mousePos;
		mousePos = new Point(input.getMouseX(), input.getMouseY());
		float cursorX = (float) (Math.round((mousePos.getX() - x) / gridSize) * gridSize);
		float cursorY = (float) (Math.round((mousePos.getY() - y) / gridSize) * gridSize);
		cursor = new Point(cursorX, cursorY);
		
		if (getCorner() != -1 || isChangingSize) {
			changeSize(input);
			return;
		}
		
		if (input.isKeyDown(Input.KEY_P)) {
			sbg.enterState(4);
		}
		
		if (input.isKeyDown(Input.KEY_LCONTROL)) {
			x += mousePos.getX() - prevMousePos.getX();
			y += mousePos.getY() - prevMousePos.getY();
			corners = new Point[] { new Point(x, y), new Point(x + width, y), new Point(x + width, y + height), new Point(x, y + height) };
		}
		
		if (input.isKeyPressed(Input.KEY_1)) {
			scale += 0.1f;
		}
		
		if (input.isKeyPressed(Input.KEY_2)) {
			scale -= 0.1f;
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			place();
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			size();
		}
		
		if (isPlacing && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			placementDone();
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			delete();
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			exportLevel();
		}
		
		if (input.isKeyPressed(Input.KEY_E)) {
			exportLevelBinary();
		}
		
		if (input.isKeyPressed(Input.KEY_I)) {
			importLevel("res/temp.nlvl");
		}
	}
	
	private void changeSize(Input input) {
		if (!isChangingSize) {
			changingCorner = getCorner();
		}
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			isChangingSize = true;
		}
		if (isChangingSize && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			moveCorners();
		}
		if (isChangingSize && !input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			isChangingSize = false;
			changingCorner = -1;
			adjustSizeAndPos();
		}
	}
	
	private void adjustSizeAndPos() {
		width = corners[1].getX() - corners[0].getX();
		height = corners[3].getY() - corners[0].getY();
		float difX = x - corners[3].getX();
		float difY = y + height - corners[3].getY();
		for (int i = 0; i < level.getObjects().size(); i++) {
			Entity e = level.getObjects().get(i);
			if (e instanceof MovableGround) {
				MovableGround mg = (MovableGround) e;
				for (Point p : mg.getPath()) {
					p.setX(p.getX() + difX);
					p.setY(p.getY() + difY);
				}
			} else if (e instanceof MovableSpikes) {
				MovableSpikes ms = (MovableSpikes) e;
				for (Point p : ms.getPath()) {
					p.setX(p.getX() + difX);
					p.setY(p.getY() + difY);
				}
			}
			e.setX(e.getX() + difX);
			e.setY(e.getY() + difY);
		}
		x = corners[0].getX();
		y = corners[0].getY();
	}
	
	private void moveCorners() {
		corners[changingCorner] = new Point(cursor.getX() + x, cursor.getY() + y);
		if (changingCorner == 0) {
			corners[1].setY(corners[changingCorner].getY());
			corners[3].setX(corners[changingCorner].getX());
		} else if (changingCorner == 1) {
			corners[0].setY(corners[changingCorner].getY());
			corners[2].setX(corners[changingCorner].getX());
		} else if (changingCorner == 2) {
			corners[3].setY(corners[changingCorner].getY());
			corners[1].setX(corners[changingCorner].getX());
		} else if (changingCorner == 3) {
			corners[2].setY(corners[changingCorner].getY());
			corners[0].setX(corners[changingCorner].getX());
		}
	}
	
	private int getCorner() {
		for (int i = 0; i < corners.length; i++) {
			if (mousePos.distanceTo(corners[i]) < 10) {
				return i;
			}
		}
		return -1;
	}
	
	private void delete() {
		for (int i = 0; i < level.getObjects().size(); i++) {
			Entity e = level.getObjects().get(i);
			if (e instanceof Ground && !(e instanceof MovableGround)) {
				Rectangle r = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
				if (r.contains(mousePos.getX() - x, mousePos.getY() - y)) {
					level.getObjects().remove(e);
				}
			}
		}
	}
	
	private void placementDone() {
		isPlacing = false;
		if (placing.getWidth() < 0) {
			placing.setX(placing.getX() + placing.getWidth());
			placing.setWidth(placing.getWidth() * -1);
		}
		if (placing.getHeight() < 0) {
			placing.setY(placing.getY() + placing.getHeight());
			placing.setHeight(placing.getHeight() * -1);
		}
		placing.setSize(placing.getWidth(), placing.getHeight());
		placing = null;
	}
	
	private void place() {
		placing = new Ground();
		placing.setX(cursor.getX());
		placing.setY(cursor.getY());
		level.getObjects().add(placing);
	}
	
	private void size() {
		if (placing != null) {
			placing.setWidth(cursor.getX() - placing.getX());
			placing.setHeight(cursor.getY() - placing.getY());
			if (placing.getWidth() == 0) {
				placing.setWidth(gridSize);
			}
			if (placing.getHeight() == 0) {
				placing.setHeight(gridSize);
			}
			isPlacing = true;
		}
	}
	
	private void exportLevelBinary() {
		try {
			String str = new String(Files.readAllBytes(Paths.get("res/temp.nlvl")));
			str = str.replaceAll("\r", "");
			String[] lines = str.split("\n");
			
			FileOutputStream fos = new FileOutputStream("res/test_level.rz");
			BufferedOutputStream out = new BufferedOutputStream(fos);
			
			float width = Float.parseFloat(lines[1].split(",")[0]);
			float height = Float.parseFloat(lines[1].split(",")[1]);
			float spawnX = Float.parseFloat(lines[2].split(",")[0]);
			float spawnY = Float.parseFloat(lines[2].split(",")[1]);
			
			out.write(floatToBytes(width));
			out.write(floatToBytes(height));
			out.write(floatToBytes(spawnX));
			out.write(floatToBytes(spawnY));	
			
			for (int i = 4; i < lines.length; i++) {
				String[] parts = lines[i].split(",");
				float x = Float.parseFloat(parts[1]);
				float y = Float.parseFloat(parts[2]);
				float w = Float.parseFloat(parts[3]);
				float h = Float.parseFloat(parts[4]);
				out.write(floatToBytes(x));
				out.write(floatToBytes(y));
				out.write(floatToBytes(w));
				out.write(floatToBytes(h));
			}
			
			out.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] floatToBytes(float val) {
	    int intBits = Float.floatToIntBits(val);
	    return new byte[] { (byte) (intBits), (byte) (intBits >> 8), (byte) (intBits >> 16), (byte) (intBits >> 24) };
	}
	
	private void exportLevel() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("0,0,0");
		lines.add(width + "," + height);
		lines.add(level.getSpawnPoint().getX() + "," + level.getSpawnPoint().getY());
		lines.add("ground_down,ground_side,ground_up,ground_side");
		
		for (int i = 0; i < level.getObjects().size(); i++) {
			Entity e = level.getObjects().get(i);
			if (!(e instanceof Bounds) && !(e instanceof Player)) {
				lines.add(e.toString());
			}
		}
		
//		if (levelPath != null) {
//			try {
//				String str = new String(Files.readAllBytes(Paths.get(levelPath)));
//				str = str.replaceAll("\r", "");
//				String[] parts = str.split("\n");
//				for (int i = 3; i < parts.length; i++) {
//					if (!parts[i].startsWith("0")) {
//						lines.add(parts[i]);
//					}
//				}
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//		
//		for (int i = 0; i < level.getObjects().size(); i++) {
//			Entity e = level.getObjects().get(i);
//			if (e instanceof Ground && !(e instanceof MovableGround) && !(e instanceof BouncingGround)) {
//				lines.add("0," + e.getX() + "," + e.getY() + "," + e.getWidth() + "," + e.getHeight());
//			}
//		}
		try {
			Files.write(Paths.get("res/temp.nlvl"), lines, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void importLevel(String path) {
		this.level = LevelLoader.readFile(path);
		this.width = level.getWidth();
		this.height = level.getHeight();
		this.levelPath = path;
	}

	@Override
	public int getID() {
		return Editor.id;
	}
}
