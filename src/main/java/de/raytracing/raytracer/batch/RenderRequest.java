package de.raytracing.raytracer.batch;

import java.util.LinkedList;
import java.util.List;

import de.raytracing.raytracer.base.Scene;

public class RenderRequest {

	private int width;
	private int height;
	private List<Scene> scenes = new LinkedList<Scene>();


	public void setDimension(int width, int height) {
		if (width <= 0 || height <= 0) throw new IllegalArgumentException("Invalid dimension");

		this.width = width;
		this.height = height;
	}

	public void addScene(Scene scene) {
		scenes.add(scene);
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Scene> getScenes() {
		return scenes;
	}

}
