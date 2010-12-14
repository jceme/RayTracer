package de.raytracing.raytracer.traceobjects.base;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.base.material.Materialed;

public interface TraceObject extends Materialed<Material> {

	public CutPoint[] getCutPoints(Ray ray);

	public boolean isInside(Vector point);

}
