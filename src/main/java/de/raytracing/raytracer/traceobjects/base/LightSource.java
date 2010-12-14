package de.raytracing.raytracer.traceobjects.base;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.material.Materialed;
import de.raytracing.raytracer.traceobjects.base.material.SimpleMaterial;

public interface LightSource extends Materialed<SimpleMaterial> {

	public Vector getLightLocation();

	public Color calcLight(Vector target, Raytracer scene);

	public Vector getDirection(Vector target);

}
