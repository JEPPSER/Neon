package neon.graphics;

import org.newdawn.slick.Color;
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

	public static void setColorHue(Color color, float hue) {
		float[] hsl = rgbToHSL(color);
		hsl[0] = hue;
		if (hsl[0] > 360) {
			hsl[0] -= 360;
		} else if (hsl[0] < 0) {
			hsl[0] += 360;
		}
		float[] rgb = hslToRGB(hsl);
		color.r = rgb[0];
		color.g = rgb[1];
		color.b = rgb[2];
	}

	public static float[] rgbToHSL(Color color) {
		float r = color.r;
		float g = color.g;
		float b = color.b;
		float cMax = max(r, g, b);
		float cMin = min(r, g, b);
		float delta = cMax - cMin;
		float l = (cMax + cMin) / 2;
		float h = 0;
		float s = 0;

		if (delta == 0) {
			h = 0;
		} else if (cMax == r) {
			h = 60 * (((g - b) / delta) % 6);
		} else if (cMax == g) {
			h = 60 * (((b - r) / delta) + 2);
		} else {
			h = 60 * (((r - g) / delta) + 4);
		}

		if (delta == 0) {
			s = 0;
		} else {
			s = (delta / (1 - Math.abs(2 * l - 1)));
		}

		return new float[] { h, s, l };
	}

	public static float[] hslToRGB(float[] color) {
		float h = color[0];
		float s = color[1];
		float l = color[2];
		float c = (1 - Math.abs(2 * l - 1)) * s;
		float x = c * (1 - Math.abs((h / 60) % 2 - 1));
		float m = l - c / 2;

		float r, g, b;

		if (h < 60) {
			r = c;
			g = x;
			b = 0;
		} else if (h < 120) {
			r = x;
			g = c;
			b = 0;
		} else if (h < 180) {
			r = 0;
			g = c;
			b = x;
		} else if (h < 240) {
			r = 0;
			g = x;
			b = c;
		} else if (h < 300) {
			r = x;
			g = 0;
			b = c;
		} else {
			r = c;
			g = 0;
			b = x;
		}
		
		r = normalizeRgbValue(r, m);
		g = normalizeRgbValue(g, m);
		b = normalizeRgbValue(b, m);
		
		return new float[] {r, g, b};
	}
	
	private static float normalizeRgbValue(float color, float m) {
		color += m;
		if (color < 0) {
			color = 0;
		}
		return color;
	}

	private static float max(float a, float b, float c) {
		float max = a;
		if (b > max) {
			max = b;
		}
		if (c > max) {
			max = c;
		}
		return max;
	}

	private static float min(float a, float b, float c) {
		float max = a;
		if (b < max) {
			max = b;
		}
		if (c < max) {
			max = c;
		}
		return max;
	}
}
