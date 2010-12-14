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

public abstract class BaseLight extends SimpleMaterialObject<SimpleMaterial>
implements LightSource {

	private Vector location;


	public BaseLight(Color color, Vector location) {
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
		Vector position = getLightPosition(target);
		Vector direction = getDirection(target);

		Ray lightRay = new Ray(position, direction);

		if (isObjectIntersecting(lightRay, direction, scene)) {
			// If some object is placed between cut-point and light
			// then no light shines on it from this light source
			return Color.Black;
		}

		// TODO Introduce light fading

		return getFinalMaterial().getColor();
	}


	protected boolean isObjectIntersecting(Ray lightRay, Vector direction,
			final Raytracer scene) {
		final double maxDistance = direction.length() - 2.1 * GeometryUtils.EPSILON;
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


	protected Vector getLightPosition(Vector target) {
		return getLightLocation();
	}

	@Override
	public Vector getDirection(Vector target) {
		return target.sub(getLightPosition(target));
	}

}
