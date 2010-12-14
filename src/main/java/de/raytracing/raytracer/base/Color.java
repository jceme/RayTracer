package de.raytracing.raytracer.base;


public class Color implements Approximate<Color> {

	public static final Color Red = new Color(1.0, 0.0, 0.0);
	public static final Color Green = new Color(0.0, 1.0, 0.0);
	public static final Color Blue = new Color(0.0, 0.0, 1.0);
	public static final Color White = new Color(1.0, 1.0, 1.0);
	public static final Color Black = new Color(0.0, 0.0, 0.0);
	public static final Color Yellow = new Color(1.0, 1.0, 0.0);


	private final Vector color;


	public Color(double red, double green, double blue) {
		this(new Vector(red, green, blue));
	}

	public Color(double grayValue) {
		this(grayValue, grayValue, grayValue);
	}

	protected Color(Vector color) {
		this.color = new Vector(
				// Cut off over-scaled values
				Math.max(0.0, Math.min(1.0, color.x)),
				Math.max(0.0, Math.min(1.0, color.y)),
				Math.max(0.0, Math.min(1.0, color.z))
		);
	}


	public Color add(Color color) {
		return new Color(this.color.add(color.color));
	}

	public Color multiply(Color color) {
		return new Color(this.color.vectorMultiply(color.color));
	}


	public Color multiply(double value) {
		return new Color(this.color.multiply(value));
	}

	public java.awt.Color getAwtColor() {
		return new java.awt.Color(getColor(color.x), getColor(color.y), getColor(color.z));
	}


	private float getColor(double value) {
		return Math.max(0.0f, Math.min(1.0f, (float) value));
	}

	@Override
	public boolean approxEquals(Color color) {
		return this.color.approxEquals(color.color);
	}

	@Override
	public String toString() {
		return String.format("Color[red=%.2f, green=%.2f, blue=%.2f]", color.x, color.y, color.z);
	}


	public static Color multiplyColors(double factor, Color color, Color... colors) {
		Vector col = color.color;

		for (Color c : colors) {
			col = col.vectorMultiply(c.color);
		}

		return new Color(col.multiply(factor));
	}

}
