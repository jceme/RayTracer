package de.raytracing.raytracer.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gui {

	public static void main(String[] args) {
		new Gui();
	}


	public Gui() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("RayTracer Test");

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				RenderPanel panel = new RenderPanel();

				frame.add(panel);

				frame.setSize(800, 600);
				frame.setVisible(true);
			}
		});
	}

}
