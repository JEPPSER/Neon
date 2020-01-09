package neon.entity.area.minigame;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.controllable.Player;
import neon.entity.terrain.movable.MovableTerrain;
import neon.graphics.gui.GUI;
import neon.level.LevelManager;
import neon.physics.Physics;
import neon.settings.InputSettings;
import neon.time.TimeInfo;

public class FlappyArea extends MinigameArea {
	
	private int scoreReq;
	private String activateName;
	private float xVel = 0.4f;
	private float yVel = 0;
	private float yAcc = 0.01f;
	private final float MAX_Y_VEL = 1f;
	private float jumpVel = -1.1f;
	private LinkedList<Rectangle> pipes;
	private Rectangle bird;
	private int score;
	private Color teal;
	private boolean started = false;
	
	private int timer = 0;
	private final int PIPE_TIME = 1000;
	private final float PIPE_GAP = 220;
	
	public FlappyArea(float x, float y, float width, float height, String activateName, int scoreReq) {
		this.name = "FlappyArea";
		this.physics = new Physics(0f, 0f);
		this.x = x;
		this.y = y;
		this.setSize(width, height);
		this.activateName = activateName;
		this.layer = 2;
		this.scoreReq = scoreReq;
		teal = new Color(60, 177, 188);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(teal);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		
		if (isTriggered) {
			g.setColor(Color.green);
			for (Rectangle r : pipes) {
				float tWidth = r.getWidth();
				float tX = r.getX();
				if (r.getX() > width - r.getWidth()) {
					tWidth = width - r.getX();
				} else if (r.getX() < 0) {
					tX = 0;
					tWidth = r.getWidth() + r.getX();
				}
				g.fillRect(x + offsetX + tX, y + offsetY + r.getY(), tWidth, r.getHeight());
			}
			
			g.setColor(Color.yellow);
			g.fillRect(x + offsetX + bird.getX(), y + offsetY + bird.getY(), bird.getWidth(), bird.getHeight());
			
			g.setColor(Color.white);
			g.drawString("score: " + score, offsetX + x + 20, offsetY + y + 20);
			if (!started) {
				g.setFont(GUI.getFont());
				g.drawString("Press space", x + offsetX + width / 2f - GUI.getFont().getWidth("Press space") / 2f, y + offsetY + 100);
			}
		}
	}

	@Override
	public int getID() {
		return 24;
	}
	
	@Override
	public String toString() {
		return getID() + "," + x + "," + y + "," + width + "," + height + "," + activateName + "," + scoreReq;
	}

	@Override
	public void start() {
		score = 0;
		pipes = new LinkedList<Rectangle>();
		bird = new Rectangle(height / 4f, width / 2f - 25, 50, 50);
		timer = 0;
		yVel = 0;
		started = false;
	}

	@Override
	public void end() {
		Player p = LevelManager.getLevel().getPlayer();
		p.setX(x + width + 50);
		p.setY(y + height - p.getHeight());
		isDone = true;
		for (Entity e : LevelManager.getLevel().getObjects()) {
			if (e instanceof MovableTerrain && e.getName().equals(activateName)) {
				((MovableTerrain) e).activate();
			}
		}
	}

	@Override
	public void update(Input input, Player player) {
		
		if (!started) {
			if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("jump")) || InputSettings.isButtonPressed(input, "jump")) {
				started = true;
			} else {
				return;
			}
		}
		
		// Collision
		for (Rectangle r : pipes) {
			if (r.intersects(bird)) {
				player.takeDamage(player.getMaxHealth());
				player.setMinigame(null);
			}
		}
		
		// Controls
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("jump")) || InputSettings.isButtonPressed(input, "jump")) {
			yVel = jumpVel;
		}
			
		// Movement
		for (Rectangle r : pipes) {
			r.setX(r.getX() - xVel * TimeInfo.getDelta());
		}
		
		// Remove passed pipes
		if (!pipes.isEmpty() && pipes.getFirst().getX() < -pipes.getFirst().getWidth()) {
			pipes.removeFirst();
			pipes.removeFirst();
		}
		bird.setY(bird.getY() + yVel * TimeInfo.getDelta());
		yVel += yAcc;
		if (yVel > MAX_Y_VEL) {
			yVel = MAX_Y_VEL;
		}
		if (bird.getY() + bird.getHeight() > height || bird.getY() < 0) {
			player.takeDamage(player.getMaxHealth());
			player.setMinigame(null);
		}
		
		// Pipes
		timer += TimeInfo.getDelta();
		if (timer > PIPE_TIME) {
			timer = 0;
			score++;
			if (score > scoreReq) {
				end();
			}
			float pass = (float) (Math.random() * (height - PIPE_GAP) + PIPE_GAP / 2f);
			Rectangle top = new Rectangle(width, 0, 50, pass - PIPE_GAP / 2f);
			Rectangle bottom = new Rectangle(width, pass + PIPE_GAP / 2f, 50, height - (pass + PIPE_GAP / 2f));
			pipes.addLast(top);
			pipes.addLast(bottom);
		}
	}
}
