package de.raytracing.raytracer.traceobjects.csg;

import static de.raytracing.raytracer.util.GeometryUtils.collectApproxEquals;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

public class Group extends MaterialObject implements TraceObject {

	protected final TraceObject[] objects;


	public Group(TraceObject... objects) {
		this.objects = objects;
	}


	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		CutPoint[][] list = new CutPoint[objects.length][];

		for (int i=0; i < objects.length; i++) {
			list[i] = objects[i].getCutPoints(ray);
		}

		return propagateMaterials(collectApproxEquals(list));
	}

	@Override
	public boolean isInside(final Vector point) {
		for (TraceObject object : objects) {
			if (object.isInside(point)) {
				return true;
			}
		}

		return false;
	}

}
