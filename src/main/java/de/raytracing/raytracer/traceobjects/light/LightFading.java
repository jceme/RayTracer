package de.raytracing.raytracer.traceobjects.light;

import static java.lang.Math.max;

public abstract class LightFading {

	public abstract double getFading(double distance);


	public static LightFading createConstantFading() {
		return createConstantFading(1.0);
	}

	public static LightFading createConstantFading(final double intensity) {
		return new LightFading() {
			@Override
			public double getFading(double distance) {
				return intensity;
			}
		};
	}

	public static LightFading createQuadraticFading(
			final double q, final double l, final double c) {
		return new LightFading() {
			@Override
			public double getFading(double distance) {
				return 1.0 / (q * distance*distance + l * distance + c);
			}
		};
	}

	public static LightFading createLinearFading(double falloff) {
		return createLinearFading(falloff, 1.0);
	}


	public static LightFading createLinearFading(final double falloff, final double initial) {
		return new LightFading() {
			@Override
			public double getFading(double distance) {
				return max(distance * falloff + initial, 0.0);
			}
		};
	}

	public static LightFading createLinearFadingParametric(double d1, double v1, double d2, double v2) {
		double falloff = (v2 - v1) / (d2 - d1);
		double initial = v1 - d1 * falloff;

		return createLinearFading(falloff, initial);
	}

}
