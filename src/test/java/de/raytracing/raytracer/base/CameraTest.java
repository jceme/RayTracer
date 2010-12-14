package de.raytracing.raytracer.base;

import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import static de.raytracing.raytracer.base.Vector.ZERO;
import static de.raytracing.raytracer.base.Vector.Z_AXIS;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Vector;

public class CameraTest {

	@Test
	public void testStraightCam() throws Exception {
		final double focaldist = 10.0;
		Camera camera = new Camera(ZERO, ZERO, focaldist);

		Ray ray = camera.getRay(ZERO);

		assertTrue(ray.start.approxEquals(ZERO));
		assertTrue(ray.dir.approxEquals(Z_AXIS));

		ray = camera.getRay(Y_AXIS);

		assertTrue(ray.start.approxEquals(Y_AXIS));
		assertTrue(ray.dir.isParallel(new Vector(0, 1, focaldist)));
	}

}
