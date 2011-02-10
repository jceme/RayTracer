package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Voxel;

public class SimpleReconstructionFilterFactory
implements ReconstructionFilterFactory<SimpleReconstructionFilter> {

	@Override
	public SimpleReconstructionFilter createFilter(RayTraceJob job, Voxel voxel) {
		return new SimpleReconstructionFilter();
	}

}
