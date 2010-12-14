package de.raytracing.raytracer.base;

import static de.raytracing.raytracer.base.CutPoint.getNearestCutPoint;
import static de.raytracing.raytracer.util.MiscUtils.checkParam;
import static java.lang.Thread.currentThread;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.shader.Shader;
import de.raytracing.raytracer.traceobjects.base.LightSource;

public class Raytracer {

	private final Log log = LogFactory.getLog(getClass());

	private final RayTraceJob job;


	public Raytracer(RayTraceJob job) {
		checkParam(job, "job");
		this.job = job;
	}


	public RayTraceJob getRayTraceJob() {
		return job;
	}


	public void render(int width, int height, RenderCallback callback) {
		if (width <= 0) throw new IllegalArgumentException("Invalid width: "+width);
		if (height <= 0) throw new IllegalArgumentException("Invalid height: "+height);
		checkParam(callback, "callback");

		final double sceneWidth = job.getScene().getSceneWidth();
		final double sceneHeight = (sceneWidth * height) / width;
		final double dx = sceneWidth / width;
		final double dy = -sceneHeight / height;
		final double x0 = -sceneWidth / 2.0 + dx / 2.0;
		final double y0 = sceneHeight / 2.0 + dy / 2.0;
		final int recursionDepth = job.getRecursionDepth();

		if (log.isDebugEnabled()) log.debug("Rendering "+width+"x"+height);

		for (int y = 0; y < height; y++) {
		for (int x = 0; x < width; x++) {
			if (currentThread().isInterrupted()) {
				if (log.isDebugEnabled()) {
					int pc = (int) ((((x+1) * (y+1)) * 100.0) / (width * height));
					log.debug("Rendering "+width+"x"+height+" interrupted after "+
							pc+"%, exiting");
				}
				return;
			}
			Thread.yield();

			// Use reconstruction filter with multiple values per pixel
			Vector screenPoint = new Vector(x * dx + x0, y * dy + y0, 0);

			// TODO remove debug entry below
			if (x == 142 && y == 88) {
				x += 0;
			}

			Ray initRay = job.getScene().getCamera().getRay(screenPoint);

			Color color = trace(initRay, recursionDepth);

			callback.rendered(x, y, color);
		}
		}

		if (log.isDebugEnabled()) log.debug("Rendering completed "+width+"x"+height);
	}


	public Color trace(Ray ray, int recursion) {
		recursion--;

		CutPoint cutPoint = getNearestCutPoint(getCutPoints(ray));

		if (cutPoint == null) {
			return job.getInfiniteColor();
		}

		cutPoint.trySetColor(job.getDefaultColor());

		final Shader shader = new Shader(this, ray, cutPoint);

		Color color = shader.shade(recursion);

		return color;
	}

	public boolean canRecurse(int recursion) {
		return recursion >= 0;
	}

	public CutPoint[] getCutPoints(Ray ray) {
		return job.getSceneObject().getCutPoints(ray);
	}

	public List<LightSource> getLightSources() {
		return job.getScene().getLightSources();
	}

}
