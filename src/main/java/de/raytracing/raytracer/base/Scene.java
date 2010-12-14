package de.raytracing.raytracer.base;

import java.util.List;

import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.TraceObject;

public interface Scene {

	public Camera getCamera();

	public double getSceneWidth();

	public List<TraceObject> getSceneObjects();

	public List<LightSource> getLightSources();

}
