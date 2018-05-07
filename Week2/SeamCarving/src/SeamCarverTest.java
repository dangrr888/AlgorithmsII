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
}
