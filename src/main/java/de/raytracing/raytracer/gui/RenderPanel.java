package de.raytracing.raytracer.gui;

import static de.raytracing.raytracer.scenes.DefaultScene.getDefaultScene;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.base.RayTraceJob;
import de.raytracing.raytracer.base.Raytracer;

public class RenderPanel extends JPanel implements RenderObserver {

	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(getClass());

	private RenderImage renderImage;
	private final Raytracer raytracer;

	private int renderCount = 0;


	public RenderPanel() {
		//setDoubleBuffered(true);

		raytracer = createRaytracer();

		updateRenderImage();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (log.isDebugEnabled()) {
					try {
						log.info("Clicked at "+e.getX()+", "+e.getY()
								+": Color is "+new Robot().getPixelColor(e.getXOnScreen(),
										e.getYOnScreen()));
					} catch (AWTException e1) {
						throw new UnsupportedOperationException(e1);
					}
				}
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				log.trace("Component resized, updating render image");
				updateRenderImage();
			}
		});
	}

	private Raytracer createRaytracer() {
		final RayTraceJob job = new RayTraceJob(getDefaultScene());
		// Change options here

		return new Raytracer(job);
	}

	private void updateRenderImage() {
		int width = getWidth();
		int height = getHeight();

		if (width <= 0 || height <= 0) {
			log.trace("No width and height available yet");
			return;
		}

		if (renderImage != null) {
			if (renderImage.check(width, height)) {
				log.trace("Render image dimensions still valid");
				return;
			}

			renderImage.abortRendering();
		}

		renderImage = new RenderImage(width , height, raytracer);
		renderImage.setObserver(this);

		renderCount++;

		if (log.isDebugEnabled()) {
			log.debug("Created new render image #"+renderCount);
		}
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		if (renderImage == null) {
			log.trace("No render image available yet");
			return;
		}

		int width = getWidth();
		int height = getHeight();

		try {
			final Image image = renderImage.getImage(width, height);

			g.drawImage(image, 0, 0, null);
		} catch (ModifiedDimensionException e) {
			log.debug("Render panel changed dimension, updating render image");

			updateRenderImage();

			g.clearRect(0, 0, width, height);
		}
	}


	@Override
	public void renderImageUpdated() {
		repaint();
	}

}
