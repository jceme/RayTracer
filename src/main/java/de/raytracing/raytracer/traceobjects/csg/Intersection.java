package de.raytracing.raytracer.traceobjects.csg;

import static de.raytracing.raytracer.util.GeometryUtils.collectApproxEquals;
import static de.raytracing.raytracer.util.GeometryUtils.filterList;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.CutPointInsideFilter;

public class Intersection extends MaterialObject implements TraceObject {

	public static class IntersectionFilter extends CutPointInsideFilter {

		public IntersectionFilter(Ray ray, TraceObject... objects) {
			super(ray, objects);
		}

		@Override
		protected boolean test(TraceObject object, Vector point) {
			return !super.test(object, point);
		}

	}

	private final TraceObject[] objects;


	public Intersection(TraceObject... objects) {
		this.objects = objects;
	}


	@Override
	public CutPoint[] getCutPoints(Ray ray) {
		CutPoint[][] list = new CutPoint[objects.length][];

		CutPointInsideFilter filter = new IntersectionFilter(ray, objects);

		for (int i=0; i < objects.length; i++) {
			filter.ignore(objects[i]);

			list[i] = filterList(objects[i].getCutPoints(ray), filter);
		}

		return propagateMaterials(collectApproxEquals(list));
	}

	@Override
	public boolean isInside(final Vector point) {
		for (TraceObject object : objects) {
			if (!object.isInside(point)) {
				return false;
			}
		}

		return true;
	}

}
