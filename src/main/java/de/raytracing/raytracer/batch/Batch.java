package de.raytracing.raytracer.batch;

import static de.raytracing.raytracer.batch.BatchRenderer.getSceneName;
import static de.raytracing.raytracer.scenes.DefaultScene.resolveScene;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.base.Scene;

public class Batch {

	private static final Log log = LogFactory.getLog(Batch.class);

	private static final PrintStream out = System.out;


	public static void main(String[] args) {
		final RenderRequest request = parseRequest(args);

		if (request == null || request.getScenes().isEmpty()) {
			out.println("Usage: batch IMAGEWIDTH IMAGEHEIGHT SCENENAME...");
			return;
		}

		if (log.isDebugEnabled()) log.debug("Have scenes: "+request.getScenes());


		for (final Scene scene : request.getScenes()) {
			out.println();
			out.println("Rendering scene "+getSceneName(scene));

			final BatchRenderer batchRenderer = new BatchRenderer(request.getWidth(),
					request.getHeight(), scene);

			try {
				batchRenderer.complete();

				out.println("Rendered scene "+getSceneName(scene));
			}
			catch (IOException e) {
				out.println("Failed to save scene "+getSceneName(scene)+": "+e.getMessage());
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		out.println();
		out.println("Render completed");
	}


	private static RenderRequest parseRequest(String[] args) {
		if (args.length < 2) return null;

		RenderRequest request = new RenderRequest();

		try {
			request.setDimension(parseInt(args[0]), parseInt(args[1]));
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid dimension", e);
		}

		for (int i = 2; i < args.length; i++) {
			try {
				request.addScene(resolveScene(args[i]));
			}
			catch (IllegalStateException e) {
				out.println("Skipping unresolvable scene name "+args[i]+": "+e.getMessage());
			}
		}

		return request;
	}

}
