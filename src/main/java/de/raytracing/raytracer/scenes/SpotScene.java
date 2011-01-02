package de.raytracing.raytracer.scenes;

import static de.raytracing.raytracer.base.Vector.Y_AXIS;
import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.light.SpotLight;
import de.raytracing.raytracer.traceobjects.objects.Plane;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class SpotScene extends DefaultScene {

	private static final double RADIUS = 3.0;


	public SpotScene() {
		setCamera(Camera.lookAt(new Vector(0, 2, -10), Vector.ZERO));

		Plane plane = new Plane(Y_AXIS, -RADIUS);
		plane.setMaterial(new Material(Color.White));
		addSceneObject(plane);

		Sphere sphere = new Sphere(RADIUS);
		sphere.setMaterial(new Material(Color.White));
		addSceneObject(sphere);

		clearLightSources();
		addLight(new Vector(-3, 8, -10), Color.Green);
		addLight(new Vector(3, 8, -10), Color.Red);
	}


	private void addLight(Vector location, Color color) {
		SpotLight light = new SpotLight(color, location, Vector.ZERO);
		light.setSpotAngles(20, 0);
		light.setFallOff(1);
		addLightSource(light);
	}

}
