import edu.princeton.cs.algs4.Picture;

/*
 * A class to perform seam carving on a specified Picture.
 */
public class SeamCarver {
	
	// Current Picture
	private Picture picture;
	private boolean redraw = false;
	
	// Dimensions
	private int width;
	private int height;
	private int dim;
	
	// Arrays containing constant information
	private final int pixels[][];
	private final double energySq[][];
	
	// Constant information for terminal nodes
	private final int SOURCECOL;
	private final int TARGETROW;
	private final int TARGETCOL;
	
	// Cached arrays for SP finding.
	private double energySqTo[][];
	private int colTo[][];
	
	// Cached information on terminal Node for SP finding.
	private int rowToTarget;
	private int colToTarget;
	private double energySqToTarget = Double.POSITIVE_INFINITY;

	// Oreintation
	private enum Orientation {
		PORTRAIT,
		LANDSCAPE
	}
	private Orientation orientation = Orientation.PORTRAIT;
	
	// Static constants
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
		
		// Extent
		this.width = picture.width();
		this.height = picture.height();

		this.dim = Math.max(this.width, this.height);
		
		// Picture
		this.picture = new Picture(this.width, this.height);
		
		// RGB
		this.pixels = new int[this.dim][];
		
		// Energy
		this.energySq = new double[this.dim][];
		this.energySqTo = new double[this.dim][];
		
		// Paths
		this.colTo = new int[this.dim][];
		
		// Initialize multi-dimensional arrays (dim x dim so can transpose in-place)
		for (int row = 0; row < this.height; ++row) {
			
			// RGB
			this.pixels[row] = new int[this.dim];
			
			// Energy
			this.energySq[row] = new double[this.dim];
			this.energySqTo[row] = new double[this.dim];
		
			// Paths
			this.colTo[row] = new int[this.dim];
			
			// Initialize columns of multi-dimensional arrays
			for (int col = 0; col < this.width; ++col) {
			
				final int rgb = picture.getRGB(col, row);	

				// Picture (copy source)
				this.picture.setRGB(col, row, rgb);
				
				// RGB (copy picture)
				this.pixels[row][col] = rgb;
				
				// EnergyTo (all start as infinity)
				this.energySqTo[row][col] = Double.POSITIVE_INFINITY;
				
				// Path (Start with null paths)
				this.colTo[row][col] = col;
			}
		}
		
		// Calculate energy
		for (int row = 0; row < this.height; ++row) {
			for (int col = 0; col < this.width; ++col) {
				
				if (this.isBorderPixel(row, col)) {
					// Border pixels have fixed energy.
					this.energySq[row][col] = SeamCarver.BORDERENERGY;
				} else {
					// Non-border pixels
					this.energySq[row][col] = this.energySq(row, col);
				}
			}
		}
				
