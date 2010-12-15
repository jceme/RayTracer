package de.raytracing.raytracer.reconstruction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Voxel;

/**
 * A simple reconstruction filter using central pixel color.
 */
public class SimpleReconstructionFilter implements ReconstructionFilter {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void processVoxel(final Voxel voxel, final Tracer tracer) {
		for (int y = 0; y < voxel.height; y++) {
		for (int x = 0; x < voxel.width; x++) {
			if (log.isTraceEnabled()) log.trace("Voxel "+voxel+" at "+x+"x"+y);

			Color midcolor = tracer.trace(voxel, x, y, 0.5, 0.5);

			if (midcolor == null) continue;  // Skip out of bounds

			tracer.callback(voxel, x, y, midcolor);
		}
		}
	}

}
