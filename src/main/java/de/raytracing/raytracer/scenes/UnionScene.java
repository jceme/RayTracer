package de.raytracing.raytracer.scenes;

import static de.raytracing.raytracer.traceobjects.modifier.Translate.moveX;
import static de.raytracing.raytracer.traceobjects.modifier.Translate.moveZ;
import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.csg.Group;
import de.raytracing.raytracer.traceobjects.csg.Union;
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

		Material material = new Material(Color.White);
		material.setTransparency(0.8);
		material.setPhong(0.8);
		material.setPhongExp(8);
		material.setDiffuse(0.2);

		group.setMaterial(material);
		addSceneObject(group);
		union.setMaterial(material);
		addSceneObject(union);

		TraceObject floor = new Plane(Vector.Y_AXIS, -2);
		floor.setMaterial(new Material(Color.Red));
		addSceneObject(floor);


		setCamera(Camera.lookAt(new Vector(2, 4, -10), Vector.ZERO));
		clearLightSources();
		addLightSource(new PointLight(Color.White, new Vector(5, 10, -10)));
	}

}
