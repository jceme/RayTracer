package de.raytracing.raytracer.traceobjects.csg;

import static de.raytracing.raytracer.util.GeometryUtils.collectApproxEquals;
import static de.raytracing.raytracer.util.GeometryUtils.filterList;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.util.CutPointInsideFilter;

public class Union extends Group {

	public Union(TraceObject... objects) {
		super(objects);
	}


	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		CutPoint[][] list = new CutPoint[objects.length][];

		CutPointInsideFilter filter = new CutPointInsideFilter(ray, objects);

		for (int i=0; i < objects.length; i++) {
			filter.ignore(objects[i]);

			list[i] = filterList(objects[i].getCutPoints(ray), filter);
		}

		return propagateMaterials(collectApproxEquals(list));
	}

}
