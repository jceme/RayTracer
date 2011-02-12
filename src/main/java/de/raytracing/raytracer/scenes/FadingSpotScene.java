package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.traceobjects.light.LightFading;

public class FadingSpotScene extends SpotScene {

	@Override
	public void modifyRaytracer(Raytracer raytracer) {
		super.modifyRaytracer(raytracer);

		raytracer.getRayTraceJob().setLightFading(
				LightFading.createLinearFadingParametric(11, 0.92, 14, 0.2));
	}

}
