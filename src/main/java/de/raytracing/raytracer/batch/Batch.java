package de.raytracing.raytracer.batch;

import static de.raytracing.raytracer.scenes.DefaultScene.resolveScene;
import static java.lang.Integer.parseInt;

import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
