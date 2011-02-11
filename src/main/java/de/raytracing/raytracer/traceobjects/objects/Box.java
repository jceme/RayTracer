package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.base.Vector.X_AXIS;
import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import static de.raytracing.raytracer.base.Vector.Z_AXIS;
import static de.raytracing.raytracer.util.GeometryUtils.isZero;
import static java.lang.Math.max;
import static java.lang.Math.min;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Intersection;

public class Box extends Intersection {

	public Box(Vector p1, Vector p2) {
		super(getPlanes(p1, p2));
	}

	public Box(Vector point) {
		this(point.multiply(0.5), point.multiply(0.5).neg());
	}

	public Box(double size) {
		this(Vector.ONE.multiply(size));
	}

	public Box() {
		this(1.0);
	}


	private static TraceObject[] getPlanes(Vector p1, Vector p2) {
		if (approxComponentEquals(p1.sub(p2))) {
			throw new IllegalArgumentException("Invalid box points");
		}

		Plane[] sides = new Plane[] {
			// right
			new Plane(X_AXIS, max(p1.x, p2.x)),
			// left
			new Plane(X_AXIS.neg(), -min(p1.x, p2.x)),
			// top
			new Plane(Y_AXIS, max(p1.y, p2.y)),
			// bottom
			new Plane(Y_AXIS.neg(), -min(p1.y, p2.y)),
			// back
			new Plane(Z_AXIS, max(p1.z, p2.z)),
			// front
			new Plane(Z_AXIS.neg(), -min(p1.z, p2.z))
		};

		return sides;
	}

	private static boolean approxComponentEquals(Vector v) {
		return isZero(v.x) || isZero(v.y) || isZero(v.z);
	}

}
