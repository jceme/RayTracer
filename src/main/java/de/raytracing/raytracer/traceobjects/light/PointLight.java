package de.raytracing.raytracer.traceobjects.light;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Vector;

public class PointLight extends BaseLight {

	public PointLight(Color color, Vector location) {
		super(color, location);
	}

	public PointLight(Vector location) {
		this(Color.White, location);
	}

	@Override
	public String toString() {
		return "PointLight[at "+getLightLocation()+" with material "+getMaterial()+"]";
	}

}
