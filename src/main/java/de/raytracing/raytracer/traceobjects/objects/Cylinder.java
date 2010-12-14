package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import static de.raytracing.raytracer.util.GeometryUtils.isLowerEqZero;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Intersection;
import de.raytracing.raytracer.util.GeometryUtils;

public class Cylinder extends Intersection {

	public Cylinder(double radius, double length) {
		super(
				new InfiniteCylinder(radius),
				new Plane(Y_AXIS, length / 2),
				new Plane(Y_AXIS.neg(), length / 2)
		);
	}

	public Cylinder() {
		this(1.0, 1.0);
	}


	private static class InfiniteCylinder extends MaterialObject implements TraceObject {

		private final double radiusSqr;

		public InfiniteCylinder(double radius) {
			if (isLowerEqZero(radius)) throw new IllegalArgumentException("Invalid radius");

			radiusSqr = radius * radius;
		}

		@Override
		public CutPoint[] getCutPoints(final Ray ray) {
			final Vector start = getPlanar(ray.start);
			final Vector dir = getPlanar(ray.dir);

			if (dir.isZero())
				return new CutPoint[0];

			double sv = start.dot(dir);
			double rad = sv * sv - dir.lengthSqr() * (start.lengthSqr() - radiusSqr);

			if (rad < 0.0)
				return new CutPoint[0];

			if (GeometryUtils.isZero(rad)) {
				return new CutPoint[] { getCutPoint(ray, -sv / dir.lengthSqr()) };
			}

			rad = Math.sqrt(rad);

			return new CutPoint[] {
					getCutPoint(ray, (-sv + rad) / dir.lengthSqr()),
					getCutPoint(ray, (-sv - rad) / dir.lengthSqr())
			};
		}

		@Override
		public boolean isInside(final Vector point) {
			return getPlanar(point).lengthSqr() <= radiusSqr;
		}


		private CutPoint getCutPoint(Ray ray, double dist) {
			return propagateMaterial(new CutPoint(dist, getPlanar(ray.getPoint(dist)), this));
		}

		private Vector getPlanar(Vector v) {
			return new Vector(v.x, 0, v.z);
		}

	}

}
