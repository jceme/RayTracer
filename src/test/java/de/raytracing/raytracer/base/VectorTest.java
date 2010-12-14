package de.raytracing.raytracer.base;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import de.raytracing.raytracer.base.Vector;

public class VectorTest {

	@Ignore
	@Test
	public void testMirror() throws Exception {
		doTestMirror(-1, 1, 0,		0, 1, 0,	1, 1, 0);
		doTestMirror(-1, 2, 0,		0, 1, 0,	1, 2, 0);
		doTestMirror(-2, 1, 0,		0, 1, 0,	2, 1, 0);
		doTestMirror(1, 0, 0,		1, 0, 1,	0, 0, 1);
		doTestMirror(2, 0, 0,		1, 0, 1,	0, 0, 2);
		doTestMirror(1, 0, 0,		2, 0, 2,	0, 0, 1);
	}

	private void doTestMirror(double vx, double vy, double vz,
			double mx, double my, double mz,
			double ex, double ey, double ez) {
		Vector vector = new Vector(vx, vy, vz);
		Vector mirror = new Vector(mx, my, mz);

		Vector result = vector.mirror(mirror);
		Vector expected = new Vector(ex, ey, ez);

		assertTrue(result.approxEquals(expected));
	}

	@Test
	public void testRefract() throws Exception {
		doTestRefract(1,	-1, -1, 0,		0, 1, 0,	-1, -1, 0);
		doTestRefract(1.33,	-1, -1, 0,		0, 1, 0,	-1, -1, 0);
	}

	private void doTestRefract(double ratio, double vx, double vy, double vz,
			double mx, double my, double mz,
			double ex, double ey, double ez) {
		Vector vector = new Vector(vx, vy, vz).normalized();
		Vector normal = new Vector(mx, my, mz).normalized();

		Vector result = vector.refract(normal, ratio);
		Vector expected = new Vector(ex, ey, ez).normalized();

		assertTrue(result.approxEquals(expected));
	}

}
