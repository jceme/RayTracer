package de.raytracing.raytracer.csg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Union;
import de.raytracing.raytracer.traceobjects.modifier.Translate;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class UnionTest {

	@Test
	public void testGetCutPoints() throws Exception {
		TraceObject obj1 = new Translate(Vector.X_AXIS, new Sphere(2));
		TraceObject obj2 = new Translate(Vector.X_AXIS.neg(), new Sphere(2));

		Union union = new Union(obj1, obj2);

		Ray ray = new Ray(Vector.Z_AXIS.multiply(-10), Vector.Z_AXIS);

		CutPoint[] result = union.getCutPoints(ray);

		assertEquals(2, result.length);
	}

}
