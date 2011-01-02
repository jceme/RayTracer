package de.raytracing.raytracer.base;

public class ColoredRay {

	private final Ray ray;
	private final Color color;


	public ColoredRay(Ray ray, Color color) {
		if (ray == null) throw new IllegalArgumentException("Ray required");
		if (color == null) throw new IllegalArgumentException("Color required");

		this.ray = ray;
		this.color = color;
	}

	public Ray getRay() {
		return ray;
	}

	public Color getColor() {
		return color;
	}

}
