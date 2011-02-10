package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.reconstruction.SimpleReconstructionFilterFactory;

public class SpotSceneUnfiltered extends SpotScene {

	@Override
	public void modifyRaytracer(Raytracer raytracer) {
		super.modifyRaytracer(raytracer);

		raytracer.setReconstructionFilterFactory(new SimpleReconstructionFilterFactory());
	}

}
