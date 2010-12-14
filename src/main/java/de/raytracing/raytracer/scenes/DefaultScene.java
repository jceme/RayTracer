package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Scene;

public class DefaultScene {

	public static Scene getDefaultScene() {
		return new SimpleRedSphere();
	}

}
