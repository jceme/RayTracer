package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import static de.raytracing.raytracer.util.GeometryUtils.isLowerEqZero;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.GeometryUtils;
import de.raytracing.raytracer.util.GeometryUtils.Filter;

public class Cone extends MaterialObject implements TraceObject {

	private final double radius;
	private final double height;


	public Cone(double radius, double height) {
		if (isLowerEqZero(radius)) throw new IllegalArgumentException("Invalid radius");
		if (isLowerEqZero(height)) throw new IllegalArgumentException("Invalid height");

		this.radius = radius;
		this.height = height;
	}


	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		Vector s = ray.start.planar(Y_AXIS);
		Vector v = ray.dir.planar(Y_AXIS);
		double sy = ray.start.y;
		double vy = ray.dir.y;

		double r2 = radius * radius;
		double h2 = height * height;
		double b = r2 * vy * (height - sy) + h2 * s.dot(v);

		double p = h2*v.lengthSqr() - r2*vy*vy;

		double rad = b*b + r2*sy*(sy-2.0*height) + h2*(r2 - s.lengthSqr()) * p;

		if (rad < 0.0 || GeometryUtils.isZero(p)) {
			return new CutPoint[0];
		}

		rad = Math.sqrt(rad);

		CutPoint[] points = new CutPoint[] {
				getCutPoint(ray, (b + rad) / (-p)),
				getCutPoint(ray, (b - rad) / (-p))
		};

		points = GeometryUtils.filterList(points, new Filter<CutPoint>() {
			@Override
			public boolean accept(CutPoint point) {
				double y = point.getCutPoint(ray).y;

				return y >= 0.0 && y <= height;
			}
		});

		return GeometryUtils.collectApproxEquals(new CutPoint[][] { points });
	}

	private CutPoint getCutPoint(Ray ray, double dist) {
		Vector p = ray.getPoint(dist).planar(Y_AXIS);

		Vector normal = new Vector(p.x, p.length() * radius / height, p.z);

		return propagateMaterial(new CutPoint(dist, normal, this));
	}

	@Override
	public boolean isInside(final Vector point) {
		return point.y >= 0.0 && point.y <= height &&
				point.planar(Y_AXIS).length() <= radius * (1.0 - point.y / height);
	}

}
