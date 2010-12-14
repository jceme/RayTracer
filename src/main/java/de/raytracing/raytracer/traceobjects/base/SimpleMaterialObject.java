package de.raytracing.raytracer.traceobjects.base;

import de.raytracing.raytracer.traceobjects.base.material.Materialed;
import de.raytracing.raytracer.traceobjects.base.material.SimpleMaterial;

public abstract class SimpleMaterialObject<T extends SimpleMaterial>
implements Materialed<T> {

	private T material;

	@Override
	public void setMaterial(T material) {
		this.material = material;
	}

	@Override
	public T getMaterial() {
		return material;
	}

}
