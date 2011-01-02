package de.raytracing.raytracer.traceobjects.light;

import static java.lang.Math.acos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.toRadians;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;

public class SpotLight extends BaseLight {

	private static final double DEFAULT_WIDE_ANGLE = 20.0;
	private static final double DEFAULT_INNER_ANGLE = 12.0;
	private static final double DEFAULT_FALLOFF = 2.6;

	private final Vector normal;
	private double pitch;
	private double raise;
	private double falloff;


	public SpotLight(Color color, Vector location, Vector lookat) {
		super(color, location);

		final Vector normal = lookat.sub(location);
		if (normal.isZero()) {
			throw new IllegalArgumentException("Illegal lookat vector "+lookat);
		}
		this.normal = normal.normalized();

		setSpotAngles(DEFAULT_WIDE_ANGLE, DEFAULT_INNER_ANGLE);
		setFallOff(DEFAULT_FALLOFF);
	}

	public void setFallOff(double falloff) {
		this.falloff = falloff;
	}

	public void setSpotAngles(double wideSpotAngle, double innerSpotAngle) {
		double wide = toRadians(wideSpotAngle);
		double inner = toRadians(innerSpotAngle);

		if (inner >= wide) {
			throw new IllegalArgumentException("Illegal spot angles");
		}

		pitch = 1.0 / (wide - inner);
		raise = -1.0 / (wide / inner - 1.0);
	}


	@Override
	public Color calcLight(final Vector target, final Raytracer scene) {
		Color color = super.calcLight(target, scene);

		final Ray lightRay = getLightRay(target);

		double diff = acos(lightRay.dir.dot(normal));

		double diffscale = max(0.0, diff * pitch + raise);

		double infscale = 1.0 - pow(diffscale, falloff);

		double scale = max(0.0, min(1.0, infscale));

		return color.multiply(scale);
	}

}
