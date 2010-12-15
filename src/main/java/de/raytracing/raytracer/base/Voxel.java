package de.raytracing.raytracer.base;

public class Voxel {

	// Voxel coord
	public int x;
	public int y;

	// Voxel dimension
	public final int width;
	public final int height;


	public Voxel(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getPixelX(int x) {
		return this.x * width + x;
	}

	public int getPixelY(int y) {
		return this.y * height + y;
	}

	@Override
	public String toString() {
		return "Voxel[id="+((x+1)*(y+1))+", coord="+x+"x"+y+", dim="+width+"x"+height+"]";
	}

}
