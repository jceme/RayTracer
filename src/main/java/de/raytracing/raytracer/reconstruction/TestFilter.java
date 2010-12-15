package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Voxel;

public class TestFilter implements ReconstructionFilter {

	private final Color color;

	public TestFilter(Color color) {
		this.color = color;
	}

	public TestFilter() {
		this(Color.Red);
	}

	@Override
	public void processVoxel(Voxel voxel, Tracer tracer) {
		for (int y=0; y < voxel.height; y++) {
		for (int x=0; x < voxel.width; x++) {
			tracer.callback(voxel, x, y, color);
		}
		}
	}

}
