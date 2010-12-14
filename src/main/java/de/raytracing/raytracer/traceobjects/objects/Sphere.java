package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.util.GeometryUtils.isLowerEqZero;
import static java.lang.Math.sqrt;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.GeometryUtils;

/**
 * A sphere object in the coordinate center.
 *
 * @author daniel
 */
public class Sphere extends MaterialObject implements TraceObject {

	private final double radiusSqr;
	//private final double innerRadiusSqr;

	public Sphere() {
		this(1.0);
	}

	public Sphere(double radius) {
		if (isLowerEqZero(radius)) throw new IllegalArgumentException("Invalid radius");

		this.radiusSqr = radius * radius;

		//double innerRadius = radius - ZERO;
		//this.innerRadiusSqr = innerRadius * innerRadius;
	}

	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		double dotprod  = ray.start.dot(ray.dir);
		double startsqr = ray.start.lengthSqr();
		double dirsqr   = ray.dir.lengthSqr();

		double rad = dotprod*dotprod - dirsqr * (startsqr - radiusSqr);

		//System.out.println("Sphere dist: ray="+ray+" rad="+rad);

		if (rad < 0.0) return new CutPoint[0];

		if (GeometryUtils.isZero(rad)) {
			return new CutPoint[] { getCutPoint(ray, -dotprod / dirsqr) };
		}

		double sqrt = sqrt(rad);

		return new CutPoint[] {
				getCutPoint(ray, (-dotprod + sqrt) / dirsqr),
				getCutPoint(ray, (-dotprod - sqrt) / dirsqr)
		};
	}

	@Override
	public boolean isInside(final Vector point) {
		return point.lengthSqr() < radiusSqr; // innerRadiusSqr;
	}

	private CutPoint getCutPoint(Ray ray, double dist) {
		Vector normal = ray.getPoint(dist);

		return propagateMaterial(new CutPoint(dist, normal, this));
	}

	@Override
	public String toString() {
		return "Sphere[radius="+Math.sqrt(radiusSqr)+"]";
	}

}
