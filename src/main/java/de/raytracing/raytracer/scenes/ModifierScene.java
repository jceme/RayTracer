package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.light.SpotLight;
import de.raytracing.raytracer.traceobjects.modifier.Rotate;
import de.raytracing.raytracer.traceobjects.modifier.Translate;
import de.raytracing.raytracer.traceobjects.objects.Box;
import de.raytracing.raytracer.traceobjects.objects.Cylinder;
import de.raytracing.raytracer.traceobjects.objects.Plane;

public class ModifierScene extends DefaultScene {

	public ModifierScene() {
		TraceObject obj;
		final Material material = new Material(Color.Yellow);

		obj = new Cylinder(1, 6);
		obj = Rotate.rotZ(-45, obj);
		obj = Rotate.rotY(30, obj);
		obj = Translate.move(-5, 0, 10, obj);

		obj.setMaterial(material);
		addSceneObject(obj);


		obj = new Box(4);
		obj = Rotate.rotY(45, obj);
		obj = Rotate.rotX(45, obj);
		obj = Rotate.rotY(20, obj);

		obj.setMaterial(material);
		addSceneObject(obj);


		final TraceObject floor = new Plane(Vector.Y_AXIS, -4);

		floor.setMaterial(new Material(Color.White));
		addSceneObject(floor);

		clearLightSources();
		addLightSource(new SpotLight(Color.Red, new Vector(0, 10, 0), Vector.ZERO)
			.setSpotAngles(20, 10).setFallOff(0.6));

		addLightSource(new SpotLight(Color.Green, new Vector(-5, 10, 10), Vector.ZERO)
			.setSpotAngles(20, 10).setFallOff(2));

		addLightSource(new SpotLight(Color.Green, new Vector(-5, 10, 10), new Vector(-5, 0, 10))
		.setSpotAngles(20, 10).setFallOff(2));
	}

}
