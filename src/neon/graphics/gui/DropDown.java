package neon.graphics.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class DropDown extends GUIElement {
	
	private VBox items;
	private Item selectedItem;
	private boolean expanded = false;
	private Button expandBtn;
	
	public DropDown(float width, float height) {
		this.width = width;
		this.height = height;
		items = new VBox();
		expandBtn = new Button("v", 14, height, height);
		expandBtn.x = x + width;
		expandBtn.y = y;
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		g.setColor(Color.white);
		g.fillRect(x + offsetX, y + offsetY, width, height);
		
		if (selectedItem != null) {
			g.setColor(Color.black);
			float textX = width / 2 - GUI.getFont().getWidth(selectedItem.getText()) / 2;
			float textY = height / 2 - GUI.getFont().getHeight(selectedItem.getText()) / 2;
			g.drawString(selectedItem.getText(), x + offsetX + textX, y + offsetY + textY);
		}
		
		expandBtn.render(g, x + offsetX, y + offsetY);
		
		if (expanded) {
			items.render(g, x + offsetX, y + height + offsetY);
		}
	}
	
	@Override
	public void update(Input input, float offsetX, float offsetY, float scale) {
		expandBtn.update(input, x + offsetX, y + offsetY, scale);
		if (expandBtn.wasClicked) {
			expanded = !expanded;
		}
		if (expanded) {
			items.update(input, x + offsetX, y + height + offsetY, scale);
			for (GUIElement i : items.getChildren()) {
				if (i.wasClicked) {
					selectedItem = (Item) i;
					expanded = false;
					break;
				}
			}
		}
		super.update(input, offsetX, offsetY, scale);
	}
	
	
	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void addItem(String text) {
		Item i = new Item(text, 14, width, height);
		items.getChildren().add(i);
	}
}
