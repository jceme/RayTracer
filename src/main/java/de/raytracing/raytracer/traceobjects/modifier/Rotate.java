package de.raytracing.raytracer.traceobjects.modifier;

import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.RotationMatrix;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.MaterialObject;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

/**
 * A rotation object rotating around specified axis by angle in degrees using
 * <b>left-hand orientation</b>.
 *
 * @author daniel
 */
public class Rotate extends MaterialObject implements TraceObject {

	private final TraceObject object;
	private final RotationMatrix matrix;


	public static Rotate rotX(double angle, TraceObject object) {
		return new Rotate(new Vector(angle, 0, 0), object);
	}

	public static Rotate rotY(double angle, TraceObject object) {
		return new Rotate(new Vector(0, angle, 0), object);
	}

	public static Rotate rotZ(double angle, TraceObject object) {
		return new Rotate(new Vector(0, 0, angle), object);
	}


	public Rotate(Vector rotation, TraceObject object) {
		this.object = object;
		this.matrix = new RotationMatrix(rotation.neg());
	}

	@Override
	public CutPoint[] getCutPoints(final Ray ray) {
		return propagateMaterials(object.getCutPoints(new Ray(matrix.multiply(ray.start),
				matrix.multiply(ray.dir))));
	}

	@Override
	public boolean isInside(Vector point) {
		return object.isInside(matrix.multiply(point));
	}

}
