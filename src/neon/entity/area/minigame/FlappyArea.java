package neon.entity.area.minigame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.controllable.Player;
import neon.physics.Physics;
import neon.settings.InputSettings;
import neon.time.TimeInfo;

public class FlappyArea extends MinigameArea {
	
	private int scoreReq;
	private String activateName;
	private float xVel = 0.5f;
	private float yVel = 0;
	private float yAcc = 0.002f;
	private float jumpVel = -0.3f;
	private ArrayList<Rectangle> pipes;
	private Rectangle bird;
	private int score;
	private Color teal;
	
	private int timer = 0;
	private final int PIPE_TIME = 1000;
	
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
				g.fillRect(x + offsetX + r.getX(), y + offsetY + r.getY(), r.getWidth(), r.getHeight());
			}
			
			g.setColor(Color.yellow);
			g.fillRect(x + offsetX + bird.getX(), y + offsetY + bird.getY(), bird.getWidth(), bird.getHeight());
		}
	}

	@Override
	public int getID() {
		return 24;
	}
	
	@Override
	public String toString() {
		return "";
	}

	@Override
	public void start() {
		score = 0;
		pipes = new ArrayList<Rectangle>();
		bird = new Rectangle(height / 2f - 25, width / 2f - 25, 50, 50);
		timer = 0;
	}

	@Override
	public void end() {

	}

	@Override
	public void update(Input input, Player player) {
		
		// Collision
		for (Rectangle r : pipes) {
			if (r.intersects(bird)) {
				player.takeDamage(player.getMaxHealth());
				player.setMinigame(null);
			}
		}
		
		// Controls
		if (input.isKeyPressed(InputSettings.getKeyboardBinds().get("jump")) || InputSettings.isButtonPressed(input, "jump")) {
			yVel = TimeInfo.getDelta() * jumpVel;
		}
			
		// Movement
		for (Rectangle r : pipes) {
			r.setX(r.getX() - xVel * TimeInfo.getDelta());
		}
		bird.setY(bird.getY() + yVel * TimeInfo.getDelta());
		yVel += yAcc * TimeInfo.getDelta();
		
		// Pipes
		timer += TimeInfo.getDelta();
		if (timer > PIPE_TIME) {
			timer = 0;
			Rectangle top = new Rectangle(width, 0, 50, 150);
			Rectangle bottom = new Rectangle(width, height - 150, 50, 150);
			pipes.add(top);
			pipes.add(bottom);
		}
	}
}
