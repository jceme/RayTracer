package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.reconstruction.SimpleReconstructionFilterFactory;

public class SimpleRedSphereUnfiltered extends SimpleRedSphere {

	@Override
	public void modifyRaytracer(Raytracer raytracer) {
		super.modifyRaytracer(raytracer);

		raytracer.setReconstructionFilterFactory(new SimpleReconstructionFilterFactory());
	}

}
