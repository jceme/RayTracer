package de.raytracing.raytracer.reconstruction;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Voxel;

public interface Tracer {

	public Color trace(Voxel voxel, int x, int y, double vx, double vy);

	public void callback(Voxel voxel, int x, int y, Color color);

}
