package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.csg.Group;
import de.raytracing.raytracer.traceobjects.csg.Union;
import de.raytracing.raytracer.traceobjects.light.PointLight;
import de.raytracing.raytracer.traceobjects.modifier.Rotate;
import de.raytracing.raytracer.traceobjects.modifier.Translate;
import de.raytracing.raytracer.traceobjects.objects.Box;
import de.raytracing.raytracer.traceobjects.objects.Cone;
import de.raytracing.raytracer.traceobjects.objects.Cylinder;
import de.raytracing.raytracer.traceobjects.objects.Plane;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class ComplexMirrorScene extends DefaultScene {

	public ComplexMirrorScene() {
		final Vector roomdim = new Vector(14, 6, 14);
		final Vector campos = new Vector(roomdim.x * 0.2, roomdim.y / 2, roomdim.z * -0.2);
		TraceObject obj;
		Material material;
		double r;

		TraceObject room = createRoom(roomdim);
		addSceneObject(room);

		r = roomdim.y * 0.45;
		obj = new Sphere(r);
		material = new Material(Color.Blue);
		material.setReflection(0.4);
		obj.setMaterial(material);
		addSceneObject(Translate.move(roomdim.x / -2 + r, r, roomdim.z / 2 - r, obj));

		r = r * 0.6;
		obj = new Sphere(r);
		material = new Material(Color.Red);
		material.setPhong(0.9);
		material.setReflection(0.5);
		obj.setMaterial(material);
		addSceneObject(Translate.move(roomdim.x * -0.3 + r, r, roomdim.z * -0.1 - r, obj));

		r = 0.8;
		obj = new Cylinder(r, 2 * roomdim.y);
		material = new Material(new Color(0.3));
		material.setReflection(0.3);
		obj.setMaterial(material);
		addSceneObject(Translate.move(roomdim.x * 0.4 - r, roomdim.y, roomdim.z * 0.2, obj));

		obj = new Box(new Vector(roomdim.x / -2, 0, roomdim.z / -2),
				new Vector(roomdim.x * 0.1, roomdim.y * 0.8, roomdim.z * -0.48));
		material = new Material(Color.Green);
		obj.setMaterial(material);
		addSceneObject(obj);

		r = 0.8;
		obj = new Cone(r, r * 1.2);
		material = new Material(Color.Red);
		obj.setMaterial(material);
		addSceneObject(Translate.moveZ(roomdim.z * -0.48 + r, obj));

		obj = createQuyphus(1.5, 3);
		material = new Material(Color.White);
		material.setReflection(0.8);
		material.setTransparency(0.5);
		obj.setMaterial(material);
		addSceneObject(Translate.move(1.5, 0, 1, obj));



		setCamera(Camera.lookAt(campos, new Vector(roomdim.x * -0.25, roomdim.y / 2, roomdim.z / 2)));

		clearLightSources();
		addLightSource(new PointLight(new Color(1), new Vector(0, roomdim.y - 1, 0)));
		addLightSource(new PointLight(new Color(0.6), campos));
	}

	private TraceObject createRoom(Vector dim) {
		TraceObject floor;

		Group room = new Group(
				floor = new Plane(Vector.Y_AXIS),
				new Plane(Vector.Y_AXIS.neg(), -dim.y),

				new Plane(Vector.X_AXIS, dim.x / -2),
				new Plane(Vector.X_AXIS.neg(), dim.x / -2),

				new Plane(Vector.Z_AXIS, dim.z / -2),
				new Plane(Vector.Z_AXIS.neg(), dim.z / -2)
		);

		Material material = new Material(new Color(0.6));
		material.setPhong(0);
		room.setMaterial(material);

		material = new Material(material);
		material.setReflection(0.8);
		floor.setMaterial(material);

		return room;
	}


	private TraceObject createQuyphus(double radius, double height) {
		TraceObject cone = new Cone(radius, height);
		cone = Translate.moveY(height, Rotate.rotX(180, cone));

		double pitch = height / radius;
		double sradius = radius * Math.sqrt(1.0 + pitch*pitch) / pitch;
		double sd = Math.sqrt(sradius*sradius / (1.0 + pitch*pitch));

		TraceObject sphere = new Sphere(sradius);
		sphere = Translate.moveY(sd + height, sphere);

		return new Union(cone, sphere);
	}

	@Override
	public void modifyRaytracer(Raytracer raytracer) {
		super.modifyRaytracer(raytracer);

		raytracer.getRayTraceJob().setRecursionDepth(8);
		raytracer.getRayTraceJob().setVoxel(400);
	}

}
