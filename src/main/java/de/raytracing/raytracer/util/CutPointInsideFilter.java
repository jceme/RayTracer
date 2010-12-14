package de.raytracing.raytracer.util;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.GeometryUtils.Filter;

public class CutPointInsideFilter implements Filter<CutPoint> {

	private TraceObject[] objects;
	private final Ray ray;
	private TraceObject ignore;


	public CutPointInsideFilter(Ray ray, TraceObject... objects) {
		this.ray = ray;
		this.objects = objects;
	}

	public void ignore(TraceObject obj) {
		this.ignore = obj;
	}

	@Override
	public boolean accept(final CutPoint cutPoint) {
		final Vector point = cutPoint.getCutPoint(ray);

		for (TraceObject obj : objects) {
			if ((ignore == null || obj != ignore) && test(obj, point)) {
				return false;
			}
		}

		return true;
	}

	protected boolean test(TraceObject object, Vector point) {
		return object.isInside(point);
	}

}