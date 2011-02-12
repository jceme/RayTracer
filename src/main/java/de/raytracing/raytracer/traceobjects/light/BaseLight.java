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
import de.raytracing.raytracer.traceobjects.base.material.SimpleMaterial;
import de.raytracing.raytracer.util.GeometryUtils;

abstract class BaseLight extends SimpleMaterialObject<SimpleMaterial>
implements LightSource {

	private Vector location;


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
	public void setMaterial(SimpleMaterial material) {
		if (material == null) throw new IllegalArgumentException("Material required");

		super.setMaterial(material);
	}

	@Override
	public SimpleMaterial getFinalMaterial() {
		return getMaterial();
	}

	@Override
	public Color calcLight(Vector target, Raytracer scene) {
		final Ray lightRay = getLightRay(target);
		final double dist = getDirection(target).length();

		if (isObjectIntersecting(lightRay, dist, scene)) {
			// If some object is placed between cut-point and light
			// then no light shines on it from this light source
			return Color.Black;
		}

		// TODO Introduce light fading

		return getFinalMaterial().getColor();
	}


	protected Ray getLightRay(Vector target) {
		return new Ray(getLightPosition(target), getDirection(target));
	}


	private boolean isObjectIntersecting(Ray lightRay, double maxDistance, final Raytracer scene) {
		maxDistance -= 2.1 * GeometryUtils.EPSILON;
		CutPoint[] cutPoints = scene.getCutPoints(lightRay);

		for (CutPoint cutPoint : cutPoints) {
			final double distance = cutPoint.getDistance();
			double diff = distance - maxDistance;

			if (isGreaterZero(distance) && isLowerEqZero(diff)) {
				return true;
			}
		}

		return false;
	}


	private Vector getLightPosition(Vector target) {
		return getLightLocation();
	}

	@Override
	public Vector getDirection(Vector target) {
		return target.sub(getLightPosition(target));
	}

}
