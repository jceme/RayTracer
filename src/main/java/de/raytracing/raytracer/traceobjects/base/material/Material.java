package de.raytracing.raytracer.traceobjects.base.material;

import de.raytracing.raytracer.base.Color;

public class Material extends SimpleMaterial {

	private Color ambient		= Color.Black;

	private double diffuse		= 0.7;
	private double diffuseExp	= 1.0;

	private double phong		= 0.3;
	private double phongExp		= 3.6;

	private double reflection	= 0.0;

	private double transparency	= 0.0;
	private double refraction	= 1.0;
	private double fresnel		= 1.0;


	public Material() {
	}

	public Material(Color color) {
		super(color);
	}

	public Material(Material material) {
		super(material);

		setAmbient(material.getAmbient());
		setDiffuse(material.getDiffuse());
		setDiffuseExp(material.getDiffuseExp());
		setPhong(material.getPhong());
		setPhongExp(material.getPhongExp());
		setReflection(material.getReflection());
		setTransparency(material.getTransparency());
		setRefraction(material.getRefraction());
		setFresnel(material.getFresnel());
	}

	public Color getAmbient() {
		return ambient;
	}
	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}
	public double getDiffuse() {
		return diffuse;
	}
	public void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}
	public double getDiffuseExp() {
		return diffuseExp;
	}
	public void setDiffuseExp(double diffuseExp) {
		this.diffuseExp = diffuseExp;
	}
	public double getPhong() {
		return phong;
	}
	public void setPhong(double phong) {
		this.phong = phong;
	}
	public double getPhongExp() {
		return phongExp;
	}
	public void setPhongExp(double phongExp) {
		this.phongExp = phongExp;
	}
	public double getReflection() {
		return reflection;
	}
	public void setReflection(double reflection) {
		this.reflection = reflection;
	}
	public double getTransparency() {
		return transparency;
	}
	public void setTransparency(double transparency) {
		this.transparency = transparency;
	}
	public double getRefraction() {
		return refraction;
	}
	public void setRefraction(double refraction) {
		this.refraction = refraction;
	}
	public double getFresnel() {
		return fresnel;
	}
	public void setFresnel(double fresnel) {
		this.fresnel = fresnel;
	}

	@Override
	public String toString() {
		return String.format(
				"Material[color=%s, diffuse=%.2f, phong=%.2f, reflection=%.2f, refraction=%.2f",
				getColor().toString(), getDiffuse(), getPhong(),
				getReflection(), getRefraction());
	}

}
