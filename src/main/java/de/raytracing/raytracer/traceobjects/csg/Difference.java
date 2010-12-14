package de.raytracing.raytracer.traceobjects.csg;

import static de.raytracing.raytracer.util.GeometryUtils.collectApproxEquals;
import static de.raytracing.raytracer.util.GeometryUtils.filterList;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.GeometryUtils.Filter;

public class Difference extends MaterialObject implements TraceObject {

	private final TraceObject root;
	private final TraceObject diff;


	public Difference(TraceObject root, TraceObject... diffObjects) {
		this.root = root;

		if (diffObjects != null && diffObjects.length == 1) {
			this.diff = diffObjects[0];
		}
		else {
			this.diff = new Union(diffObjects);
		}
	}


	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		CutPoint[] rootPoints = root.getCutPoints(ray);
		CutPoint[] diffPoints = diff.getCutPoints(ray);

		rootPoints = filterList(rootPoints, new Filter<CutPoint>() {
			@Override
			public boolean accept(CutPoint cutPoint) {
				return !diff.isInside(cutPoint.getCutPoint(ray));
			}
		});

		diffPoints = filterList(diffPoints, new Filter<CutPoint>() {
			@Override
			public boolean accept(CutPoint cutPoint) {
				return root.isInside(cutPoint.getCutPoint(ray));
			}
		});

		for (CutPoint p : diffPoints) {
			p.flipNormal();
		}

		return propagateMaterials(collectApproxEquals(new CutPoint[][] { rootPoints, diffPoints }));
	}

	@Override
	public boolean isInside(final Vector point) {
		return root.isInside(point) && !diff.isInside(point);
	}

}