		// Terminal Nodes
		// row and col of the source and target virtual nodes.
		// They never change throughout carving.
		this.SOURCECOL = this.dim;
		this.TARGETROW = this.TARGETCOL = this.dim+1;
		this.rowToTarget = this.TARGETROW;
		this.colToTarget = this.TARGETCOL;
	}	
	
	private double deltaSqComponent(int rgb1, int rgb2) {
		final int mask = 0xFF;
		final int rdiff = (rgb1 >> 16 & mask) - (rgb2 >> 16 & mask);
		final int gdiff = (rgb1 >> 8 & mask) - (rgb2 >> 8 & mask);
		final int bdiff = (rgb1 & mask) - (rgb2 & mask);
		
		return rdiff*rdiff + gdiff*gdiff + bdiff*bdiff;		
	}
	
	private double deltaXSq(int row, int col) {
		final int lrgb = this.pixels[row][col-1];
		final int rrgb = this.pixels[row][col+1];
		return this.deltaSqComponent(lrgb, rrgb);
	}

	private double deltaYSq(int row, int col) {
		final int urgb = this.pixels[row-1][col];
		final int drgb = this.pixels[row+1][col];
		return this.deltaSqComponent(urgb, drgb);		
	}
	
	private double energySq(int row, int col) {
		return this.deltaXSq(row, col) + this.deltaYSq(row, col);
	}
	
	private Integer middleAdj(int row, int col) {
		if (row < this.height-1) {
			return col;
		}
		
		return null;
	}

	private Integer leftAdj(int row, int col) {
		if (col > 0 && row < this.height-1) {
			return col-1;
		}
		
		return null;
	}

	private Integer rightAdj(int row, int col) {
		if (col < this.width-1 && row < this.height-1) {
			return col+1;
		}
		
		return null;
	}

	private void relax(int row, int col) {		
		final double newEnergySqTo = this.energySqTo[row][col] + this.energySq(row, col);
		
		if (row == this.height-1) {
			// Pixel on bottom row
			if (this.energySqToTarget > newEnergySqTo) {
				this.energySqToTarget = newEnergySqTo;
				this.rowToTarget = row;
				this.colToTarget = col;
			}
		}
		else {
			final int adjRow = row + 1;
			final Integer[] adjCols = {this.leftAdj(row, col), this.middleAdj(row, col), this.rightAdj(row, col)};
	
			for (Integer adjCol : adjCols) {
				if (adjCol != null) {
					if (this.energySqTo[adjRow][adjCol] > newEnergySqTo) {
						this.energySqTo[adjRow][adjCol] = newEnergySqTo;
						this.colTo[adjRow][adjCol] = col;
					}
				}			
			}
		}
	}
	
	private void transpose(int[][] a) {
		for (int row = 0; row < this.dim; ++row) {
			for (int col = 0; col < this.dim; ++col) {
				final int tmp = a[row][col];
				a[row][col] = a[col][row];
				a[col][row] = tmp;
			}
		}
	}

	private void transpose(double[][] a) {
		for (int row = 0; row < this.dim; ++row) {
			for (int col = 0; col < this.dim; ++col) {
				final double tmp = a[row][col];
				a[row][col] = a[col][row];
				a[col][row] = tmp;
			}
		}
	}
	
	private void transpose() {
		this.transpose(this.pixels);
		this.transpose(this.energySq);
		this.transpose(energySqTo);
		this.transpose(colTo);
		
		final int tmp = this.width;
		this.width = this.height;
		this.height = tmp;
		
		this.orientation = (this.orientation == Orientation.PORTRAIT) 
						   ? Orientation.LANDSCAPE
						   : Orientation.PORTRAIT;
	}
	
	private boolean validColumnIndex(int col) {
		return col >= 0 && col < this.width;
	}
	
	private boolean validRowIndex(int row) {
		return row >= 0 && row < this.height;
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
		if (this.redraw) {
			if (this.orientation != Orientation.PORTRAIT) {
				this.transpose();
			}

			this.picture = new Picture(this.width, this.height);
			for (int row = 0; row < this.height; ++row) {
				for (int col = 0; col < this.width; ++col) {
					this.picture.setRGB(col, row, this.pixels[row][col]);
				}
			}
			this.redraw = false;
		}
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
		return this.orientation == Orientation.PORTRAIT ? this.width : this.height;
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
		return this.orientation == Orientation.PORTRAIT ? this.height : this.width;
	}
		
	private boolean isBorderPixel(int row, int col) {
		return row == 0 || row == this.height-1 ||
			   col == 0 || col == this.width-1;
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
		
		return this.orientation == Orientation.PORTRAIT
				? Math.sqrt(this.energySq[row][col])
				: Math.sqrt(this.energySq[col][row]);
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
		
		if (this.orientation == Orientation.PORTRAIT) {
			this.transpose();
		}
		
		int[] seamRows = this.findVerticalSeam();		
		this.transpose();
		return seamRows;
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
		
		if (this.orientation != Orientation.PORTRAIT) {
			this.transpose();
		}

		// Relax edges from the source.
		for (int col = 0; col < this.width; ++col) {
			this.energySqTo[0][col] = 0.0;
			this.colTo[0][col] = this.SOURCECOL;
		}
	
		// Relax all remaining edges in topological order.
		for (int row = 0; row < this.height; ++row) {
			for (int col = 0; col < this.width; ++col) {
				this.relax(row, col);
			}
		}
		
		final int[] seamCols = new int[this.height];
		int row = this.rowToTarget;
		int col = this.colToTarget;
		int idx = this.height-1; // bottom row
		do {
			seamCols[idx--] = col;
			final int newCol = this.colTo[row][col];
			col = newCol;
		} while (idx >= 0);
		
		return seamCols;
	}
	
	/*
	 * @brief Remove the next horizontal seam from
	 *   the current picture associated with this
	 *   SeamCarver object.
	 * @param seam The horizontal seam to be removed.
	 */
	public void removeHorizontalSeam(int[] seam) {
		
		if (this.orientation == Orientation.PORTRAIT) {
			this.transpose();
		}
		
		this.removeVerticalSeam(seam);
	}
	
	private void deleteCol(int[] a, int col) {
		
		final int len = a.length;
		if (len == 0 || col == len-1) {
			// No shift necessary.
			return;
		} else {
			// Shift 'a' one column to the left from col+1
			System.arraycopy(a, col+1, a, col, len-col);
		}
	}

	private void deleteCol(double[] a, int col) {
		
		final int len = a.length;
		if (len == 0 || col == len-1) {
			// No shift necessary.
			return;
		} else {
			// Shift 'a' one column to the left from col+1
			System.arraycopy(a, col+1, a, col, len-col);
		}
	}

	/*
	 * @brief Remove the next vertical seam from
	 *   the current picture associated with this
	 *   SeamCarver object.
	 * @param seam The vertical seam to be removed.
	 */
	public void removeVerticalSeam(int[] seam) {
		
		if (this.orientation != Orientation.PORTRAIT) {
			this.transpose();
		}

		if (this.width() < 2) {
			throw new IllegalArgumentException();
		}
		
		if (seam == null) {
			throw new IllegalArgumentException();
		}

		final int length = seam.length;
		if (length != this.height()) {
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

		// Shift Elements of arrays
		for (int row = 0; row < this.height; ++row) {
			this.deleteCol(pixels[row], seam[row]);
			this.deleteCol(energySq[row], seam[row]);
		}
		
		// Update dimensions
		--this.width;
		this.dim = Math.max(this.width, this.height);		
		
		// Recalculate energies in columns (row, seam[row]-1) and (row, seam[row] (which was previously seam[row]+1))
		for (int row = 0; row < this.height; ++row) {
			if (this.validIndices(row, seam[row]-1)) {
				if (this.isBorderPixel(row, seam[row]-1)) {
					this.energySq[row][seam[row]-1] = SeamCarver.BORDERENERGY;
				} else {
					this.energySq[row][seam[row]-1] = this.energySq(row, seam[row]-1);
				}
			}
			
			if (this.validIndices(row, seam[row])) {
				if (this.isBorderPixel(row, seam[row])) {
					this.energySq[row][seam[row]] = SeamCarver.BORDERENERGY;
				} else {
					this.energySq[row][seam[row]] = this.energySq(row, seam[row]);
				}
			}
		}

		// Reinitialize cached data
		for (int row = 0; row < this.height; ++row) {
			for (int col = 0; col < this.width; ++ col) {
				this.energySqTo[row][col] = Double.POSITIVE_INFINITY;
				this.colTo[row][col] = col;
			}
		}
		
		this.rowToTarget = this.TARGETROW;
		this.colToTarget = this.TARGETCOL;
		this.energySqToTarget = Double.POSITIVE_INFINITY;
		
		// Set flag to regenerate picture on next request.
		this.redraw = true;		
	}
}

















