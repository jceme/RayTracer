package de.raytracing.raytracer.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gui {

	private static String sceneName;


	public static void main(String[] args) {
		if (args.length > 0) {
			sceneName = args[0];
		}

		new Gui();
	}

	public static String getSceneName() {
		return sceneName;
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
