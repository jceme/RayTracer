package de.raytracing.raytracer.base;

public class Ray {

	public final Vector start;
	public final Vector dir;

	public Ray(Vector start, Vector dir) {
		this.start = start;
		this.dir = dir.normalized();
	}

	public Vector getPoint(double dist) {
		return start.add(dir.multiply(dist));
	}

	@Override
	public String toString() {
		return "Ray[from "+start+" heading "+dir+"]";
	}

}
