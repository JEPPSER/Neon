package neon.entity.controllable;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import neon.controller.PlayerController;
import neon.graphics.EntityGraphics;
import neon.graphics.Point;
import neon.physics.Physics;
import neon.physics.Collision;

public class Player extends ControllableEntity {
	
	private EntityGraphics graphics;
	private String name;
	private float x = 300;
	private float y = 100;
	
	public Player() {
		name = "Player";
		initGraphics();
		this.controller = new PlayerController(this);
		this.physics = new Physics(0f, 0.5f);
		this.collision = new Collision(new Rectangle(0, 0, 20, 60), 1.0f, 10f, true);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public EntityGraphics getGraphics() {
		return graphics;
	}

	@Override
	public void render(Graphics g) {
		graphics.render(g, this.x, this.y);
	}
	
	private void initGraphics() {
		this.graphics = new EntityGraphics();
		this.graphics.setColor(Color.red);
		this.graphics.setLineWidth(2.0f);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(20, 0));
		points.add(new Point(20, 60));
		points.add(new Point(0, 60));
		points.add(new Point(0, 0));
		this.graphics.setPoints(points);
	}
}
