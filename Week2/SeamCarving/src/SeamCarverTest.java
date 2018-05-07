import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;

class SeamCarverTest {

	Picture picture1;
	SeamCarver sc1;
	final double EPSILON = 1e-11;
	
	void setUp() {
		picture1 = new Picture(3, 4);
		picture1.set(0, 0, new Color(255, 101, 51));
		picture1.set(1, 0, new Color(255, 101,153));
		picture1.set(2, 0, new Color(255, 101, 255));
		picture1.set(0, 1, new Color(255, 153, 51));
		picture1.set(1, 1, new Color(255, 153, 153));
		picture1.set(2, 1, new Color(255, 153, 255));
		picture1.set(0, 2, new Color(255, 203, 51));
		picture1.set(1, 2, new Color(255, 204, 153));
		picture1.set(2, 2, new Color(255, 205, 255));
		picture1.set(0, 3, new Color(255, 255, 51));
		picture1.set(1, 3, new Color(255, 255, 153));
		picture1.set(2, 3, new Color(255, 255, 255));
		
		sc1 = new SeamCarver(picture1);
	}

	@Test
	void dimensionsTest1() {
		setUp();
		assertEquals(sc1.width(), picture1.width(), EPSILON);
		assertEquals(sc1.height(), picture1.height(), EPSILON);
	}
	
