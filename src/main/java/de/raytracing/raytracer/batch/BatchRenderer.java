package de.raytracing.raytracer.batch;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Scene;
import de.raytracing.raytracer.gui.RenderImage;

public class BatchRenderer {

	private static final String IMAGE_FORMAT = "png";

	private final RenderImage image;
	private final String sceneName;


	public BatchRenderer(int width, int height, Scene scene) {
		final RayTraceJob job = new RayTraceJob(scene);
		final Raytracer raytracer = new Raytracer(job);

		this.image = new RenderImage(width, height, raytracer);

		this.sceneName = getSceneName(scene);
	}


	public void complete() throws InterruptedException, IOException {
		image.awaitRenderingFinished();

		ImageIO.write(image.getRenderedImage(), IMAGE_FORMAT, new File(sceneName+"."+IMAGE_FORMAT));
	}


	public static String getSceneName(Scene scene) {
		return scene.getClass().getSimpleName();
	}

}
