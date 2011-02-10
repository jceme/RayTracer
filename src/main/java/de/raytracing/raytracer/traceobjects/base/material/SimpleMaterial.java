package de.raytracing.raytracer.traceobjects.base.material;

import de.raytracing.raytracer.base.Color;

public class SimpleMaterial {

	private Color color;


	public SimpleMaterial() {
	}

	public SimpleMaterial(Color color) {
		setColor(color);
	}

	public SimpleMaterial(SimpleMaterial material) {
		this(material.getColor());
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "SimpleMaterial[color="+getColor()+"]";
	}

}
