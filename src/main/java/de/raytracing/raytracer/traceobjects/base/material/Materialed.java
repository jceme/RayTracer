package de.raytracing.raytracer.traceobjects.base.material;


public interface Materialed<T extends SimpleMaterial> {

	public void setMaterial(T material);

	public T getMaterial();

	public T getFinalMaterial();

}
