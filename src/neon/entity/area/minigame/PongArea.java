package neon.entity.area.minigame;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import neon.entity.Entity;
import neon.entity.controllable.Player;
import neon.entity.terrain.movable.MovableTerrain;
import neon.level.LevelManager;
import neon.physics.Physics;
import neon.settings.InputSettings;
import neon.time.TimeInfo;

public class PongArea extends MinigameArea {

	private String activateName;
	private final float BALL_SPEED = 0.5f;
	private float yVel;
	private float xVel;
	private final float PLAYER_SPEED = 0.4f;
	private final float OPPONENT_SPEED = 0.3f;
	private Rectangle player;
	private Rectangle opponent;
	private Rectangle ball;
	private boolean paused = false;
	private float timer = 0;

	public PongArea(float x, float y, float width, float height, String activateName) {
		this.name = "PongArea";
		this.physics = new Physics(0f, 0f);
		this.x = x;
		this.y = y;
		this.setSize(width, height);
		this.activateName = activateName;
		this.layer = 2;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.fillRect(offsetX + x, offsetY + y, width, height);

		if (isTriggered) {
			g.setColor(Color.black);
			g.fillRect(offsetX + x + player.getX(), offsetY + y + player.getY(), player.getWidth(), player.getHeight());
			g.fillRect(offsetX + x + opponent.getX(), offsetY + y + opponent.getY(), opponent.getWidth(),
					opponent.getHeight());
			g.fillRect(offsetX + x + ball.getX(), offsetY + y + ball.getY(), ball.getWidth(), ball.getHeight());
		}
	}

	@Override
	public String toString() {
		String str = getID() + "," + x + "," + y + "," + width + "," + height + "," + activateName;
		return str;
	}

	@Override
	public int getID() {
		return 23;
	}

	@Override
	public void start() {
		player = new Rectangle(25f, height / 2f - 50f, 25f, 100f);
		opponent = new Rectangle(width - 25f - 25f, height / 2f - 50f, 25f, 100f);
		ball = new Rectangle(50f, height / 2f - 12.5f, 25f, 25f);
		yVel = 0;
		xVel = BALL_SPEED;
	}

	@Override
	public void end() {
		Player p = LevelManager.getLevel().getPlayer();
		p.setX(x + width + 50);
		p.setY(y + height - p.getHeight());
		isDone = true;
	}

	@Override
	public void update(Input input, Player player) {
		if (paused) {
			timer += TimeInfo.getDelta();
			if (timer > 1000) {
				timer = 0;
				paused = false;
				start();
			}
			return;
		}
		
		ball.setY(ball.getY() + yVel * TimeInfo.getDelta());
		ball.setX(ball.getX() + xVel * TimeInfo.getDelta());

		// Collision
		if (ball.getY() + ball.getHeight() > height && yVel > 0 || ball.getY() < 0 && yVel < 0) {
			yVel *= -1;
		}

		if (ball.getX() < 0) {
			player.takeDamage(player.getMaxHealth());
			player.setMinigame(null);
			LevelManager.getLevel().getCamera().setFocalPoint(null);
		} else if (ball.getX() + ball.getWidth() > width) {
			activate();
			paused = true;
		}

		if (ball.intersects(this.player) && ball.getX() > this.player.getX()) {
			xVel = BALL_SPEED;
			yVel += (ball.getY() + ball.getHeight() / 2f - this.player.getY() - this.player.getHeight() / 2f) * 0.005f;
		}
		if (ball.intersects(this.opponent) && ball.getX() < this.opponent.getX()) {
			xVel = -BALL_SPEED;
			yVel += (ball.getY() + ball.getHeight() / 2f - opponent.getY() - opponent.getHeight() / 2f) * 0.005f;
		}
		if (yVel > BALL_SPEED) {
			yVel = BALL_SPEED;
		} else if (yVel < -BALL_SPEED) {
			yVel = -BALL_SPEED;
		}

		// Player movement
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("up")) || InputSettings.isButtonDown(input, "up")) {
			if (this.player.getY() > 0) {
				this.player.setY(this.player.getY() - PLAYER_SPEED * TimeInfo.getDelta());
			}
		}
		if (input.isKeyDown(InputSettings.getKeyboardBinds().get("down"))
				|| InputSettings.isButtonDown(input, "down")) {
			if (this.player.getY() + this.player.getHeight() < height) {
				this.player.setY(this.player.getY() + PLAYER_SPEED * TimeInfo.getDelta());
			}
		}

		// Opponent movement
		if (ball.getY() + ball.getHeight() / 2f > opponent.getY() + opponent.getHeight() / 2f) {
			opponent.setY(opponent.getY() + OPPONENT_SPEED * TimeInfo.getDelta());
		} else {
			opponent.setY(opponent.getY() - OPPONENT_SPEED * TimeInfo.getDelta());
		}
	}

	private void activate() {
		ArrayList<Entity> objects = LevelManager.getLevel().getObjects();
		int count = 0;
		for (Entity e : objects) {
			if (e instanceof MovableTerrain && !((MovableTerrain) e).isActive() && e.getName().equals(activateName)) {
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
}
