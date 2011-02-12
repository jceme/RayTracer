package de.raytracing.raytracer.scenes;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.LightSource;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.csg.Difference;
import de.raytracing.raytracer.traceobjects.csg.Group;
import de.raytracing.raytracer.traceobjects.light.PointLight;
import de.raytracing.raytracer.traceobjects.modifier.Rotate;
import de.raytracing.raytracer.traceobjects.modifier.Translate;
import de.raytracing.raytracer.traceobjects.objects.Box;
import de.raytracing.raytracer.traceobjects.objects.Cylinder;
import de.raytracing.raytracer.traceobjects.objects.Plane;

public class PillarScene extends DefaultScene {

	private static final double RADIUS = 1.2;
	private static final double HEIGHT = 10;
	private static final int NOTCHES = 16;
	private static final double NOTCHRADIUS = 0.2;
	private static final double NOTCHINTO = 0.1;
	private static final double PLATEHEIGHT = 0.02 * HEIGHT;
	private static final double BASEPLATEHEIGHT = 0.08 * HEIGHT;
	private static final double ROOMWIDTH = 16;
	private static final double PILLARDIST = 40;


	public PillarScene() {
		TraceObject pillars = createPillars();

		pillars.setMaterial(new Material(new Color(1, 0.8, 0.8)));
		addSceneObject(pillars);

		TraceObject walls = createWalls();
		Material wallmaterial = new Material(new Color(0.4));
		walls.setMaterial(wallmaterial);
		addSceneObject(walls);

		TraceObject ceiling = new Plane(Vector.Y_AXIS.neg(),
				-(BASEPLATEHEIGHT + HEIGHT + 2 * PLATEHEIGHT));
		ceiling.setMaterial(wallmaterial);
		addSceneObject(ceiling);

		TraceObject floor = new Plane(Vector.Y_AXIS);
		floor.setMaterial(new Material(new Color(0.6, 0.1, 0)));
		addSceneObject(floor);

		double h = BASEPLATEHEIGHT + HEIGHT / 2;
		setCamera(Camera.lookAt(new Vector(0, h * 0.7, -15), new Vector(0, h, 10)));
		clearLightSources();
		addLightSource(createLight(0.3, -0.5));
		addLightSource(createLight(0.3, 0.5));
		addLightSource(createLight(0.3, 1.5));
	}

	private LightSource createLight(double intensity, double distance) {
		return new PointLight(new Color(intensity),
				new Vector(0, BASEPLATEHEIGHT + HEIGHT, PILLARDIST * distance));
	}

	private TraceObject createWalls() {
		return new Group(
				new Plane(Vector.X_AXIS, ROOMWIDTH / -2),
				new Plane(Vector.X_AXIS.neg(), ROOMWIDTH / -2),
				new Plane(Vector.Z_AXIS.neg(), -3 * PILLARDIST - 5)
		);
	}

	private TraceObject createPillars() {
		TraceObject pillar = createPillar();

		double x = ROOMWIDTH * 0.42 - RADIUS;

		return new Group(
				Translate.move(x, 0, 0, pillar),
				Translate.move(-x, 0, 0, pillar),

				Translate.move(x, 0, PILLARDIST, pillar),
				Translate.move(-x, 0, PILLARDIST, pillar),

				Translate.move(x, 0, 2 * PILLARDIST, pillar),
				Translate.move(-x, 0, 2 * PILLARDIST, pillar)
		);
	}

	private TraceObject createPillar() {
		TraceObject base = createPillarBase();

		return new Group(
				createPlate(RADIUS * 3, BASEPLATEHEIGHT, 0),

				Translate.moveY(BASEPLATEHEIGHT, base),

				// Top plates
				createPlate(RADIUS * 2.6, PLATEHEIGHT, BASEPLATEHEIGHT + HEIGHT),
				createPlate(RADIUS * 2.8, PLATEHEIGHT, BASEPLATEHEIGHT + HEIGHT + PLATEHEIGHT)
		);
	}

	private TraceObject createPlate(double len, double height, double base) {
		return new Box(
				new Vector(len / -2, base, len / -2),
				new Vector(len / 2, base + height, len / 2)
		);
	}

	private TraceObject createPillarBase() {
		TraceObject[] notches = new TraceObject[NOTCHES];
		for (int i=0; i < NOTCHES; i++) {
			notches[i] = Rotate.rotY((360.0 * i) / NOTCHES,
					Translate.moveX(RADIUS + NOTCHRADIUS * (1 - 2 * NOTCHINTO),
					new Cylinder(NOTCHRADIUS, HEIGHT * 1.1)));
		}

		TraceObject rawPillar = new Difference(new Cylinder(RADIUS, HEIGHT), notches);
		//TraceObject rawPillar = new Cylinder(RADIUS, HEIGHT);

		return Translate.moveY(HEIGHT / 2, rawPillar);
	}

	@Override
	public void modifyRaytracer(Raytracer raytracer) {
		super.modifyRaytracer(raytracer);
		raytracer.getRayTraceJob().setVoxel(4*40);
		//raytracer.setReconstructionFilterFactory(new SimpleReconstructionFilterFactory());
	}

}
