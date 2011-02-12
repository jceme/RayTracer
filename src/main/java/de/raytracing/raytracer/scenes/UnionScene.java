package de.raytracing.raytracer.scenes;

import static de.raytracing.raytracer.traceobjects.modifier.Translate.move;
import static de.raytracing.raytracer.traceobjects.modifier.Translate.moveX;
import static de.raytracing.raytracer.traceobjects.modifier.Translate.moveZ;
import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.csg.Group;
import de.raytracing.raytracer.traceobjects.csg.Intersection;
import de.raytracing.raytracer.traceobjects.csg.Union;
import de.raytracing.raytracer.traceobjects.light.LightFading;
import de.raytracing.raytracer.traceobjects.light.PointLight;
import de.raytracing.raytracer.traceobjects.objects.Plane;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class UnionScene extends DefaultScene {

	public UnionScene() {
		final TraceObject[] spheres = new TraceObject[] {
				moveX(1, new Sphere(2)),
				moveX(-1, new Sphere(2))
		};

		TraceObject group = moveZ(8, new Group(spheres));
		TraceObject union = moveZ(-4, new Union(spheres));
		TraceObject inter = move(-6, 0, 8, new Intersection(spheres));

		Material material = new Material(Color.White);
		material.setTransparency(0.8);
		material.setPhong(0.8);
		material.setPhongExp(8);
		material.setDiffuse(0.2);

		group.setMaterial(material);
		addSceneObject(group);
		union.setMaterial(material);
		addSceneObject(union);
		material = new Material(material);
		material.setColor(Color.Red);
		inter.setMaterial(material);
		addSceneObject(inter);

		TraceObject floor = new Plane(Vector.Y_AXIS, -2);
		floor.setMaterial(new Material(Color.White));
		addSceneObject(floor);


		setCamera(Camera.lookAt(new Vector(2, 4, -10), new Vector(-1, 0, 0)));
		clearLightSources();

		LightSource lightSource = new PointLight(new Color(1, 1, 0), new Vector(5, 10, -10));
		lightSource.setFading(LightFading.createLinearFadingParametric(10, 0.9, 30, 0.6));
		addLightSource(lightSource);
	}

}
