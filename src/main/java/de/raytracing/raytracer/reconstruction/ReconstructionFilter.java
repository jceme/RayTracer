package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.Voxel;

public interface ReconstructionFilter {

	public void processVoxel(Voxel voxel, Tracer tracer);

}
