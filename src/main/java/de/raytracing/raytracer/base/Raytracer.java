package de.raytracing.raytracer.base;

import static de.raytracing.raytracer.base.CutPoint.getNearestCutPoint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.raytracing.raytracer.shader.Shader;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.csg.Group;

public class Raytracer {

	private Color infiniteColor = Color.Black;
	private Color defaultColor  = new Color(0.8);

	private Camera camera;
	private double sceneWidth = 10.0;
	private List<TraceObject> objects = new LinkedList<TraceObject>();
	private List<LightSource> lights = new LinkedList<LightSource>();

	private int recursionDepth = 5;
	private TraceObject traceObject;


	public Raytracer(Camera camera) {
		setCamera(camera);
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public void setSceneWidth(double sceneWidth) {
		if (sceneWidth <= 0.0) throw new IllegalArgumentException("Invalid width");
		this.sceneWidth = sceneWidth;
	}

	public void setInfiniteColor(Color infiniteColor) {
		this.infiniteColor = infiniteColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public void setRecursionDepth(int recursionDepth) {
		this.recursionDepth = recursionDepth;
	}



	public void addObjects(TraceObject... objects) {
		this.objects.addAll(Arrays.asList(objects));
	}

	public void addLights(LightSource... lights) {
		this.lights.addAll(Arrays.asList(lights));
	}


	public List<LightSource> getLights() {
		return lights;
	}


	public void render(int width, int height, RenderCallback callback) {
		final double sceneHeight = (sceneWidth * height) / width;
		final double dx = sceneWidth / width;
		final double dy = -sceneHeight / height;
		final double x0 = -sceneWidth / 2.0 + dx / 2.0;
		final double y0 = sceneHeight / 2.0 + dy / 2.0;

		this.traceObject = new Group(this.objects.toArray(
				new TraceObject[this.objects.size()]));

		System.out.println("Rendering "+width+"x"+height);

		for (int x = 0; x < width; x++) {
		for (int y = 0; y < height; y++) {
			// Use reconstruction filter with multiple values per pixel
			Vector screenPoint = new Vector(x * dx + x0, y * dy + y0, 0);

			if (x == 142 && y == 88) {
				x += 0;
			}

			Ray initRay = camera.getRay(screenPoint);

			Color color = trace(initRay, recursionDepth);

			callback.rendered(x, y, color);
		}
		}
	}

	public Color trace(Ray ray, int recursion) {
		recursion--;

		CutPoint cutPoint = getNearestCutPoint(getCutPoints(ray));

		if (cutPoint == null) {
			return infiniteColor;
		}

		cutPoint.trySetColor(defaultColor);

		final Shader shader = new Shader(this, ray, cutPoint);

		Color color = shader.shade(recursion);

		return color;
	}

	public boolean canRecurse(int recursion) {
		return recursion >= 0;
	}

	public CutPoint[] getCutPoints(Ray ray) {
		return traceObject.getCutPoints(ray);
	}

}
