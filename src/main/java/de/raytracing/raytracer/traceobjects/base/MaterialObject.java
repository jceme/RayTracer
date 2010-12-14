package de.raytracing.raytracer.traceobjects.base;

import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.base.material.Materialed;

public abstract class MaterialObject extends SimpleMaterialObject<Material> {

	@Override
	public Material getFinalMaterial() {
		Material material = getMaterial();
		if (material == null) material = new Material();

		return material;
	}

	protected <T extends Materialed<Material>> T propagateMaterial(final T object) {
		Material material = getMaterial();
		if (material != null) {
			object.setMaterial(material);
		}

		return object;
	}

	protected <T extends Materialed<Material>> T[] propagateMaterials(final T... objects) {
		for (T object : objects) {
			propagateMaterial(object);
		}

		return objects;
	}

}
