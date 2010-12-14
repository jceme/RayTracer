package de.raytracing.raytracer.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.raytracing.raytracer.base.Approximate;
import de.raytracing.raytracer.util.GeometryUtils;
import de.raytracing.raytracer.util.GeometryUtils.Filter;

public class GeometryUtilsTest {

	private static class Approx implements Approximate<Approx> {

		public int n;

		public Approx(int n) {
			this.n = n;
		}

		@Override
		public boolean approxEquals(Approx s) {
			return n == s.n;
		}

		@Override
		public String toString() {
			return "Approx["+n+"]";
		}
	}


	@Test
	public void testcollectApproxEquals() throws Exception {
		Approx[][] list = new Approx[][] {
			new Approx[] {
					new Approx(2),
					new Approx(2)
			},
			new Approx[] {
					new Approx(4),
					new Approx(2),
					new Approx(1)
			}
		};


		Approx[] result = GeometryUtils.collectApproxEquals(list);

		assertEquals(3, result.length);
		assertEquals(2, result[0].n);
		assertEquals(4, result[1].n);
		assertEquals(1, result[2].n);
	}

	@Test
	public void testFilterList() throws Exception {
		Integer[] list = new Integer[] { 2, 3, 1, 6, 8, 9, 4, 5, 2, 1, 0, 5 };

		Filter<Integer> evenFilter = new Filter<Integer>() {
			@Override
			public boolean accept(Integer i) {
				return i % 2 == 0;
			}
		};

		Filter<Integer> unevenFilter = new Filter<Integer>() {
			@Override
			public boolean accept(Integer i) {
				return i % 2 != 0;
			}
		};

		Integer[] result;

		result = GeometryUtils.filterList(list, evenFilter);

		assertArrayEquals(new Integer[] { 2, 6, 8, 4, 2, 0 }, result);

		result = GeometryUtils.filterList(list, unevenFilter);

		assertArrayEquals(new Integer[] { 3, 1, 9, 5, 1, 5 }, result);
	}

}
