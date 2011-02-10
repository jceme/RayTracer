package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import static de.raytracing.raytracer.util.GeometryUtils.isLowerEqZero;
import static de.raytracing.raytracer.util.GeometryUtils.isZero;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Difference;

public class Cone extends Difference {

	public static class InfiniteCone extends MaterialObject implements TraceObject {

		private final double radius;
		private final double height;

		private InfiniteCone(double radius, double height) {
			if (isLowerEqZero(radius)) throw new IllegalArgumentException("Invalid radius");
			if (isLowerEqZero(height)) throw new IllegalArgumentException("Invalid height");

			this.radius = radius;
			this.height = height;
		}

		@Override
		public CutPoint[] getCutPoints(Ray ray) {
			final double s  = ray.start.y;
			final double v  = ray.dir.y;
			final Vector sq = ray.start.planar(Y_AXIS);
			final Vector vq = ray.dir.planar(Y_AXIS);

			final double h2 = height * height;
			final double r2 = radius * radius;

			final double f = h2 * sq.dot(vq) + r2 * v * (height - s);
			final double g = r2 * v*v - h2 * vq.lengthSqr();

			double rad = f*f - g * (h2 * (r2 - sq.lengthSqr()) + r2 * s * (s - 2 * height));

			if (rad < 0) {
				return new CutPoint[0];
			}

			if (isZero(rad)) {
				return new CutPoint[] {
						getCutPoint(ray, f / g)
				};
			}

			rad = Math.sqrt(rad);

			return new CutPoint[] {
					getCutPoint(ray, (f + rad) / g),
					getCutPoint(ray, (f - rad) / g)
			};
		}

		private CutPoint getCutPoint(Ray ray, double dist) {
			Vector p = ray.getPoint(dist).planar(Y_AXIS);

			Vector normal = new Vector(p.x, p.length() * radius / height, p.z);

			if (normal.isZero()) {
				// Normal at top of cone
				normal = Vector.Y_AXIS;
			}

			return propagateMaterial(new CutPoint(dist, normal, this));
		}

		@Override
		public boolean isInside(Vector point) {
			return point.planar(Y_AXIS).length() <= radius * (1.0 - point.y / height);
		}

	}


	public Cone(double radius, double height) {
		super(new InfiniteCone(radius, height),
				new Plane(Y_AXIS),
				new Plane(Y_AXIS.neg(), -height));
	}

}
