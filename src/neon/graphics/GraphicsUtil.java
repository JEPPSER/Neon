package neon.graphics;

import org.newdawn.slick.Image;

public class GraphicsUtil {

	public static void drawImageMatrix(Image[][] matrix, float x, float y, float scale) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != null) {
					float width = matrix[i][j].getWidth();
					float height = matrix[i][j].getHeight();
					matrix[i][j].draw(x + i * scale * width, y + j * scale * height, scale);
				}
			}
		}
	}
}
