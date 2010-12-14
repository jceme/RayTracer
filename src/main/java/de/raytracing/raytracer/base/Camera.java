package de.raytracing.raytracer.base;

import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.toDegrees;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Camera {

	private static final double DEFAULT_FOCAL_DIST = 20.0;

	private final static Log log = LogFactory.getLog(Camera.class);

	private final Vector position;
	private final double focaldist;

	private final RotationMatrix matrix;


	public Camera() {
		this(Vector.ZERO);
	}

	public Camera(Vector position) {
		this(position, DEFAULT_FOCAL_DIST);
	}

	public Camera(Vector position, double focaldist) {
		this(position, Vector.ZERO, focaldist);
	}

	public Camera(Vector position, Vector rotation) {
		this(position, rotation, DEFAULT_FOCAL_DIST);
	}

	public Camera(Vector position, Vector rotation, double focaldist) {
		if (focaldist <= 0.0) throw new IllegalArgumentException("Invalid focal distance");

		matrix = new RotationMatrix(rotation);

		this.position = position;
		this.focaldist = focaldist;
	}


	public static Camera lookAt(final Vector position, final Vector lookat) {
		final Vector v = lookat.sub(position);
		if (v.isZero()) throw new IllegalArgumentException("Invalid look location");

		final Vector planeV = new Vector(v.x, 0, v.z);
		final double y = -acos(planeV.normalized().dot(Vector.Z_AXIS));

		final double x = asin(-v.y / v.length());

		final Vector rotation = new Vector(toDegrees(x), toDegrees(y), 0);
		if (log.isDebugEnabled()) {
			log.debug("Created Camera at "+position+" with rotation "+rotation);
		}

		return new Camera(position, rotation);
	}


	public Ray getRay(final Vector screenPoint) {
		final Vector target = new Vector(screenPoint.x, screenPoint.y, 0.0);

		final Vector raydir = target.sub(new Vector(0, 0, -focaldist));
		final Vector rotraydir = matrix.multiply(raydir);

		final Vector raystart = matrix.multiply(target).add(position);

		return new Ray(raystart, rotraydir);
	}


	@Override
	public String toString() {
		return "Camera[position="+position+", focaldist="+focaldist+
			", looking="+matrix.multiply(Vector.Z_AXIS).normalized()+"]";
	}

}
