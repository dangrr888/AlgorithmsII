import edu.princeton.cs.algs4.Picture;

/*
 * A class to perform seam carving on a specified Picture.
 */
public class SeamCarver {
	
	private final Picture picture;
	
	/*
	 * @brief 1-parameter constructor.
	 * @param picture The specified picture used to
	 *   construct this SeamCarver object.
	 */
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new IllegalArgumentException();
		}
		throw new UnsupportedOperationException();
	}
	
	private boolean validColumnIndex(int x) {
		return x >= 0 && x < this.width();
	}
	
	private boolean validRowIndex(int y) {
		return y >= 0 && y < this.height();
	}
	
	private boolean validCoordinates(int x, int y) {
		return this.validColumnIndex(x) && this.validRowIndex(y);
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
	public double energy(int x, int y) {
		if (!this.validCoordinates(x, y)) {
			throw new IllegalArgumentException();
		}
		
		throw new UnsupportedOperationException();
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

















