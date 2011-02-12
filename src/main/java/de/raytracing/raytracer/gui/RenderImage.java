package de.raytracing.raytracer.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.RenderCallback;

public final class RenderImage implements RenderCallback {

	private final Log log = LogFactory.getLog(getClass());

	private final BufferedImage image;

	private final int width;
	private final int height;

	private final Thread renderThread;

	private final RenderObserver observer;


	public RenderImage(int width, int height, Raytracer raytracer) {
		this(width, height, raytracer, null);
	}

	public RenderImage(int width, int height, final Raytracer raytracer, RenderObserver observer) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		clearImage(raytracer.getRayTraceJob().getInfiniteColor().getAwtColor());


		this.width = width;
		this.height = height;

		renderThread = new Thread("RenderThread") {
			@Override
			public void run() {
				raytracer.render(RenderImage.this.width, RenderImage.this.height,
						RenderImage.this);

				if (RenderImage.this.observer != null) {
					RenderImage.this.observer.renderImageFinished();
				}
			}
		};

		if (log.isDebugEnabled()) log.debug("Created render image "+width+"x"+height);

		this.observer = observer;

		renderThread.setDaemon(true);
		renderThread.setPriority(3);

		renderThread.start();
	}


	private void clearImage(java.awt.Color color) {
		final Graphics2D g = (Graphics2D) image.getGraphics();

		g.setBackground(color);
		g.clearRect(0, 0, width, height);
	}


	public boolean check(int width, int height) {
		return this.width == width || this.height == height;
	}


	public Image getImage(int width, int height)
	throws ModifiedDimensionException {
		if (!check(width, height)) {
			throw new ModifiedDimensionException();
		}

		synchronized (image) {
			return image;
		}
	}

	public RenderedImage getRenderedImage() {
		synchronized (image) {
			return image;
		}
	}


	public void abortRendering() {
		renderThread.interrupt();
		log.info("Rendering aborted");
	}

	public void awaitRenderingFinished() throws InterruptedException {
		renderThread.join();
	}


	@Override
	public void rendered(int posX, int posY, final Color color) {
		// Draw pixel on image
		synchronized (image) {
			final Graphics g = image.getGraphics();

			g.setColor(color.getAwtColor());
			g.drawLine(posX, posY, posX, posY);
		}

		if (observer != null) {
			observer.renderImageUpdated();
		}
	}

}
