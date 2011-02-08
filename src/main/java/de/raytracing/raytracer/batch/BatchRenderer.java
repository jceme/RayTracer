package de.raytracing.raytracer.batch;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Scene;
import de.raytracing.raytracer.gui.RenderImage;

public class BatchRenderer {

	private final RenderImage image;


	public BatchRenderer(int width, int height, Scene scene) {
		final RayTraceJob job = new RayTraceJob(scene);
		final Raytracer raytracer = new Raytracer(job);

		this.image = new RenderImage(width, height, raytracer);
	}


	public void complete() throws InterruptedException {
		image.awaitRenderingFinished();
		// TODO save image via ImageIO.write
	}

}
