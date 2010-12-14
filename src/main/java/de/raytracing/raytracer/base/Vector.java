package de.raytracing.raytracer.base;

import de.raytracing.raytracer.util.GeometryUtils;

public class Vector implements Approximate<Vector> {

	public static final Vector ZERO = new Vector(0.0, 0.0, 0.0);
	public static final Vector ONE  = new Vector(1.0, 1.0, 1.0);

	public static final Vector X_AXIS = new Vector(1.0, 0.0, 0.0);
	public static final Vector Y_AXIS = new Vector(0.0, 1.0, 0.0);
	public static final Vector Z_AXIS = new Vector(0.0, 0.0, 1.0);

	public final double x;
	public final double y;
	public final double z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public Vector neg() {
		return new Vector(-x, -y, -z);
	}

	public Vector add(final Vector v) {
		return add(v.x, v.y, v.z);
	}

	public Vector add(double dx, double dy, double dz) {
		return new Vector(x + dx, y + dy, z + dz);
	}

	public Vector sub(final Vector v) {
		return add(v.neg());
	}

	public double length() {
		return Math.sqrt(lengthSqr());
	}

	public double lengthSqr() {
		return x*x + y*y + z*z;
	}

	public Vector multiply(double t) {
		return new Vector(t*x, t*y, t*z);
	}

	public Vector vectorMultiply(final Vector v) {
		return new Vector(x * v.x, y * v.y, z * v.z);
	}

	public Vector vectorDivision(final Vector v) {
		return new Vector(x / v.x, y / v.y, z / v.z);
	}

	public double dot(final Vector v) {
		return x*v.x + y*v.y + z*v.z;
	}

	public Vector cross(final Vector v) {
		return new Vector(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
	}

	public Vector normalized() {
		return isZero() ? ZERO : multiply(1 / length());
	}


	public boolean isZero() {
		return GeometryUtils.isZero(length());
	}

	@Override
	public boolean approxEquals(final Vector v) {
		return sub(v).isZero();
	}


	public double angle(final Vector v) {
		return Math.acos(normalized().dot(v.normalized()));
	}

	public boolean isParallel(final Vector v) {
		return GeometryUtils.isZero(Math.abs(angle(v)));
	}

	public Vector mirror(final Vector axis) {
		/*
		 * Alternate algorithm:
		 * Incoming ray dir (unchanged) I and normal N
		 * Reflection dir = I - 2 (I * N) * N
		 */

		return this.sub(axis.multiply(2.0 * this.dot(axis)));

		/*
		final Vector r = cross(axis).normalized(); // rotation vector

		if (r.isZero()) return this;

		final double axisCos = normalized().dot(axis.normalized());

		final double cos = Math.cos(2.0 * Math.acos(axisCos));
		final double sin = Math.sqrt(1.0 - cos * cos);
		final double rem = 1.0 - cos;

		double[] m = new double[] {
				cos + r.x * r.x * rem,
				r.x * r.y * rem - r.z * sin,
				r.x * r.z * rem + r.y * sin,

				r.x * r.y * rem + r.z * sin,
				cos + r.y * r.y * rem,
				r.y * r.z * rem - r.x * sin,

				r.x * r.z * rem - r.y * sin,
				r.y * r.z * rem + r.x * sin,
				cos + r.z * r.z * rem
		};

		return new Vector(x*m[0]+y*m[1]+z*m[2], x*m[3]+y*m[4]+z*m[5], x*m[6]+y*m[7]+z*m[8]);
		*/
	}

	public Vector refract(Vector normal, double ratio) {
		double cosi = dot(normal);
		double sini = Math.sqrt(1.0 - cosi * cosi);
		double sino = sini * ratio;

		if (sino * sino >= 1.0) {
			return ZERO;
		}

		double coso = Math.sqrt(1.0 - sino * sino);

		return multiply(ratio).sub(normal.multiply(ratio * cosi + coso));
	}

	public Vector planar(Vector axis) {
		return vectorMultiply(ONE.sub(axis));
	}


	@Override
	public String toString() {
		return String.format("Vector[%.2f, %.2f, %.2f]", x, y, z);
	}

}
