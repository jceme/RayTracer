package de.raytracing.raytracer.base;

import java.util.Arrays;
import java.util.Comparator;

import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.util.GeometryUtils;

public class CutPoint extends MaterialObject
implements Approximate<CutPoint> {

	public static class CutPointNearestComparator implements Comparator<CutPoint> {

		@Override
		public int compare(CutPoint p1, CutPoint p2) {
			double d1 = p1.getDistance();
			double d2 = p2.getDistance();

			if (d1 < 0.0) return 1;
			if (d2 < 0.0) return -1;

			return Double.compare(d1, d2);
		}

	}

	private static final CutPointNearestComparator NEAREST_COMPARATOR
			= new CutPointNearestComparator();

	private double dist;
	private Vector normal;
	private TraceObject object;


	public CutPoint(double dist, Vector normal, TraceObject object) {
		this.dist = dist;
		this.object = object;
		setNormal(normal);
	}


	public Vector getCutPoint(final Ray ray) {
		return ray.start.add(ray.dir.multiply(getDistance()));
	}

	public double getDistance() {
		return dist;
	}

	public Vector getNormal() {
		return normal;
	}

	public TraceObject getObject() {
		return object;
	}

	public void trySetColor(Color color) {
		Material m = getMaterial();
		if (m == null) m = new Material();

		Color c = m.getColor();

		if (c == null) {
			m.setColor(color);
		}

		setMaterial(m);
	}


	public void flipNormal() {
		normal = normal.neg();
	}

	public void setNormal(Vector normal) {
		if (normal.isZero()) throw new IllegalArgumentException("Invalid normal vector");

		this.normal = normal.normalized();
	}

	@Override
	public boolean approxEquals(final CutPoint point) {
		return GeometryUtils.isZero(getDistance() - point.getDistance());
	}

	@Override
	public String toString() {
		return "CutPoint[dist="+getDistance()+", normal="+getNormal()+", obj="+getObject()+"]";
	}


	public static CutPoint getNearestCutPoint(CutPoint... points) {
		if (points.length == 0) return null;

		points = sortCutPoints(points);

		return points[0].getDistance() < 0.0 ? null : points[0];
	}

	public static CutPoint[] sortCutPoints(CutPoint... points) {
		if (points == null) return null;

		Arrays.sort(points, NEAREST_COMPARATOR);

		return points;
	}

}
