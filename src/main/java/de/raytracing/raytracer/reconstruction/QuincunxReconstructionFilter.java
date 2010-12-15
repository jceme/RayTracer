package de.raytracing.raytracer.reconstruction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Voxel;

public class QuincunxReconstructionFilter implements ReconstructionFilter {

	private final Log log = LogFactory.getLog(getClass());

	private final double centralWeight;
	private final double edgeWeight;
	private final double surroundWeight;

	private final int width;
	private final int height;

	private final Color[] edgeColorCache;
	private final Color[] midColorCache;


	public QuincunxReconstructionFilter(double centralWeight, double edgeWeight,
			int voxelWidth, int voxelHeight) {
		int commonEdges = (voxelWidth + 1) * (voxelHeight + 1);
		edgeColorCache = new Color[commonEdges];

		int mids = (voxelWidth + 2) * (voxelHeight + 2);
		midColorCache = new Color[mids];

		if (log.isDebugEnabled()) {
			log.debug("Prepared edge cache: "+commonEdges+" + mid cache: "+mids);
		}

		this.centralWeight = centralWeight;
		this.edgeWeight = edgeWeight / 4;
		this.surroundWeight = (1 - centralWeight - edgeWeight) / 8;

		if (this.centralWeight < 0 || this.edgeWeight < 0 || this.surroundWeight < 0) {
			throw new IllegalArgumentException("Weights must sum to 1.0");
		}

		this.width = voxelWidth;
		this.height = voxelHeight;
	}


	@Override
	public void processVoxel(Voxel voxel, Tracer tracer) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (log.isTraceEnabled()) log.trace("Voxel "+voxel+" at "+x+"x"+y);

				Color edgeColor = getEdgeColors(voxel, tracer, x, y);

				if (edgeColor == null) continue; // Skip out-of-bounds pixel

				Color surroundColor = getSurroundColors(voxel, tracer, x, y);

				Color resultcolor = surroundColor.add(edgeColor);

				tracer.callback(voxel, x, y, resultcolor);
			}
			}
	}


	private Color getEdgeColors(Voxel voxel, Tracer tracer, int x, int y) {
		// Bottom right corner
		Color brcolor = tracer.trace(voxel, x, y, 1.0, 1.0);

		if (brcolor == null) return null;  // Skip out of bounds pixel

		putEdge(brcolor, x + 1, y + 1);

		// Top right corner
		Color trcolor;
		if (y > 0) trcolor = getEdge(x + 1, y);
		else {
			trcolor = tracer.trace(voxel, x, y, 1.0, 0.0);

			putEdge(trcolor, x + 1, y);
		}

		// Top left corner
		Color tlcolor;
		if (y > 0) tlcolor = getEdge(x, y);
		else {
			tlcolor = tracer.trace(voxel, x, y, 0.0, 0.0);
		}

		// Bottom left corner
		Color blcolor;
		if (x > 0) blcolor = getEdge(x, y + 1);
		else {
			blcolor = tracer.trace(voxel, x, y, 0.0, 1.0);

			putEdge(blcolor, x, y + 1);
		}

		return sumWeighted(edgeWeight, brcolor, trcolor, tlcolor, blcolor);
	}


	private Color getSurroundColors(Voxel voxel, Tracer tracer, int x, int y) {
		// Top left neighbor
		Color colortl;
		if (x > 0 || y > 0) colortl = getMid(x - 1, y - 1);
		else {
			colortl = tracer.trace(voxel, x, y, -0.5, -0.5);
		}

		// Top center neighbor
		Color colortc;
		if (x > 0 || y > 0) colortc = getMid(x, y - 1);
		else {
			colortc = tracer.trace(voxel, x, y, 0.5, -0.5);

			putMid(colortc, x, y - 1);
		}

		// Top right neighbor
		Color colortr;
		if (y > 0) colortr = getMid(x + 1, y - 1);
		else {
			colortr = tracer.trace(voxel, x, y, 1.5, -0.5);

			putMid(colortr, x + 1, y - 1);
		}

		// Middle left neighbor
		Color colorml;
		if (x > 0 || y > 0) colorml = getMid(x - 1, y);
		else {
			colorml = tracer.trace(voxel, x, y, -0.5, 0.5);

			putMid(colorml, x - 1, y);
		}

		// Central
		Color colormc;
		if (x > 0 || y > 0) colormc = getMid(x, y);
		else {
			colormc = tracer.trace(voxel, x, y, 0.5, 0.5);

			putMid(colormc, x, y);
		}

		// Middle right neighbor
		Color colormr;
		if (y > 0) colormr = getMid(x + 1, y);
		else {
			colormr = tracer.trace(voxel, x, y, 1.5, 0.5);

			putMid(colormr, x + 1, y);
		}

		// Bottom left neighbor
		Color colorbl;
		if (x > 0) colorbl = getMid(x - 1, y + 1);
		else {
			colorbl = tracer.trace(voxel, x, y, -0.5, 1.5);

			putMid(colorbl, x - 1, y + 1);
		}

		// Bottom center neighbor
		Color colorbc;
		if (x > 0) colorbc = getMid(x, y + 1);
		else {
			colorbc = tracer.trace(voxel, x, y, 0.5, 1.5);

			putMid(colorbc, x, y + 1);
		}

		// Bottom right neighbor
		Color colorbr = tracer.trace(voxel, x, y, 1.5, 1.5);

		putMid(colorbr, x + 1, y + 1);

		return sumWeighted(centralWeight, colormc).add(sumWeighted(surroundWeight,
				colortl, colortc, colortr, colorml, colormr, colorbl, colorbc, colorbr));
	}


	private Color getMid(int x, int y) {
		return midColorCache[(y + 1) * (width + 2) + x + 1];
	}

	private void putMid(Color color, int x, int y) {
		midColorCache[(y + 1) * (width + 2) + x + 1] = color;
	}


	private Color getEdge(int x, int y) {
		return edgeColorCache[y * (width + 1) + x];
	}

	private void putEdge(Color color, int x, int y) {
		edgeColorCache[y * (width + 1) + x] = color;
	}

	private Color sumWeighted(double weight, Color... colors) {
		Color base = colors[0].multiply(weight);

		for (int i = 1; i < colors.length; i++) {
			base = base.add(colors[i].multiply(weight));
		}

		return base;
	}

}
