package de.raytracing.raytracer.base;

import static de.raytracing.raytracer.base.CutPoint.getNearestCutPoint;
import static de.raytracing.raytracer.util.MiscUtils.checkParam;
import static java.lang.Math.ceil;
import static java.lang.Thread.currentThread;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.reconstruction.QuincunxReconstructionFilter;
import de.raytracing.raytracer.reconstruction.ReconstructionFilter;
import de.raytracing.raytracer.reconstruction.Tracer;
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


	public void render(final int width, final int height, final RenderCallback callback) {
		if (width <= 0) throw new IllegalArgumentException("Invalid width: "+width);
		if (height <= 0) throw new IllegalArgumentException("Invalid height: "+height);
		checkParam(callback, "callback");

		final double sceneWidth = job.getScene().getSceneWidth();
		final double sceneHeight = (sceneWidth * height) / width;
		final double dx = sceneWidth / width;
		final double dy = -sceneHeight / height;
		final double x0 = -sceneWidth / 2.0;
		final double y0 = sceneHeight / 2.0;
		final int recursionDepth = job.getRecursionDepth();

		final Voxel voxel = new Voxel(job.getVoxelWidth(), job.getVoxelHeight());

		final int xVoxels = (int) ceil(width / ((double) voxel.width));
		final int yVoxels = (int) ceil(height / ((double) voxel.height));

		final ReconstructionFilter filter =
			new QuincunxReconstructionFilter(job.getAliasingCentralWeight(),
					job.getAliasingEdgeWeight(), voxel.width, voxel.height);

		final Tracer tracer = new Tracer() {

			@Override
			public Color trace(Voxel voxel, int x, int y, double vx, double vy) {
				final int px = voxel.getPixelX(x);
				final int py = voxel.getPixelY(y);

				if (px >= width || py >= height) {
					return null;
				}

				final double sx = (px + vx) * dx + x0;
				final double sy = (py + vy) * dy + y0;

				Vector screenPoint = new Vector(sx, sy, 0);

				Ray initRay = job.getScene().getCamera().getRay(screenPoint);

				Color color = Raytracer.this.trace(initRay, recursionDepth);

				if (log.isTraceEnabled()) {
					log.trace("Traced pixel "+px+"x"+py+" diff="+vx+"x"+vy+" to "+color);
				}

				return color;
			}

			@Override
			public void callback(Voxel voxel, int x, int y, Color color) {
				if (color != null) {
					int px = voxel.getPixelX(x);
					int py = voxel.getPixelY(y);

					if (px < width && py < height) {
						if (log.isTraceEnabled()) {
							log.trace("Pixel "+px+"x"+py+" is "+color);
						}

						callback.rendered(px, py, color);
					}
				}
			}

		};

		if (log.isDebugEnabled()) {
			log.debug("Rendering "+width+"x"+height+" with voxels "+voxel);
		}


		for (voxel.y = 0; voxel.y < yVoxels; voxel.y++) {
		for (voxel.x = 0; voxel.x < xVoxels; voxel.x++) {
			if (currentThread().isInterrupted()) {
				if (log.isDebugEnabled()) {
					int pc = (int) ((((voxel.x+1) * (voxel.y+1)) * 100.0) / (xVoxels * yVoxels));
					log.debug("Rendering "+width+"x"+height+" interrupted after "+
							pc+"%, exiting");
				}
				return;
			}

			filter.processVoxel(voxel, tracer);
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
