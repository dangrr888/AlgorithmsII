import java.awt.Color;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

/*
 * A class to perform seam carving on a specified Picture.
 */
public class SeamCarver {
	
	private final Picture picture;
	private int width;
	private int height;
	private int pixels[][];
	private double energy[][];
	private double energyTo[][];
	private int rowTo[][];
	private int colTo[][];
	private static final double BORDERENERGY = 1000.0;
	
	/*
	 * @brief 1-parameter constructor.
	 * @param picture The specified picture used to
	 *   construct this SeamCarver object.
	 */
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new IllegalArgumentException();
		}
		
		// Initialize data members
		this.picture = picture;
		this.width = this.picture.width();
		this.height = this.picture.height();
		this.pixels = new int[this.height][];
		this.energy = new double[this.height][];
		this.energyTo = new double[this.height][];
		this.rowTo = new int[this.height][];
		this.colTo = new int[this.height][];
		for (int row = 0; row < this.height; ++row) {
			this.pixels[row] = new int[this.width];
			this.energy[row] = new double[this.width];
			this.energyTo[row] = new double[this.width];
			this.rowTo[row] = new int[this.width];
			this.colTo[row] = new int[this.width];
			for (int col = 0; col < this.width; ++col) {
				this.pixels[row][col] = this.picture.getRGB(col, row);
				this.energyTo[row][col] = 0.0;
				this.rowTo[row][col] = row;
				this.colTo[row][col] = col;
			}
		}
		
		// Calculate energy
		for (int row = 0; row < this.height; ++row) {
			for (int col = 0; col < this.width; ++col) {
				if (this.isBorderPixel(row, col)) {
					this.energy[row][col] = SeamCarver.BORDERENERGY;
				} else {
					this.energy[row][col] = this.calculateEnergy(row, col);
				}
			}
		}
	}
	
	private double calculateEnergy(int row, int col) {
		throw new UnsupportedOperationException();
	}
	
	private int vertexFromIndices(int row, int col) {
		throw new UnsupportedOperationException();
	}
	
	private Integer leftAdj(int v) {
		throw new UnsupportedOperationException();
	}

	private Integer rightAdj(int v) {
		throw new UnsupportedOperationException();
	}

	private Integer upAdj(int v) {
		throw new UnsupportedOperationException();
	}

	private Integer downAdj(int v) {
		throw new UnsupportedOperationException();
	}
	
	private Integer downLeftAdj(int v) {
		throw new UnsupportedOperationException();
	}
	
	private Integer downRightAdj(int v) {
		throw new UnsupportedOperationException();
	}

	private boolean validColumnIndex(int col) {
		return col >= 0 && col < this.width();
	}
	
	private boolean validRowIndex(int row) {
		return row >= 0 && row < this.height();
	}
	
	private boolean validIndices(int row, int col) {
		return this.validColumnIndex(col) && this.validRowIndex(row);
	}
	
	/*
	 * @brief Return the current Picture associated with
	 *   this SeamCraver object.
	 * @return the Picture associated with
	 *   this SeamCraver object.
	 */
	public Picture picture() {
		return this.picture;
	}
	
	/*
	 * @brief Retrieve the width of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 * @return the width (in pixels) of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 */
	public int width() {
		return this.picture.width();
	}
	
	/*
	 * @brief Retrieve the height of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 * @return the height (in pixels) of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 */
	public int height() {
		return this.picture.height();
	}
	
	private double deltasq(Color c1, Color c2) {
		throw new UnsupportedOperationException();
	}	
	
	private boolean isBorderPixel(int row, int col) {
		return row == 0 || row == this.height-1 ||
			   col == 0 || col == this.width- 1;
	}
	
	/*
	 * @brief Return the energy associated with the
	 *   pixel of the current picture, associated with
	 *   this SeamCarver object, at the specified
	 *   location.
	 * @param x The column index of the desired
	 *   pixel (0 is the left-most column).
	 * @param y The row index of the desired
	 *   pixel (0 is the top-most row).
	 * @return the energy associated with the
	 *   pixel of the current picture, associated with
	 *   this SeamCarver object, at the specified
	 *   location.
	 */
	public double energy(int col, int row) {
		if (!this.validIndices(row, col)) {
			throw new IllegalArgumentException();
		}
		
		return this.energy[row][col];
	}
	
	/*
	 * @brief Return the sequence of row indices
	 *   for the next horizontal seam of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 * @return the sequence of row indices
	 *   for the next horizontal seam of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 */
	public int[] findHorizontalSeam() {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * @brief Return the sequence of column indices
	 *   for the next vertical seam of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 * @return the sequence of column indices
	 *   for the next vertical seam of the current
	 *   picture associated with this SeamCarver
	 *   object.
	 */
	public int[] findVerticalSeam() {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * @brief Remove the next horizontal seam from
	 *   the current picture associated with this
	 *   SeamCarver object.
	 * @param seam The horizontal seam to be removed.
	 */
	public void removeHorizontalSeam(int[] seam) {
		if (this.picture.height() < 2) {
			throw new IllegalArgumentException();
		}

		if (seam == null) {
			throw new IllegalArgumentException();
		}

		final int length = seam.length;
		if (length != this.picture.width()) {
			throw new IllegalArgumentException();
		}
		
		int lastRowIdx = seam[0];
		for (int i = 0; i < length; ++i) {
			final int rowIdx = seam[i];
			if (!this.validRowIndex(rowIdx)) {
				throw new IllegalArgumentException();
			}
			
			if (Math.abs(lastRowIdx - rowIdx) > 1) {				
				throw new IllegalArgumentException();
			} else {
				lastRowIdx = rowIdx;
			}
		}
		
		throw new UnsupportedOperationException();
	}
	
	/*
	 * @brief Remove the next vertical seam from
	 *   the current picture associated with this
	 *   SeamCarver object.
	 * @param seam The vertical seam to be removed.
	 */
	public void removeVerticalSeam(int[] seam) {
		if (this.picture.width() < 2) {
			throw new IllegalArgumentException();
		}
		
		if (seam == null) {
			throw new IllegalArgumentException();
		}

		final int length = seam.length;
		if (length != this.picture.height()) {
			throw new IllegalArgumentException();
		}

		int lastColumnIdx = seam[0];
		for (int i = 0; i < length; ++i) {
			final int columnIdx = seam[i];
			if (!this.validColumnIndex(seam[i])) {
				throw new IllegalArgumentException();
			}
			
			if (Math.abs(lastColumnIdx - columnIdx) > 1) {				
				throw new IllegalArgumentException();
			} else {
				lastColumnIdx = columnIdx;
			}			
		}

		throw new UnsupportedOperationException();
	}
}

















