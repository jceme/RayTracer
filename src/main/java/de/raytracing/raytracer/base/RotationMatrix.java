package de.raytracing.raytracer.base;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class RotationMatrix {

	private final double[] triang;

	public RotationMatrix(final Vector v) {
		triang = new double[] {
				sin(toRadians(v.x)), cos(toRadians(v.x)),
				sin(toRadians(v.y)), cos(toRadians(v.y)),
				sin(toRadians(v.z)), cos(toRadians(v.z))
		};
	}

	public Vector multiply(Vector v) {
		v = rotateXAxis(v);
		v = rotateYAxis(v);
		v = rotateZAxis(v);

		return v;
	}

	private Vector rotateXAxis(final Vector v) {
		final double s = triang[0];
		final double c = triang[1];

		return new Vector(v.x, c * v.y - s * v.z, s * v.y + c * v.z);
	}

	private Vector rotateYAxis(final Vector v) {
		final double s = triang[2];
		final double c = triang[3];

		return new Vector(c * v.x + s * v.z, v.y, c * v.z - s * v.x);
	}

	private Vector rotateZAxis(final Vector v) {
		final double s = triang[4];
		final double c = triang[5];

		return new Vector(c * v.x - s * v.y, s * v.x + c * v.y, v.z);
	}

}
