package neon.entity.area;

import org.newdawn.slick.Graphics;

public class InputPromptTrigger extends Trigger {
	
	protected String input;
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public String getInput() {
		return input;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		if (isTriggered) {
			g.drawString(this.text, offsetX + x, offsetY + y - 100);
		}
	}

	@Override
	public int getID() {
		return -1;
	}

	@Override
	public void triggered() {
		this.isTriggered = true;
	}

	@Override
	public void setTrigger(String text, float textX, float textY) {
		this.text = text;
	}

	@Override
	public void unTriggered() {
		this.isTriggered = false;
	}
}
