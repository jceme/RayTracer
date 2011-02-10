package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Voxel;

public interface ReconstructionFilterFactory<T extends ReconstructionFilter> {

	public T createFilter(RayTraceJob job, Voxel voxel);

}