	@Test
	void energiesTest1() {
		setUp();
		assertEquals(Math.pow(sc1.energy(0, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 1), 2.0), 52225.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 2), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 2), 2.0), 52024.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 2), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 3), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 3), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 3), 2.0), 1000000.00, EPSILON);		
	}
	
	@Test
	void findVerticalSeamTest1() {
		setUp();
		final int[] vSeam = sc1.findVerticalSeam();
		assertEquals(vSeam.length, picture1.height());
		assertEquals(vSeam[0], 0);
		assertEquals(vSeam[1], 1);
		assertEquals(vSeam[2], 1);
		assertEquals(vSeam[3], 0);		
	}
	
	@Test
	void removeVerticalSeamTest1() {
		setUp();
		final int[] vSeam = sc1.findVerticalSeam();
		assertEquals(vSeam.length, picture1.height());
		assertEquals(vSeam[0], 0);
		assertEquals(vSeam[1], 1);
		assertEquals(vSeam[2], 1);
		assertEquals(vSeam[3], 0);		

		sc1.removeVerticalSeam(vSeam);
		final Picture p = sc1.picture();
		
		// Validate Dimensions
		assertEquals(sc1.width(), picture1.width()-1);
		assertEquals(sc1.height(), picture1.height());
		assertEquals(p.width(), picture1.width()-1);
		assertEquals(p.height(), picture1.height());
		
		// Validate Energies
		assertEquals(Math.pow(sc1.energy(0, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 2), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 2), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 3), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 3), 2.0), 1000000.00, EPSILON);

		// Validate Colors of new picture.
		assertEquals(p.get(0, 0).getRGB(), picture1.get(1, 0).getRGB());
		assertEquals(p.get(1, 0).getRGB(), picture1.get(2, 0).getRGB());
		
		assertEquals(p.get(0, 1).getRGB(), picture1.get(0, 1).getRGB());
		assertEquals(p.get(1, 1).getRGB(), picture1.get(2, 1).getRGB());
		
		assertEquals(p.get(0, 2).getRGB(), picture1.get(0, 2).getRGB());
		assertEquals(p.get(1, 2).getRGB(), picture1.get(2, 2).getRGB());
		
		assertEquals(p.get(0, 3).getRGB(), picture1.get(1, 3).getRGB());
		assertEquals(p.get(1, 3).getRGB(), picture1.get(2, 3).getRGB());
	}
	
	@Test
	void findHorizontalSeamTest1() {
		setUp();
		final int[] hSeam = sc1.findHorizontalSeam();
		assertEquals(hSeam.length, picture1.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);
	}
	
	@Test
	void removeHorizontalSeamTest1() {
		setUp();
		final int[] hSeam = sc1.findHorizontalSeam();
		assertEquals(hSeam.length, picture1.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);

		sc1.removeHorizontalSeam(hSeam);
		final Picture p = sc1.picture();
		
		// Validate Dimensions
		assertEquals(sc1.width(), picture1.width());
		assertEquals(sc1.height(), picture1.height()-1);
		assertEquals(p.width(), picture1.width());
		assertEquals(p.height(), picture1.height()-1);
		
		// Validate Energies
		assertEquals(Math.pow(sc1.energy(0, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(0, 2), 2.0), 1000000.00, EPSILON);
		
		assertEquals(Math.pow(sc1.energy(1, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 1), 2.0), 65336.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(1, 2), 2.0), 1000000.00, EPSILON);
		
		assertEquals(Math.pow(sc1.energy(2, 0), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 1), 2.0), 1000000.00, EPSILON);
		assertEquals(Math.pow(sc1.energy(2, 2), 2.0), 1000000.00, EPSILON);		

		// Validate Colors of new picture.	
		assertEquals(p.get(0, 0).getRGB(), picture1.get(0, 0).getRGB());
		assertEquals(p.get(0, 1).getRGB(), picture1.get(0, 2).getRGB());
		assertEquals(p.get(0, 2).getRGB(), picture1.get(0, 3).getRGB());

		assertEquals(p.get(1, 0).getRGB(), picture1.get(1, 0).getRGB());
		assertEquals(p.get(1, 1).getRGB(), picture1.get(1, 1).getRGB());
		assertEquals(p.get(1, 2).getRGB(), picture1.get(1, 3).getRGB());

		assertEquals(p.get(2, 0).getRGB(), picture1.get(2, 0).getRGB());
		assertEquals(p.get(2, 1).getRGB(), picture1.get(2, 2).getRGB());
		assertEquals(p.get(2, 2).getRGB(), picture1.get(2, 3).getRGB());
	}
	
	@Test
	void energiesTest6x5() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/6x5.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final double eps = 1e-2;
		
		assertEquals(sc.energy(0, 0), 1000.00, eps);
		assertEquals(sc.energy(1, 0), 1000.00, eps);
		assertEquals(sc.energy(2, 0), 1000.00, eps);
		assertEquals(sc.energy(3, 0), 1000.00, eps);
		assertEquals(sc.energy(4, 0), 1000.00, eps);
		assertEquals(sc.energy(5, 0), 1000.00, eps);
		
		assertEquals(sc.energy(0, 1), 1000.00, eps);
		assertEquals(sc.energy(1, 1), 237.35, eps);
		assertEquals(sc.energy(2, 1), 151.02, eps);
		assertEquals(sc.energy(3, 1), 234.09, eps);
		assertEquals(sc.energy(4, 1), 107.89, eps);
		assertEquals(sc.energy(5, 1), 1000.00, eps);
		
		assertEquals(sc.energy(0, 2), 1000.00, eps);
		assertEquals(sc.energy(1, 2), 138.69, eps);
		assertEquals(sc.energy(2, 2), 228.10, eps);
		assertEquals(sc.energy(3, 2), 133.07, eps);
		assertEquals(sc.energy(4, 2), 211.51, eps);
		assertEquals(sc.energy(5, 2), 1000.00, eps);
		
		assertEquals(sc.energy(0, 3), 1000.00, eps);
		assertEquals(sc.energy(1, 3), 153.88, eps);
		assertEquals(sc.energy(2, 3), 174.01, eps);
		assertEquals(sc.energy(3, 3), 284.01, eps);
		assertEquals(sc.energy(4, 3), 194.50, eps);
		assertEquals(sc.energy(5, 3), 1000.00, eps);
		
		assertEquals(sc.energy(0, 4), 1000.00, eps);
		assertEquals(sc.energy(1, 4), 1000.00, eps);
		assertEquals(sc.energy(2, 4), 1000.00, eps);
		assertEquals(sc.energy(3, 4), 1000.00, eps);
		assertEquals(sc.energy(4, 4), 1000.00, eps);
		assertEquals(sc.energy(5, 4), 1000.00, eps);
	}

	@Test
	void findVerticalSeamTest6x5() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/6x5.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 3);
		assertEquals(vSeam[1], 4);
		assertEquals(vSeam[2], 3);
		assertEquals(vSeam[3], 2);
		assertEquals(vSeam[4], 1);
	}
	
	@Test
	void removeVerticalSeamTest6x5() {

		final double eps = 1e-2;
		
		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/6x5.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		sc.removeVerticalSeam(vSeam);
		final Picture p = sc.picture();
		
		// Validate Dimensions
		assertEquals(sc.width(), picture.width()-1);
		assertEquals(sc.height(), picture.height());
		assertEquals(p.width(), picture.width()-1);
		assertEquals(p.height(), picture.height());
		
		// Validate Energies
		assertEquals(sc.energy(0, 0), 1000.00, eps);
		assertEquals(sc.energy(1, 0), 1000.00, eps);
		assertEquals(sc.energy(2, 0), 1000.00, eps);
		assertEquals(sc.energy(3, 0), 1000.00, eps);
		assertEquals(sc.energy(4, 0), 1000.00, eps);		

		assertEquals(sc.energy(0, 1), 1000.00, eps);
		assertEquals(sc.energy(1, 1), 237.35, eps);
		assertEquals(sc.energy(2, 1), 151.02, eps);
		assertEquals(sc.energy(3, 1), 151.28, eps);
		assertEquals(sc.energy(4, 1), 1000.00, eps);
		
		assertEquals(sc.energy(0, 2), 1000.00, eps);
		assertEquals(sc.energy(1, 2), 138.69, eps);
		assertEquals(sc.energy(2, 2), 224.49, eps);
		assertEquals(sc.energy(3, 2), 145.15, eps);
		assertEquals(sc.energy(4, 2), 1000.00, eps);

		assertEquals(sc.energy(0, 3), 1000.00, eps);
		assertEquals(sc.energy(1, 3), 185.83, eps);
		assertEquals(sc.energy(2, 3), 103.24, eps);
		assertEquals(sc.energy(3, 3), 194.50, eps);
		assertEquals(sc.energy(4, 3), 1000.00, eps);
		
		assertEquals(sc.energy(0, 4), 1000.00, eps);
		assertEquals(sc.energy(1, 4), 1000.00, eps);
		assertEquals(sc.energy(2, 4), 1000.00, eps);
		assertEquals(sc.energy(3, 4), 1000.00, eps);
		assertEquals(sc.energy(4, 4), 1000.00, eps);
		
		// Validate Colors of new picture.
		assertEquals(p.get(0, 0).getRGB(), picture.get(0, 0).getRGB());
		assertEquals(p.get(1, 0).getRGB(), picture.get(1, 0).getRGB());
		assertEquals(p.get(2, 0).getRGB(), picture.get(2, 0).getRGB());
		assertEquals(p.get(3, 0).getRGB(), picture.get(4, 0).getRGB());
		assertEquals(p.get(4, 0).getRGB(), picture.get(5, 0).getRGB());
		
		assertEquals(p.get(0, 1).getRGB(), picture.get(0, 1).getRGB());
		assertEquals(p.get(1, 1).getRGB(), picture.get(1, 1).getRGB());
		assertEquals(p.get(2, 1).getRGB(), picture.get(2, 1).getRGB());
		assertEquals(p.get(3, 1).getRGB(), picture.get(3, 1).getRGB());
		assertEquals(p.get(4, 1).getRGB(), picture.get(5, 1).getRGB());

		assertEquals(p.get(0, 2).getRGB(), picture.get(0, 2).getRGB());
		assertEquals(p.get(1, 2).getRGB(), picture.get(1, 2).getRGB());
		assertEquals(p.get(2, 2).getRGB(), picture.get(2, 2).getRGB());
		assertEquals(p.get(3, 2).getRGB(), picture.get(4, 2).getRGB());
		assertEquals(p.get(4, 2).getRGB(), picture.get(5, 2).getRGB());

		assertEquals(p.get(0, 3).getRGB(), picture.get(0, 3).getRGB());
		assertEquals(p.get(1, 3).getRGB(), picture.get(1, 3).getRGB());
		assertEquals(p.get(2, 3).getRGB(), picture.get(3, 3).getRGB());
		assertEquals(p.get(3, 3).getRGB(), picture.get(4, 3).getRGB());
		assertEquals(p.get(4, 3).getRGB(), picture.get(5, 3).getRGB());

		assertEquals(p.get(0, 4).getRGB(), picture.get(0, 4).getRGB());
		assertEquals(p.get(1, 4).getRGB(), picture.get(2, 4).getRGB());
		assertEquals(p.get(2, 4).getRGB(), picture.get(3, 4).getRGB());
		assertEquals(p.get(3, 4).getRGB(), picture.get(4, 4).getRGB());
		assertEquals(p.get(4, 4).getRGB(), picture.get(5, 4).getRGB());
	}
	
	@Test
	void findHorizontalSeamTest6x5() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/6x5.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);
		assertEquals(hSeam[3], 2);
		assertEquals(hSeam[4], 1);
		assertEquals(hSeam[5], 0);
	}

	@Test
	void removeHorizontalSeamTest6x5() {

		final double eps = 1e-2;
		
		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/6x5.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		sc.removeHorizontalSeam(hSeam);
		final Picture p = sc.picture();
		
		// Validate Dimensions
		assertEquals(sc.width(), picture.width());
		assertEquals(sc.height(), picture.height()-1);
		assertEquals(p.width(), picture.width());
		assertEquals(p.height(), picture.height()-1);
		
		// Validate Energies
		assertEquals(sc.energy(0, 0), 1000.00, eps);
		assertEquals(sc.energy(1, 0), 1000.00, eps);
		assertEquals(sc.energy(2, 0), 1000.00, eps);
		assertEquals(sc.energy(3, 0), 1000.00, eps);
		assertEquals(sc.energy(4, 0), 1000.00, eps);		
		assertEquals(sc.energy(5, 0), 1000.00, eps);		

		assertEquals(sc.energy(0, 1), 1000.00, eps);
		assertEquals(sc.energy(1, 1), 146.57, eps);
		assertEquals(sc.energy(2, 1), 125.24, eps);
		assertEquals(sc.energy(3, 1), 167.82, eps);
		assertEquals(sc.energy(4, 1), 135.50, eps);
		assertEquals(sc.energy(5, 1), 1000.00, eps);		
		
		assertEquals(sc.energy(0, 2), 1000.00, eps);
		assertEquals(sc.energy(1, 2), 253.42, eps);
		assertEquals(sc.energy(2, 2), 174.01, eps);
		assertEquals(sc.energy(3, 2), 227.49, eps);
		assertEquals(sc.energy(4, 2), 194.50, eps);
		assertEquals(sc.energy(5, 2), 1000.00, eps);
		
		assertEquals(sc.energy(0, 3), 1000.00, eps);
		assertEquals(sc.energy(1, 3), 1000.00, eps);
		assertEquals(sc.energy(2, 3), 1000.00, eps);
		assertEquals(sc.energy(3, 3), 1000.00, eps);
		assertEquals(sc.energy(4, 3), 1000.00, eps);
		assertEquals(sc.energy(5, 3), 1000.00, eps);
		
		// Validate Colors of new picture.
		assertEquals(p.get(0, 0).getRGB(), picture.get(0, 0).getRGB());
		assertEquals(p.get(0, 1).getRGB(), picture.get(0, 2).getRGB());
		assertEquals(p.get(0, 2).getRGB(), picture.get(0, 3).getRGB());
		assertEquals(p.get(0, 3).getRGB(), picture.get(0, 4).getRGB());

		assertEquals(p.get(1, 0).getRGB(), picture.get(1, 0).getRGB());
		assertEquals(p.get(1, 1).getRGB(), picture.get(1, 1).getRGB());
		assertEquals(p.get(1, 2).getRGB(), picture.get(1, 3).getRGB());
		assertEquals(p.get(1, 3).getRGB(), picture.get(1, 4).getRGB());

		assertEquals(p.get(2, 0).getRGB(), picture.get(2, 0).getRGB());
		assertEquals(p.get(2, 1).getRGB(), picture.get(2, 2).getRGB());
		assertEquals(p.get(2, 2).getRGB(), picture.get(2, 3).getRGB());
		assertEquals(p.get(2, 3).getRGB(), picture.get(2, 4).getRGB());

		assertEquals(p.get(3, 0).getRGB(), picture.get(3, 0).getRGB());
		assertEquals(p.get(3, 1).getRGB(), picture.get(3, 1).getRGB());
		assertEquals(p.get(3, 2).getRGB(), picture.get(3, 3).getRGB());
		assertEquals(p.get(3, 3).getRGB(), picture.get(3, 4).getRGB());

		assertEquals(p.get(4, 0).getRGB(), picture.get(4, 0).getRGB());
		assertEquals(p.get(4, 1).getRGB(), picture.get(4, 2).getRGB());
		assertEquals(p.get(4, 2).getRGB(), picture.get(4, 3).getRGB());
		assertEquals(p.get(4, 3).getRGB(), picture.get(4, 4).getRGB());

		assertEquals(p.get(5, 0).getRGB(), picture.get(5, 1).getRGB());
		assertEquals(p.get(5, 1).getRGB(), picture.get(5, 2).getRGB());
		assertEquals(p.get(5, 2).getRGB(), picture.get(5, 3).getRGB());
		assertEquals(p.get(5, 3).getRGB(), picture.get(5, 4).getRGB());
	}
}
