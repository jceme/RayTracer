package de.raytracing.raytracer.traceobjects.light;

import static de.raytracing.raytracer.util.GeometryUtils.isGreaterZero;
import static de.raytracing.raytracer.util.GeometryUtils.isLowerEqZero;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.SimpleMaterialObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.base.material.SimpleMaterial;
import de.raytracing.raytracer.util.GeometryUtils;

abstract class BaseLight extends SimpleMaterialObject<SimpleMaterial>
implements LightSource {

	private Vector location;
	private LightFading fading;


	protected BaseLight(Color color, Vector location) {
		if (color == null) throw new IllegalArgumentException("Invalid light color");
		if (location == null) throw new IllegalArgumentException("Invalid location");

		this.location = location;
		setMaterial(new SimpleMaterial(color));
	}


	@Override
	public Vector getLightLocation() {
		return location;
	}

	@Override
	public void setFading(LightFading fading) {
		this.fading = fading;
	}

	@Override
	public void setMaterial(SimpleMaterial material) {
		if (material == null) throw new IllegalArgumentException("Material required");

		super.setMaterial(material);
	}

	@Override
	public SimpleMaterial getFinalMaterial() {
		return getMaterial();
	}

	@Override
	public Color calcLight(final Vector target, final Raytracer scene) {
		final Ray lightRay = getLightRay(target);
		final double lightdist = getDirection(target).length();

		Color color = getFinalMaterial().getColor();

		final double dist = lightdist - 2.1 * GeometryUtils.EPSILON;

		final CutPoint[] cutPoints = scene.getCutPoints(lightRay);

		for (final CutPoint cutPoint : cutPoints) {
			final double distance = cutPoint.getDistance();
			final double diff = distance - dist;

			if (isGreaterZero(distance) && isLowerEqZero(diff)) {
				// Object in light ray
				cutPoint.trySetColor(scene.getRayTraceJob().getDefaultColor());

				Material pointMaterial = cutPoint.getFinalMaterial();
				double transparency = pointMaterial.getTransparency();
				Color pointColor = pointMaterial.getColor();

				Color filterColor = new Color(transparency).add(
						pointColor.multiply(1 - transparency));

				color = color.multiply(filterColor).multiply(transparency);
			}
		}

		// Light fading
		LightFading fading = this.fading == null ? scene.getLightFading() : this.fading;
		double fadingFactor = fading.getFading(lightdist);
		color = color.multiply(fadingFactor);

		return color;
	}


	protected Ray getLightRay(Vector target) {
		return new Ray(getLightPosition(target), getDirection(target));
	}


	private Vector getLightPosition(Vector target) {
		return getLightLocation();
	}

	@Override
	public Vector getDirection(Vector target) {
		return target.sub(getLightPosition(target));
	}

}
