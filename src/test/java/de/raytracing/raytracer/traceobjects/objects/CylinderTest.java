package de.raytracing.raytracer.traceobjects.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.objects.Cylinder;

public class CylinderTest {

	@Test
	public void testGetCutPointsYAxis() throws Exception {
		Cylinder c = new Cylinder(1.0, 2.0);
		Ray ray = new Ray(Vector.ZERO, Vector.Y_AXIS);

		CutPoint[] p = c.getCutPoints(ray);

		assertEquals(2, p.length);
		assertTrue(Vector.Y_AXIS.approxEquals(p[0].getCutPoint(ray)));
		assertTrue(Vector.Y_AXIS.neg().approxEquals(p[1].getCutPoint(ray)));
	}

	@Test
	public void testGetCutPointsParallelYAxis() throws Exception {
		Cylinder c = new Cylinder(1.0, 1.0);
		Ray ray = new Ray(Vector.X_AXIS.multiply(5), Vector.Y_AXIS);

		CutPoint[] p = c.getCutPoints(ray);

		assertEquals(0, p.length);
	}

	@Test
	public void testGetCutPoints() throws Exception {
		Cylinder c = new Cylinder(1.0, 10.0);
		Ray ray = new Ray(Vector.X_AXIS.multiply(-2), new Vector(2, 2, 1));

		CutPoint[] p = c.getCutPoints(ray);

		assertEquals(2, p.length);
	}

}
