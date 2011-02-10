package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Voxel;


public class QuincunxReconstructionFilterFactory
implements ReconstructionFilterFactory<QuincunxReconstructionFilter> {

	@Override
	public QuincunxReconstructionFilter createFilter(RayTraceJob job, Voxel voxel) {
		return new QuincunxReconstructionFilter(job.getAliasingCentralWeight(),
				job.getAliasingEdgeWeight(), voxel.width, voxel.height);
	}

}
