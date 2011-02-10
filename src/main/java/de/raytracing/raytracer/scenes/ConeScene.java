package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.light.PointLight;
import de.raytracing.raytracer.traceobjects.objects.Cone;

public class ConeScene extends DefaultScene {

	public ConeScene() {
		double size = 4;
		TraceObject cone = new Cone(size * 0.8, size);
		addSceneObject(cone);

		clearLightSources();
		addLightSource(new PointLight(new Vector(0, 4, -10)));

		setCamera(Camera.lookAt(new Vector(0, 4, -10), new Vector(0, size / 2, 0)));
	}

}
