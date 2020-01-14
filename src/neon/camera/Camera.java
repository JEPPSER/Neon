package neon.camera;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import neon.entity.Entity;
import neon.entity.PhysicalEntity;
import neon.entity.area.Trigger;
import neon.entity.controllable.Player;
import neon.graphics.Point;
import neon.level.Level;
import neon.level.LevelManager;
import neon.overworld.OverworldModel;
import neon.overworld.entity.World;

public class Camera {

	private PhysicalEntity focusedEntity;
	private GameContainer gc;
	private float cameraOffsetX;
	private float cameraOffsetY;
	private float offsetX;
	private float offsetY;
	private Point focalPoint;
	private float yScrollValue = 0.3f;
	private float overworldScrollValue = 0.2f;
	private float scale = 1.0f;
	
	private Image gradient;

	public Camera(PhysicalEntity focusedEntity, GameContainer gc) {
		this.focusedEntity = focusedEntity;
		offsetX = focusedEntity.getX() * -1 + gc.getWidth() / 2;
		offsetY = focusedEntity.getY() * -1 + gc.getHeight() / 2;
		this.gc = gc;
		try {
			gradient = new Image("res/images/gradient.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void pan(float x, float y) {
		cameraOffsetX += x;
		cameraOffsetY += y;
	}

	public void setPan(float x, float y) {
		cameraOffsetX = x;
		cameraOffsetY = y;
	}

	public void setFocusedEntity(PhysicalEntity focusedEntity) {
		this.focusedEntity = focusedEntity;
	}

	public PhysicalEntity getFocusedEntity() {
		return this.focusedEntity;
	}

	public void renderOverworld(OverworldModel owm, Graphics g) {
		g.scale(scale, scale);

		float focalX;
		float focalY;
		if (focalPoint == null) {
			focalX = focusedEntity.getX();
			focalY = focusedEntity.getY();
		} else {
			focalX = focalPoint.getX();
			focalY = focalPoint.getY();
		}

		float y = offsetY + focalY;
		float x = offsetX + focalX;
		float height = focusedEntity.getHeight();
		float width = focusedEntity.getWidth();

		if (focalPoint == null) {
			// Scroll screen vertically
			if (y > gc.getHeight() / scale - height - gc.getHeight() / scale * overworldScrollValue) {
				offsetY = -focalY + gc.getHeight() / scale - height - gc.getHeight() / scale * overworldScrollValue;
			} else if (y < gc.getHeight() / scale * overworldScrollValue) {
				offsetY = -focalY + gc.getHeight() / scale * overworldScrollValue;
			}

			// Scroll screen horizontally
			if (x > gc.getWidth() / scale - width - gc.getWidth() / scale * overworldScrollValue) {
				offsetX = -focalX + gc.getWidth() / scale - width - gc.getWidth() / scale * overworldScrollValue;
			} else if (x < gc.getWidth() / scale * overworldScrollValue) {
				offsetX = -focalX + gc.getWidth() / scale * overworldScrollValue;
			}
		} else {
			offsetY = focalY * -1 + gc.getHeight() / (2f * scale);
			offsetX = focalX * -1 + gc.getWidth() / (2f * scale) - focusedEntity.getWidth() / 2;
		}

		g.drawImage(owm.getBackground().getScaledCopy(2), cameraOffsetX + offsetX, cameraOffsetY + offsetY);
		for (int i = 0; i < owm.getWorlds().size(); i++) {
			World w = owm.getWorlds().get(i);
			w.render(g, cameraOffsetX + offsetX, cameraOffsetY + offsetY);
		}

		focusedEntity.render(g, cameraOffsetX + offsetX, cameraOffsetY + offsetY);
	}

	public void renderPlayField(Level level, Graphics g) {
		if (focusedEntity == null) {
			focusedEntity = LevelManager.getLevel().getPlayer();
		}
		
		// Background
		g.setColor(new Color(10, 20, 0));
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

		g.scale(scale, scale);

		float focalX;
		float focalY;
		if (focalPoint == null) {
			focalX = focusedEntity.getX();
			focalY = focusedEntity.getY();
		} else {
			focalX = focalPoint.getX();
			focalY = focalPoint.getY();
		}

		offsetX = focalX * -1 + gc.getWidth() / (2f * scale) - focusedEntity.getWidth() / 2;
		float y = offsetY + focalY;
		float height = focusedEntity.getHeight();

		if (focalPoint == null) {
			// Scroll screen vertically
			if (y > gc.getHeight() / scale - height - gc.getHeight() / scale * yScrollValue) {
				offsetY = -focalY + gc.getHeight() / scale - height - gc.getHeight() / scale * yScrollValue;
			} else if (y < gc.getHeight() / scale * yScrollValue) {
				offsetY = -focalY + gc.getHeight() / scale * yScrollValue;
			}
		} else {
			offsetY = focalY * -1 + gc.getHeight() / (2f * scale);
		}

		float eX = (float) (Math.round(scale * (cameraOffsetX + offsetX)) / scale);
		float eY = (float) (Math.round(scale * (cameraOffsetY + offsetY)) / scale);
		
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < level.getObjects().size(); i++) {
				Entity e = level.getObjects().get(i);
				if (isInView(e) && e instanceof PhysicalEntity && !(e instanceof Trigger)) {
					PhysicalEntity pe = (PhysicalEntity) e;
					if (pe.getLayer() == j) {
						pe.render(g, eX, eY);
					}
				}
			}
		}
		
		// Cover outside of borders
		g.setColor(Color.black);
		g.fillRect(0, 0, eX - 50, gc.getHeight() / scale);
		g.fillRect(0, 0, gc.getWidth() / scale, eY - 50);
		g.fillRect(0, eY + level.getHeight() + 50, gc.getWidth() / scale, gc.getHeight() / scale - (eY + level.getHeight()));
		g.fillRect(eX + level.getWidth() + 50, 0, gc.getWidth() / scale - (eX + level.getWidth()), gc.getHeight() / scale);
		
		float yScale = (gc.getHeight() / scale) / 50f;
		float xScale = (gc.getWidth() / scale) / 50f;
		
		g.scale(1, yScale);
		g.drawImage(gradient, eX - 50, 0);
		gradient.rotate(180);
		g.drawImage(gradient, level.getWidth() + eX, 0);
		g.scale(1, 1 / yScale);
		
		gradient.rotate(90);
		g.scale(xScale, 1);
		g.drawImage(gradient, 0, level.getHeight() + eY);
		gradient.rotate(-180);
		g.drawImage(gradient, 0, eY - 50);
		g.scale(1 / xScale, 1);
		gradient.rotate(-90);
		
		for (Entity e : level.getObjects()) {
			if (e instanceof Trigger) {
				((Trigger) e).setScale(scale);
				e.render(g, eX, eY);
			}
		}
	}

	public void renderStaticElements(GameContainer gc, Graphics g) {
		Player p = LevelManager.getLevel().getPlayer();
		float dify = gc.getHeight() - Display.getHeight();
		float x = (float) Display.getWidth() / scale / 2f - 100;
		float y = dify + 20;
		g.setColor(Color.white);
		g.drawRect(x, y, 200, 30);
		g.setColor(Color.green);
		g.fillRect(x + 2, y + 2, (p.getHealth() / p.getMaxHealth()) * 200 - 4, 30 - 4);
		g.resetFont();
		g.drawString("FPS: " + String.valueOf(gc.getFPS()), 10, y);
	}

	public void zoom(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	public void setFocalPoint(Point focalPoint) {
		this.focalPoint = focalPoint;
	}

	private boolean isInView(Entity entity) {
		if (entity.getX() + offsetX + cameraOffsetX + entity.getWidth() > 0
				&& entity.getX() + offsetX + cameraOffsetX < gc.getWidth() / scale
				&& entity.getY() + offsetY + cameraOffsetY + entity.getHeight() > 0
				&& entity.getY() + offsetY + cameraOffsetY < gc.getHeight() / scale) {
			return true;
		}
		return false;
	}
}
