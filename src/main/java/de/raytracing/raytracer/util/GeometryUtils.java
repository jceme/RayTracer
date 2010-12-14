package de.raytracing.raytracer.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.raytracing.raytracer.base.Approximate;
import de.raytracing.raytracer.base.Ray;


public class GeometryUtils {

	public static final double INFINIT_DISTANCE = Double.POSITIVE_INFINITY;

	public static final double EPSILON = 1e-10;

	public static double getMinDistance(final Ray ray, double... distances) {
		double dist = INFINIT_DISTANCE;

		for (double distance : distances) {
			distance = Math.max(0.0, ray == null ? distance : calcDistance(ray, distance));

			if (distance >= 0.0 && distance < dist) {
				dist = distance;
			}
		}

		return dist;
	}

	public static double calcDistance(final Ray ray, double t) {
		return t * ray.dir.length();
	}

	public static boolean isZero(double value) {
		return Math.abs(value) < EPSILON;
	}

	public static boolean isLowerEqZero(double value) {
		return value < EPSILON;
	}

	public static boolean isGreaterZero(double value) {
		return value >= EPSILON;
	}

	public static boolean approxEquals(double a, double b) {
		return isZero(a - b);
	}

	public static <T extends Approximate<? super T>> T[] collectApproxEquals(T[][] objects) {
		List<T> list = new LinkedList<T>();

		for (T[] object : objects) {
			l1: for (T obj : object) {
				for (T listobj : list) {
					if (obj.approxEquals(listobj)) {
						continue l1;
					}
				}

				list.add(obj);
			}
		}

		Class<?> type = objects.getClass().getComponentType().getComponentType();

		return copyList(type, list);
	}

	public static <T> T[] copyList(Class<?> type, Collection<T> list) {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(type, list.size());

		Iterator<T> it = list.iterator();
		for (int i=0; it.hasNext(); i++) {
			result[i] = it.next();
		}

		return result;
	}

	public static <T> T[] filterList(T[] objects, Filter<T> filter) {
		List<T> list = new LinkedList<T>(Arrays.asList(objects));

		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			if (!filter.accept(it.next())) {
				it.remove();
			}
		}

		Class<?> type = objects.getClass().getComponentType();

		return copyList(type, list);
	}

	public static interface Filter<T> {

		public boolean accept(T object);

	}

}
