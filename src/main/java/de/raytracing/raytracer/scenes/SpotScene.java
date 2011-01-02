package de.raytracing.raytracer.scenes;

import static de.raytracing.raytracer.base.Color.Blue;
import static de.raytracing.raytracer.base.Color.Red;
import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.objects.Plane;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class SpotScene extends DefaultScene {

	private static final double RADIUS = 1.0;


	public SpotScene() {
		Plane plane = new Plane(Y_AXIS, -RADIUS);
		plane.setMaterial(new Material(Blue));
		addSceneObject(plane);

		Sphere sphere = new Sphere(RADIUS);
		sphere.setMaterial(new Material(Red));
		addSceneObject(sphere);
	}

}
