package de.raytracing.raytracer.base;

import static de.raytracing.raytracer.util.MiscUtils.checkParam;

import java.util.List;

import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Group;

public class RayTraceJob {

	private static final Color DEFAULT_INFINITE_COLOR = Color.Black;
	private static final Color DEFAULT_DEFAULT_COLOR = new Color(0.8);

	private final Scene scene;

	private final TraceObject sceneObject;

	// Optional extras

	private Color infiniteColor = DEFAULT_INFINITE_COLOR;
	private Color defaultColor  = DEFAULT_DEFAULT_COLOR;

	private int recursionDepth = 5;


	public RayTraceJob(Scene scene) {
		checkParam(scene, "scene");

		List<TraceObject> objs = scene.getSceneObjects();

		this.scene = scene;
		this.sceneObject = new Group(objs.toArray(new TraceObject[objs.size()]));
	}


	public Scene getScene() {
		return scene;
	}

	public TraceObject getSceneObject() {
		return sceneObject;
	}


	public Color getInfiniteColor() {
		return infiniteColor;
	}

	public void setInfiniteColor(Color infiniteColor) {
		this.infiniteColor = infiniteColor == null ? DEFAULT_INFINITE_COLOR : infiniteColor;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor == null ? DEFAULT_DEFAULT_COLOR : defaultColor;
	}

	public int getRecursionDepth() {
		return recursionDepth;
	}

	public void setRecursionDepth(int depth) {
		if (depth <= 0) throw new IllegalArgumentException("Invalid recursion depth: "+depth);
		this.recursionDepth = depth;
	}

}
