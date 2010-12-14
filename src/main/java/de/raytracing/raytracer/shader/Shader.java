package de.raytracing.raytracer.shader;

import static java.lang.Math.max;
import static java.lang.Math.pow;

import java.util.List;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.CutPoint;
import de.raytracing.raytracer.base.Ray;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.util.GeometryUtils;

public class Shader {

	private final Raytracer scene;
	private final Ray ray;
	private final CutPoint cutPoint;
	private final Vector target;
	private Material material;


	public Shader(Raytracer scene, Ray ray, CutPoint cutPoint) {
		this.scene = scene;
		this.ray = ray;
		this.cutPoint = cutPoint;

		this.target = cutPoint.getCutPoint(ray);
		this.material = cutPoint.getFinalMaterial();
	}


	public Color shade(int recursion) {
		Color pointColor = getLightsColor();

		pointColor = getReflection(pointColor, recursion);

		pointColor = getTransparency(pointColor, recursion);

		return pointColor;
	}


	private Color getLightsColor() {
		Color color = Color.Black;
		Color phongColor = Color.Black;
		List<LightSource> lights = scene.getLights();

		for (LightSource light : lights) {
			// TODO Transparent shadows by global light tracing from a cutpoint to a light
			Color lightColor = getLightColor(light);

			Vector lightDirection = light.getDirection(target);
			lightDirection = lightDirection.neg().normalized();

			double phong = getPhong(lightDirection);

			phongColor = phongColor.add(lightColor.multiply(phong));

			double diffuse = getDiffuse(lightDirection);

			lightColor = lightColor.multiply(diffuse);

			color = color.add(lightColor);
		}

		Color ambient = getAmbient();
		color = color.add(ambient);

		Color pointColor = material.getColor().multiply(color);

		pointColor = pointColor.add(phongColor);

		return pointColor;
	}


	private double getDiffuse(Vector lightDirection) {
		double intensity = cutPoint.getNormal().dot(lightDirection);

		if (intensity <= 0.0) {
			return 0.0;
		}

		return max(0.0, material.getDiffuse() * pow(intensity, material.getDiffuseExp()));
	}


	private double getPhong(Vector lightDirection) {
		Vector reflectionRay = getReflectionRay().dir;

		double intensity = reflectionRay.dot(lightDirection);

		if (intensity <= 0.0) {
			return 0.0;
		}

		return max(0.0, material.getPhong() * pow(intensity, material.getPhongExp()));
	}


	private Color getAmbient() {
		return material.getAmbient();
	}


	private Color getReflection(Color pointColor, int recursion) {
		double reflection = material.getReflection();

		if (reflection > 0.0 && scene.canRecurse(recursion)) {
			Ray reflectRay = getReflectionRay();

			Color reflectColor = scene.trace(reflectRay, recursion);

			pointColor = pointColor.multiply(1.0 - reflection).add(
					reflectColor.multiply(reflection));
		}

		return pointColor;
	}


	private Ray getReflectionRay() {
		Vector refdir = ray.dir.mirror(cutPoint.getNormal());

		return createRay(refdir);
	}


	private Ray createRay(Vector refdir) {
		Ray refray = new Ray(target, refdir);

		refray = new Ray(refray.getPoint(1.2 * GeometryUtils.EPSILON), refdir);

		return refray;
	}


	private Color getTransparency(Color pointColor, int recursion) {
		double transparency = material.getTransparency();

		if (transparency > 0.0 && scene.canRecurse(recursion)) {
			double fresnel = getFresnelFactor();

			if (GeometryUtils.isGreaterZero(fresnel)) {
				Vector refdir = ray.dir.refract(
						cutPoint.getNormal(), material.getRefraction());

				if (!refdir.isZero()) {
					Ray refractRay = createRay(refdir);

					Color refractColor = scene.trace(refractRay, recursion);

					pointColor = pointColor.multiply(1.0 - transparency).add(
							refractColor.multiply(transparency));
				}
			}
		}

		return pointColor;
	}


	private double getFresnelFactor() {
		double angcos = ray.dir.neg().dot(cutPoint.getNormal());

		if (angcos <= 0.0) {
			return 0.0;
		}

		angcos = pow(angcos, material.getFresnel());

		return max(0.0, angcos);
	}


	private Color getLightColor(LightSource light) {
		return light.calcLight(target, scene);
	}

}
