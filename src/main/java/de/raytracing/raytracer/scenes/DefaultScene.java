package de.raytracing.raytracer.scenes;

import java.util.LinkedList;
import java.util.List;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Scene;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.light.PointLight;

public class DefaultScene implements Scene {

	private static Scene scene = new SimpleRedSphere();

	public static Scene getDefaultScene() {
		return scene;
	}

	public static void setDefaultScene(Scene scene) {
		DefaultScene.scene = scene;
	}



	private Camera camera;
	private double sceneWidth = 10.0;
	private List<TraceObject> sceneObjects;
	private List<LightSource> lightSources;


	public DefaultScene() {
		camera = Camera.lookAt(new Vector(0, 2, -10), Vector.ZERO);

		sceneObjects = new LinkedList<TraceObject>();

		lightSources = new LinkedList<LightSource>();

		lightSources.add(new PointLight(Color.White.multiply(0.8), new Vector(4, 6, -10)));
	}


	@Override
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		if (camera == null) throw new IllegalArgumentException("Camera required");
		this.camera = camera;
	}


	@Override
	public double getSceneWidth() {
		return sceneWidth;
	}

	public void setSceneWidth(double width) {
		if (width <= 0) throw new IllegalArgumentException("Invalid scene width: "+width);
		this.sceneWidth = width;
	}


	@Override
	public List<TraceObject> getSceneObjects() {
		return sceneObjects;
	}

	public void setSceneObjects(List<TraceObject> sceneObjects) {
		if (sceneObjects == null) throw new IllegalArgumentException("Objects required");
		this.sceneObjects = sceneObjects;
	}

	public DefaultScene addSceneObject(TraceObject object) {
		getSceneObjects().add(object);
		return this;
	}

	public void clearSceneObjects() {
		getSceneObjects().clear();
	}


	@Override
	public List<LightSource> getLightSources() {
		return lightSources;
	}

	public void setLightSources(List<LightSource> lightSources) {
		if (lightSources == null) throw new IllegalArgumentException("Light sources required");
		this.lightSources = lightSources;
	}

	public DefaultScene addLightSource(LightSource lightSource) {
		getLightSources().add(lightSource);
		return this;
	}

	public void clearLightSources() {
		getLightSources().clear();
	}

}
