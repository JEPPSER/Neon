package neon.entity.area.minigame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import neon.entity.Entity;
import neon.entity.controllable.Player;
import neon.entity.terrain.movable.MovableTerrain;
import neon.graphics.Point;
import neon.level.LevelManager;
import neon.physics.CollisionDirection;
import neon.physics.Physics;
import neon.settings.InputSettings;
import neon.time.TimeInfo;

public class SnakeArea extends MinigameArea {
	
	private String activateName;
	private boolean[][] grid;
	private float gridSize = 15;
	private ArrayList<Point> snake;
	private Point fruit;
	private CollisionDirection direction;
	private CollisionDirection lastDir;
	private final int DELTA = 80;
	private int timer = 0;
	
	public SnakeArea(float x, float y, float width, float height, String activateName) {
		this.name = "SnakeArea";
		this.physics = new Physics(0f, 0f);
		this.x = x;
		this.y = y;
		this.setSize(width, height);
		this.activateName = activateName;
		grid = new boolean[(int) (width / gridSize)][(int) (height / gridSize)];
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.yellow);
		g.fillRect(offsetX + x, offsetY + y, width, height);
		
		if (isTriggered) {	
			g.setColor(Color.black);
			for (Point p : snake) {
				g.fillRect(offsetX + x + p.getX() * gridSize, offsetY + y + p.getY() * gridSize, gridSize, gridSize);
			}
			g.setColor(Color.red);
			g.fillRect(offsetX + x + fruit.getX() * gridSize, offsetY + y + fruit.getY() * gridSize, gridSize, gridSize);
		}
	}

	@Override
	public int getID() {
		return 22;
	}

	@Override
	public void start() {
		snake = new ArrayList<Point>();
		snake.add(new Point(3, grid.length / 2f));
		snake.add(new Point(2, grid.length / 2f));
		snake.add(new Point(1, grid.length / 2f));
		placeFruit();
		direction = CollisionDirection.RIGHT;
		lastDir = CollisionDirection.RIGHT;
	}

	@Override
	public void end() {
		Player p = LevelManager.getLevel().getPlayer();
		p.setX(x + width + 50);
		p.setY(y);
	}

	@Override
	public void update(Input input, Player player) {
		
		// Check death
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
				player.takeDamage(player.getMaxHealth());
				player.setMinigame(null);
			}
		}
		if (snake.get(0).getX() >= grid.length || snake.get(0).getX() < 0
				|| snake.get(0).getY() >= grid[0].length || snake.get(0).getY() < 0) {
			player.takeDamage(player.getMaxHealth());
			player.setMinigame(null);
		}
		
		// Check fruit
		if (snake.get(0).getX() == fruit.getX() && snake.get(0).getY() == fruit.getY()) {
			Point last = snake.get(snake.size() - 1);
			snake.add(new Point(last.getX(), last.getY()));
			placeFruit();
			activate();
		}
		
		// Movement
		timer += TimeInfo.getDelta();
		if (timer > DELTA) {
			timer = 0;
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setX(snake.get(i - 1).getX());
				snake.get(i).setY(snake.get(i - 1).getY());
			}
			if (direction == CollisionDirection.RIGHT) {
				snake.get(0).setX(snake.get(0).getX() + 1);
			} else if (direction == CollisionDirection.LEFT) {
				snake.get(0).setX(snake.get(0).getX() - 1);
			} else if (direction == CollisionDirection.DOWN) {
				snake.get(0).setY(snake.get(0).getY() + 1);
			} else if (direction == CollisionDirection.UP) {
				snake.get(0).setY(snake.get(0).getY() - 1);
			}
			lastDir = direction;
		}
		
		// Controls
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("left")) || isButtonDown(input, "left")) {
			if (lastDir != CollisionDirection.RIGHT) {
				direction = CollisionDirection.LEFT;
			}
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("right")) || isButtonDown(input, "right")) {
			if (lastDir != CollisionDirection.LEFT) {
				direction = CollisionDirection.RIGHT;
			}
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("up")) || isButtonDown(input, "up")) {
			if (lastDir != CollisionDirection.DOWN) {
				direction = CollisionDirection.UP;
			}
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("down")) || isButtonDown(input, "down")) {
			if (lastDir != CollisionDirection.UP) {
				direction = CollisionDirection.DOWN;
			}
		}
	}
	
	private boolean isButtonDown(Input input, String action) {
		int button = InputSettings.getControllerBinds().get(action);
		for (int i = 0; i < input.getControllerCount(); i++) {
			if (button >= 4 && input.isButtonPressed(button - 4, i)) {
				return true;
			} else if (button < 4 && i == 2) {
				if (action.equals("left")) {
					if (input.isControllerLeft(i)) {
						return true;
					}
				} else if (action.equals("right")) {
					if (input.isControllerRight(i)) {
						return true;
					}
				} else if (action.equals("up")) {
					if (input.isControllerUp(i)) {
						return true;
					}
				} else if (action.equals("down")) {
					if (input.isControllerDown(i)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void activate() {
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		int count = 0;
		for (Entity e : objects) {
			if (e instanceof MovableTerrain && !((MovableTerrain) e).isActive() 
					&& e.getName().equals(activateName)) {
				if (count == 0) {
					((MovableTerrain) e).activate();
				}
				count++;
			}
		}
		if (count < 2) {
			end();
		}
	}
	
	private void placeFruit() {
		while (fruit == null || fruitOnSnake()) {
			int x = (int) (Math.random() * grid.length);
			int y = (int) (Math.random() * grid[0].length);
			fruit = new Point(x, y);
		}
	}
	
	private boolean fruitOnSnake() {
		for (Point p : snake) {
			if (p.getX() == fruit.getX() && p.getY() == fruit.getY()) {
				return true;
			}
		}
		return false;
	}
}
