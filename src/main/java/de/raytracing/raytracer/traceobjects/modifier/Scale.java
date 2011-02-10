package de.raytracing.raytracer.traceobjects.modifier;

import static de.raytracing.raytracer.util.GeometryUtils.isZero;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

public class Scale extends MaterialObject implements TraceObject {

	private final TraceObject object;
	private final Vector scale;

	public Scale(Vector scale, TraceObject object) {
		if (isZero(scale.x) || isZero(scale.y) || isZero(scale.z)) {
			throw new IllegalArgumentException("Invalid scale");
		}

		this.object = object;
		this.scale = scale;
	}

	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		final Ray modray = new Ray(ray.start.vectorDivision(scale), ray.dir);

		return propagateMaterials(object.getCutPoints(modray));
	}

	@Override
	public boolean isInside(final Vector point) {
		return object.isInside(point.vectorDivision(scale));
	}


	public static Scale scaleX(double scale, TraceObject object) {
		return new Scale(new Vector(scale, 1, 1), object);
	}

	public static Scale scaleY(double scale, TraceObject object) {
		return new Scale(new Vector(1, scale, 1), object);
	}

	public static Scale scaleZ(double scale, TraceObject object) {
		return new Scale(new Vector(1, 1, scale), object);
	}

}
