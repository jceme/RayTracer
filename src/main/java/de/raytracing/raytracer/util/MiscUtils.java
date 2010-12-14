package de.raytracing.raytracer.util;

public class MiscUtils {

	private MiscUtils() {
	}


	/**
	 * Ensures that parameter is not null.
	 *
	 * @param parameter the parameter
	 * @param name the parameter name
	 */
	public static void checkParam(Object parameter, String name) {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter "+name+" required");
		}
	}

}
