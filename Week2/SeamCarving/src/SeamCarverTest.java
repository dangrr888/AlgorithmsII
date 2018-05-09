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
	void findVerticalSeamTest3x4() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/3x4.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 0);
		assertEquals(vSeam[1], 1);
		assertEquals(vSeam[2], 1);
		assertEquals(vSeam[3], 0);
	}

	@Test
	void findHorizontalSeamTest3x4() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/3x4.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);
	}
	
	@Test
	void findVerticalSeamTest4x6() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/4x6.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 1);
		assertEquals(vSeam[1], 2);
		assertEquals(vSeam[2], 1);
		assertEquals(vSeam[3], 1);
		assertEquals(vSeam[4], 2);
		assertEquals(vSeam[5], 1);
	}

	@Test
	void findHorizontalSeamTest4x6() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/4x6.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);
		assertEquals(hSeam[3], 0);
	}

	@Test
	void findVerticalSeamTest5x6() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/5x6.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 1);
		assertEquals(vSeam[1], 2);
		assertEquals(vSeam[2], 2);
		assertEquals(vSeam[3], 3);
		assertEquals(vSeam[4], 2);
		assertEquals(vSeam[5], 1);
	}

	@Test
	void findHorizontalSeamTest5x6() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/5x6.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 2);
		assertEquals(hSeam[1], 3);
		assertEquals(hSeam[2], 2);
		assertEquals(hSeam[3], 3);
		assertEquals(hSeam[4], 2);
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

	@Test
	void findVerticalSeamTest3x7() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/3x7.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 0);
		assertEquals(vSeam[1], 1);
		assertEquals(vSeam[2], 1);
		assertEquals(vSeam[3], 1);
		assertEquals(vSeam[4], 1);
		assertEquals(vSeam[5], 1);
		assertEquals(vSeam[6], 0);
	}

	@Test
	void findHorizontalSeamTest3x7() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/3x7.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 1);
		assertEquals(hSeam[1], 2);
		assertEquals(hSeam[2], 1);
	}

	@Test
	void findHorizontalSeamTest7x10() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/7x10.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 6);
		assertEquals(hSeam[1], 7);
		assertEquals(hSeam[2], 7);
		assertEquals(hSeam[3], 7);
		assertEquals(hSeam[4], 8);
		assertEquals(hSeam[5], 8);
		assertEquals(hSeam[6], 7);
	}

	@Test
	void findVerticalSeamTest7x10() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/7x10.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 2);
		assertEquals(vSeam[1], 3);
		assertEquals(vSeam[2], 4);
		assertEquals(vSeam[3], 3);
		assertEquals(vSeam[4], 4);
		assertEquals(vSeam[5], 3);
		assertEquals(vSeam[6], 3);
		assertEquals(vSeam[7], 2);
		assertEquals(vSeam[8], 2);
		assertEquals(vSeam[9], 1);
	}
	
	@Test
	void findVerticalSeamTest7x3() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/7x3.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 2);
		assertEquals(vSeam[1], 3);
		assertEquals(vSeam[2], 2);
	}
	
	@Test
	void findHorizontalSeamTest7x3() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/7x3.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 0);
		assertEquals(hSeam[1], 1);
		assertEquals(hSeam[2], 1);
		assertEquals(hSeam[3], 1);
		assertEquals(hSeam[4], 1);
		assertEquals(hSeam[5], 1);
		assertEquals(hSeam[6], 0);		
	}

	@Test
	void energiesTest10x10() {
		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/10x10.png");
		final SeamCarver sc = new SeamCarver(picture);
		final double eps = 1e-2;
		
		assertEquals(sc.energy(0, 0), 1000.00, eps);
		assertEquals(sc.energy(1, 0), 1000.00, eps);
		assertEquals(sc.energy(2, 0), 1000.00, eps);
		assertEquals(sc.energy(3, 0), 1000.00, eps);
		assertEquals(sc.energy(4, 0), 1000.00, eps);
		assertEquals(sc.energy(5, 0), 1000.00, eps);
		assertEquals(sc.energy(6, 0), 1000.00, eps);
		assertEquals(sc.energy(7, 0), 1000.00, eps);
		assertEquals(sc.energy(8, 0), 1000.00, eps);
		assertEquals(sc.energy(9, 0), 1000.00, eps);
		
		assertEquals(sc.energy(0, 1), 1000.00, eps);
		assertEquals(sc.energy(1, 1), 136.73, eps);
		assertEquals(sc.energy(2, 1), 272.84, eps);
		assertEquals(sc.energy(3, 1), 254.64, eps);
		assertEquals(sc.energy(4, 1), 280.20, eps);
		assertEquals(sc.energy(5, 1), 178.56, eps);
		assertEquals(sc.energy(6, 1), 236.84, eps);
		assertEquals(sc.energy(7, 1), 128.93, eps);
		assertEquals(sc.energy(8, 1), 172.62, eps);
		assertEquals(sc.energy(9, 1), 1000.00, eps);

		assertEquals(sc.energy(0, 2), 1000.00, eps);
		assertEquals(sc.energy(1, 2), 222.06, eps);
		assertEquals(sc.energy(2, 2), 156.22, eps);
		assertEquals(sc.energy(3, 2), 186.79, eps);
		assertEquals(sc.energy(4, 2), 208.01, eps);
		assertEquals(sc.energy(5, 2), 171.06, eps);
		assertEquals(sc.energy(6, 2), 295.56, eps);
		assertEquals(sc.energy(7, 2), 125.46, eps);
		assertEquals(sc.energy(8, 2), 259.00, eps);
		assertEquals(sc.energy(9, 2), 1000.00, eps);

		assertEquals(sc.energy(0, 3), 1000.00, eps);
		assertEquals(sc.energy(1, 3), 228.47, eps);
		assertEquals(sc.energy(2, 3), 252.25, eps);
		assertEquals(sc.energy(3, 3), 168.24, eps);
		assertEquals(sc.energy(4, 3), 164.97, eps);
		assertEquals(sc.energy(5, 3), 127.18, eps);
		assertEquals(sc.energy(6, 3), 209.46, eps);
		assertEquals(sc.energy(7, 3), 202.49, eps);
		assertEquals(sc.energy(8, 3), 229.03, eps);
		assertEquals(sc.energy(9, 3), 1000.00, eps);

		assertEquals(sc.energy(0, 4), 1000.00, eps);
		assertEquals(sc.energy(1, 4), 293.85, eps);
		assertEquals(sc.energy(2, 4), 274.51, eps);
		assertEquals(sc.energy(3, 4), 217.81, eps);
		assertEquals(sc.energy(4, 4), 165.63, eps);
		assertEquals(sc.energy(5, 4), 175.67, eps);
		assertEquals(sc.energy(6, 4), 223.37, eps);
		assertEquals(sc.energy(7, 4), 194.78, eps);
		assertEquals(sc.energy(8, 4), 243.35, eps);
		assertEquals(sc.energy(9, 4), 1000.00, eps);
		
		assertEquals(sc.energy(0, 5), 1000.00, eps);
		assertEquals(sc.energy(1, 5), 208.68, eps);
		assertEquals(sc.energy(2, 5), 294.31, eps);
		assertEquals(sc.energy(3, 5), 243.53, eps);
		assertEquals(sc.energy(4, 5), 161.69, eps);
		assertEquals(sc.energy(5, 5), 253.29, eps);
		assertEquals(sc.energy(6, 5), 236.73, eps);
		assertEquals(sc.energy(7, 5), 217.57, eps);
		assertEquals(sc.energy(8, 5), 221.35, eps);
		assertEquals(sc.energy(9, 5), 1000.00, eps);
		
		assertEquals(sc.energy(0, 6), 1000.00, eps);
		assertEquals(sc.energy(1, 6), 318.51, eps);
		assertEquals(sc.energy(2, 6), 222.38, eps);
		assertEquals(sc.energy(3, 6), 240.88, eps);
		assertEquals(sc.energy(4, 6), 239.79, eps);
		assertEquals(sc.energy(5, 6), 220.45, eps);
		assertEquals(sc.energy(6, 6), 259.36, eps);
		assertEquals(sc.energy(7, 6), 269.76, eps);
		assertEquals(sc.energy(8, 6), 264.14, eps);
		assertEquals(sc.energy(9, 6), 1000.00, eps);

		assertEquals(sc.energy(0, 7), 1000.00, eps);
		assertEquals(sc.energy(1, 7), 225.20, eps);
		assertEquals(sc.energy(2, 7), 270.73, eps);
		assertEquals(sc.energy(3, 7), 187.06, eps);
		assertEquals(sc.energy(4, 7), 197.58, eps);
		assertEquals(sc.energy(5, 7), 165.59, eps);
		assertEquals(sc.energy(6, 7), 255.09, eps);
		assertEquals(sc.energy(7, 7), 276.89, eps);
		assertEquals(sc.energy(8, 7), 124.04, eps);
		assertEquals(sc.energy(9, 7), 1000.00, eps);

		assertEquals(sc.energy(0, 8), 1000.00, eps);
		assertEquals(sc.energy(1, 8), 222.50, eps);
		assertEquals(sc.energy(2, 8), 204.43, eps);
		assertEquals(sc.energy(3, 8), 252.65, eps);
		assertEquals(sc.energy(4, 8), 270.87, eps);
		assertEquals(sc.energy(5, 8), 199.05, eps);
		assertEquals(sc.energy(6, 8), 324.13, eps);
		assertEquals(sc.energy(7, 8),  90.64, eps);
		assertEquals(sc.energy(8, 8), 245.94, eps);
		assertEquals(sc.energy(9, 8), 1000.00, eps);
		
		assertEquals(sc.energy(0, 9), 1000.00, eps);
		assertEquals(sc.energy(1, 9), 1000.00, eps);
		assertEquals(sc.energy(2, 9), 1000.00, eps);
		assertEquals(sc.energy(3, 9), 1000.00, eps);
		assertEquals(sc.energy(4, 9), 1000.00, eps);
		assertEquals(sc.energy(5, 9), 1000.00, eps);
		assertEquals(sc.energy(6, 9), 1000.00, eps);
		assertEquals(sc.energy(7, 9), 1000.00, eps);
		assertEquals(sc.energy(8, 9), 1000.00, eps);
		assertEquals(sc.energy(9, 9), 1000.00, eps);
	}

	
	@Test
	void findVerticalSeamTest10x10() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/10x10.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] vSeam = sc.findVerticalSeam();
		assertEquals(vSeam.length, picture.height());
		assertEquals(vSeam[0], 0);
		assertEquals(vSeam[1], 1);
		assertEquals(vSeam[2], 2);
		assertEquals(vSeam[3], 3);
		assertEquals(vSeam[4], 4);
		assertEquals(vSeam[5], 4);
		assertEquals(vSeam[6], 5);
		assertEquals(vSeam[7], 5);
		assertEquals(vSeam[8], 5);
		assertEquals(vSeam[9], 4);
		double totalEnergySq = 0.0;
		for (int row = 0; row < vSeam.length; ++row) {
			totalEnergySq += Math.pow(sc.energy(vSeam[row], row), 2.0);
		}
		System.out.println("Total Energy: " + totalEnergySq);
		
		
		final double expeectedTotalEnergySq  =  Math.pow(sc.energy(6, 0), 2.0) +
												Math.pow(sc.energy(7, 1), 2.0) +
												Math.pow(sc.energy(7, 2), 2.0) +
												Math.pow(sc.energy(7, 3), 2.0) +
												Math.pow(sc.energy(7, 4), 2.0) +
												Math.pow(sc.energy(7, 5), 2.0) +
												Math.pow(sc.energy(8, 6), 2.0) +
												Math.pow(sc.energy(8, 7), 2.0) +
												Math.pow(sc.energy(7, 8), 2.0) +
												Math.pow(sc.energy(6, 9), 2.0);
		System.out.println("Expected Total Energy: " + expeectedTotalEnergySq);		
	}

	@Test
	void findHorizontalSeamTest10x10() {

		final Picture picture = new Picture("/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week2/SeamCarving/test/seam/10x10.png");
		final SeamCarver sc = new SeamCarver(picture);
		
		final int[] hSeam = sc.findHorizontalSeam();
		assertEquals(hSeam.length, picture.width());
		assertEquals(hSeam[0], 0);
		assertEquals(hSeam[1], 1);
		assertEquals(hSeam[2], 2);
		assertEquals(hSeam[3], 3);
		assertEquals(hSeam[4], 3);
		assertEquals(hSeam[5], 3);
		assertEquals(hSeam[6], 3);		
		assertEquals(hSeam[7], 2);
		assertEquals(hSeam[8], 1);
		assertEquals(hSeam[9], 0);
	}

}
