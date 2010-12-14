package de.raytracing.raytracer.gui;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import de.raytracing.raytracer.base.Camera;
import de.raytracing.raytracer.base.Color;
import de.raytracing.raytracer.base.RenderCallback;
import de.raytracing.raytracer.base.Raytracer;
import de.raytracing.raytracer.base.Vector;
import de.raytracing.raytracer.traceobjects.base.TraceObject;
import de.raytracing.raytracer.traceobjects.base.material.Material;
import de.raytracing.raytracer.traceobjects.csg.Group;
import de.raytracing.raytracer.traceobjects.light.PointLight;
import de.raytracing.raytracer.traceobjects.modifier.Translate;
import de.raytracing.raytracer.traceobjects.objects.Plane;
import de.raytracing.raytracer.traceobjects.objects.Sphere;

public class RenderPanel extends JPanel implements RenderCallback {

	private static final long serialVersionUID = 1L;

	private Graphics graphics;

	private int renderCount = 0;


	public RenderPanel() {
		//setDoubleBuffered(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					System.out.println("Clicked at "+e.getX()+", "+e.getY()
							+": Color is "+new Robot().getPixelColor(e.getXOnScreen(), e.getYOnScreen()));
				} catch (AWTException e1) {
					throw new UnsupportedOperationException(e1);
				}
			}
		});
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		graphics = g;
		//System.out.println("Paint clipping "+graphics.getClipBounds());

		//render();
		//grayRender();
		renderNew();

		graphics = null;
	}

	private void renderNew() {
		Vector position = new Vector(0, 0, -10);
		Vector lookat   = new Vector(1e-10, 1e-10, 1e-10);

		Vector lightpos = new Vector(4, 1, -3); // position.add(new Vector(10, 10, 0));
		Color  lightcol = new Color(1);

		Camera camera = Camera.lookAt(position, lookat);

		Raytracer scene = new Raytracer(camera);
		scene.setSceneWidth(3);

		//scene.setInfiniteColor(Color.Blue.multiply(0.4));

		scene.addLights(new PointLight(lightcol, lightpos));
		//scene.addLights(new PointLight(lightcol, new Vector(6, 0, -5)));
		//scene.addLights(new PointLight(lightcol, position));

		//scene.addObjects(createNet(5, 2, new Sphere()));
		//scene.addObjects(new Sphere());
		/*scene.addObjects(new Translate(Vector.X_AXIS.multiply(2), new Sphere()));
		scene.addObjects(new Translate(Vector.Y_AXIS.multiply(2), new Sphere()));
		scene.addObjects(new Translate(Vector.Z_AXIS.multiply(2), new Sphere()));*/
		//scene.addObjects(new Plane(Vector.Y_AXIS));
		//scene.addObjects(new Translate(X_AXIS.multiply(2), new Rotate(Y_AXIS.multiply(45), new Plane(Z_AXIS.neg()))));
		//scene.addObjects(new Translate(X_AXIS.multiply(-2), new Rotate(Y_AXIS.multiply(-45), new Plane(Z_AXIS.neg()))));
		//scene.addObjects(new Plane(Vector.X_AXIS));

		/*scene.addObjects(new Intersection(
				new Translate(Vector.X_AXIS, new Sphere(2)),
				new Translate(Vector.X_AXIS.neg(), new Sphere(2))
		));*/
		/*scene.addObjects(new Intersection(
				new Sphere(2),
				new Plane(Vector.X_AXIS)
		));*/

		TraceObject obj;
		Material m;

		/*TraceObject obj1 = new Sphere(2);
		obj1.setColor(Color.Red);
		TraceObject obj2 = new Sphere(1.8);
		obj2.setColor(Color.Green);
		obj = new Difference(
				new Translate(Vector.X_AXIS.neg(), obj1)
				, new Translate(Vector.X_AXIS, obj2)
				, new Plane(Vector.Y_AXIS.neg(), -1.3)
				, new Plane(Vector.Y_AXIS, -1.3)
		);
		//obj.setColor(Color.Yellow);
		scene.addObjects(obj);*/

		/*
		obj = new Sphere();
		m = new Material(Color.Red);
		obj.setMaterial(m);

		TraceObject obj2 = new Sphere();
		m = new Material(Color.Green);
		m.setPhong(0.9);
		m.setDiffuse(0.1);
		obj2.setMaterial(m);

		obj = new Difference(obj, new Translate(new Vector(1, 0.5, -1), obj2));

		scene.addObjects(obj);*/

		obj = new Plane(Vector.Z_AXIS.neg(), 4);
		m = new Material(Color.Red);
		m.setTransparency(0.5);
		m.setRefraction(1.0);
		m.setFresnel(1.0);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = new Sphere();
		m = new Material(Color.Green);
		obj.setMaterial(m);

		scene.addObjects(obj);

		/*
		obj = new Sphere();
		m = new Material(Color.Red);
		//m.setReflection(0.4);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = Translate.move(0*1.5, 0, -1.5, new Sphere(1.4));
		m = new Material(Color.Green);
		m.setRefraction(1.0);
		m.setFresnel(1.0);
		m.setTransparency(0.8);
		obj.setMaterial(m);

		scene.addObjects(obj);*/

		/*
		scene.setRecursionDepth(5);

		obj = Translate.move(-1.2, 0, 2, new Sphere());
		m = new Material(Color.Red);
		m.setDiffuse(1);
		m.setPhong(0);
		m.setReflection(0.8);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = Translate.moveX(1.2, new Sphere());
		m = new Material(Color.Green);
		m.setDiffuse(1);
		m.setPhong(0);
		m.setReflection(0.8);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = Translate.moveZ(-1.2, new Sphere(0.4));
		m = new Material(Color.Blue);
		m.setDiffuse(1);
		m.setPhong(0);
		m.setReflection(0.6);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = Translate.moveZ(4, new Sphere(1.4));
		m = new Material(Color.Yellow);
		m.setDiffuse(1);
		m.setPhong(0);
		m.setReflection(0.6);
		obj.setMaterial(m);

		scene.addObjects(obj);
		*/

		/*obj = Translate.moveZ(5, new Plane(Vector.Z_AXIS.neg()));
		m = new Material(Color.White);
		m.setDiffuse(1);
		m.setPhong(0);
		m.setReflection(0.9);
		obj.setMaterial(m);

		scene.addObjects(obj);*/

		/*obj = new Sphere(0.5);
		m = new Material(Color.Red);
		m.setDiffuse(1.0);
		m.setPhong(0.0);
		obj.setMaterial(m);

		scene.addObjects(moveX(-2, obj));

		obj = new Sphere(0.5);
		m = new Material(Color.Red);
		m.setDiffuse(0.75);
		m.setPhong(0.25);
		obj.setMaterial(m);

		scene.addObjects(moveX(-1, obj));

		obj = new Sphere(0.5);
		m = new Material(Color.Red);
		m.setDiffuse(0.5);
		m.setPhong(0.5);
		obj.setMaterial(m);

		scene.addObjects(obj);

		obj = new Sphere(0.5);
		m = new Material(Color.Red);
		m.setDiffuse(0.25);
		m.setPhong(0.75);
		obj.setMaterial(m);

		scene.addObjects(moveX(1, obj));

		obj = new Sphere(0.5);
		m = new Material(Color.Red);
		m.setDiffuse(0.0);
		m.setPhong(1.0);
		obj.setMaterial(m);

		scene.addObjects(moveX(2, obj));*/

		/*obj = new Sphere();
		m = new Material(Color.Red);
		obj.setMaterial(m);

		scene.addObjects(obj);*/

		/*obj = new Cone(1.0, 1.0);
		obj.setColor(Color.Red);
		scene.addObjects(obj);*/

		/*obj = new Plane(Vector.Y_AXIS);
		obj.setMaterial(new Material(Color.Red));
		scene.addObjects(obj);*/

		//scene.addObjects(Rotate.rotX(90, new Cylinder(0.4, 2.0)));


		renderCount++;

		scene.render(getWidth(), getHeight(), this);

		System.out.println("Render count is "+renderCount);
	}

	@SuppressWarnings("unused")
	private Group createNet(int count, double dist, TraceObject object) {
		final List<Vector> list = new LinkedList<Vector>() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean add(Vector e) {
				boolean contained = false;

				for (Vector v : this) {
					if (v.approxEquals(e)) {
						contained = true;
						break;
					}
				}

				if (!contained) return super.add(e);
				return false;
			}
		};

		double d0 = dist / 2.0;
		int max = count / 2;

		if (count % 2 == 1) {
			d0 = 0;
			max++;
		}

		for (int x = 0; x < max; x++) {
		for (int z = 0; z < max; z++) {
			Vector v1 = new Vector(x * dist + d0, 0, z * dist + d0);
			Vector v2 = new Vector(-x * dist - d0, 0, z * dist + d0);
			Vector v3 = new Vector(x * dist + d0, 0, -z * dist - d0);
			Vector v4 = new Vector(-x * dist - d0, 0, -z * dist - d0);

			list.add(v1);
			list.add(v2);
			list.add(v3);
			list.add(v4);
		}
		}

		TraceObject[] objs = new TraceObject[list.size()];

		for (int i=0; i < list.size(); i++) {
			objs[i] = new Translate(list.get(i), object);
		}

		//System.out.println("Created group of "+objs.length+" "+object);

		return new Group(objs);
	}

	@Override
	public void rendered(int posX, int posY, Color color) {
		//System.out.println("Render "+posX+","+posY+" val="+value+" with color "+color);

		drawPixel(graphics, posX, posY, color);
	}

	private void drawPixel(Graphics g, int posX, int posY, Color color) {
		g.setColor(color.getAwtColor());
		g.drawLine(posX, posY, posX, posY);
	}

}
