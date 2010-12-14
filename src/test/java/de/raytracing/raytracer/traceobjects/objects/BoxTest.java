package de.raytracing.raytracer.traceobjects.objects;

import static de.raytracing.raytracer.base.Vector.X_AXIS;
import static de.raytracing.raytracer.base.Vector.Z_AXIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.traceobjects.objects.Box;

public class BoxTest {

	@Test
	public void testGetCutPoints() throws Exception {
		Box box = new Box();

		Ray ray = new Ray(Z_AXIS.multiply(-10), Z_AXIS);

		CutPoint[] p = box.getCutPoints(ray);

		assertEquals(2, p.length);
		assertTrue(Z_AXIS.approxEquals(p[0].getCutPoint(ray)));
		assertTrue(Z_AXIS.neg().approxEquals(p[1].getCutPoint(ray)));

		ray = new Ray(X_AXIS.multiply(-10), Z_AXIS);

		p = box.getCutPoints(ray);

		assertEquals(0, p.length);
	}

}
