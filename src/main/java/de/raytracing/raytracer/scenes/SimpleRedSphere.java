package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.DefaultScene;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class SimpleRedSphere extends DefaultScene {

	public SimpleRedSphere() {
		Sphere sphere = new Sphere(1.0);

		Material material = new Material(Color.Red);

		sphere.setMaterial(material);

		addSceneObject(sphere);

		setSceneWidth(3.0);
	}

}
