package de.raytracing.raytracer.traceobjects.modifier;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

public class Translate extends MaterialObject implements TraceObject {

	private final TraceObject object;
	private final Vector diff;


	public Translate(Vector diff, TraceObject object) {
		this.object = object;
		this.diff = diff;
	}

	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		final Ray modray = new Ray(ray.start.sub(diff), ray.dir);

		return propagateMaterials(object.getCutPoints(modray));
	}

	@Override
	public boolean isInside(final Vector point) {
		final Vector modpoint = point.sub(diff);
		return object.isInside(modpoint);
	}

	public static Translate move(double x, double y, double z, TraceObject object) {
		return new Translate(new Vector(x, y, z), object);
	}

	public static Translate moveX(double diff, TraceObject object) {
		return move(diff, 0.0, 0.0, object);
	}

	public static Translate moveY(double diff, TraceObject object) {
		return move(0.0, diff, 0.0, object);
	}

	public static Translate moveZ(double diff, TraceObject object) {
		return move(0.0, 0.0, diff, object);
	}

}
