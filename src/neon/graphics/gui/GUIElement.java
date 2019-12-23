package neon.graphics.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public abstract class GUIElement {
	
	protected Color color;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float spacing;
	protected float paddingY;
	protected float paddingX;

	protected boolean isMouseOver;
	protected boolean wasClicked;
	
	protected ArrayList<GUIElement> children;
	
	public GUIElement() {
		children = new ArrayList<GUIElement>();
		color = Color.white;
	}
	
	public abstract void render(Graphics g, float offsetX, float offsetY);
	
	public void update(Input input, float offsetX, float offsetY, float scale) {
		Rectangle r = new Rectangle(scale * (offsetX + x), scale * (offsetY + y), scale * width, scale * height);
		if (r.contains(input.getMouseX(), input.getMouseY())) {
			isMouseOver = true;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				wasClicked = true;
			} else {
				wasClicked = false;
			}
		} else {
			isMouseOver = false;
			wasClicked = false;
		}
	}

	public float getPaddingY() {
		return paddingY;
	}

	public void setPaddingY(float paddingY) {
		this.paddingY = paddingY;
	}

	public float getPaddingX() {
		return paddingX;
	}

	public void setPaddingX(float paddingX) {
		this.paddingX = paddingX;
	}

	public float getSpacing() {
		return spacing;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isMouseOver() {
		return isMouseOver;
	}
	
	public boolean wasClicked() {
		return wasClicked;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public ArrayList<GUIElement> getChildren() {
		return children;
	}
}
