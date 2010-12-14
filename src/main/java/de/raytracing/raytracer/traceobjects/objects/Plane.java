package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.util.GeometryUtils.isZero;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

public class Plane extends MaterialObject implements TraceObject {

	private final Vector normal;
	private final Vector point;


	public Plane(Vector normal, Vector point) {
		if (normal.isZero()) throw new IllegalArgumentException("Invalid normal vector");

		this.normal = normal.normalized();
		this.point  = point;
	}

	public Plane(Vector normal, double dist) {
		this(normal, normal.normalized().multiply(dist));
	}

	public Plane(Vector normal) {
		this(normal, 0.0);
	}

	public Plane(Vector point, Vector p2, Vector p3) {
		this(p2.sub(point).cross(p3.sub(point)), point);
	}


	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		double vn = normal.dot(ray.dir);

		if (isZero(vn)) return new CutPoint[0];

		double pn = normal.dot(point);
		double sn = normal.dot(ray.start);

		double dist = (pn - sn) / vn;
		//Vector normal = getNormal(ray.getPoint(dist));

		return new CutPoint[] { propagateMaterial(new CutPoint(dist, normal, this)) };
	}

	/**
	 * Creates a wave normal texture on the plane surface
	 * @param point
	 * @return
	 */
	@SuppressWarnings("unused")
	private Vector getNormal(Vector point) {
		point = point.vectorMultiply(new Vector(30, 1, 8)).add(new Vector(0, 0, -20));

		double u = point.x;
		double v = point.z;
		double p = 1;
		double phi = Math.PI;

		double root = Math.sqrt(u*u + v*v);
		double factor = -p * Math.sin(root + phi) / root;

		double vu = factor * u;
		double vv = factor * v;

		Vector normal = new Vector(vu, 1, vv);
		return normal;
	}

	@Override
	public boolean isInside(final Vector point) {
		double angleCos = normal.dot(point.sub(this.point));
		return angleCos <= 0.0;
	}


	@Override
	public String toString() {
		return "Plane[normal="+normal+", point="+point+"]";
	}

}
