import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLengthEncoder {
	private static int R = 256;
	private static int lgR = 8;
	
	public static void encode() {

		boolean bit = false; // convention that first count is associated with zero.
		while(!BinaryStdIn.isEmpty()) {
			
			byte count = 0;
			while (BinaryStdIn.readBoolean() == bit) {
				++count;
			}
			
			BinaryStdOut.write(count);
			bit = !bit;
		}
		
		BinaryStdOut.close();
	}
	
	public static void decode() {
		
		boolean bit = false;
		while (!BinaryStdIn.isEmpty()) {
			int run = BinaryStdIn.readInt(lgR);
			for (int i = 0; i < run; ++i) {
				BinaryStdOut.write(bit);
			}
			bit = !bit;
		}
		
		BinaryStdOut.close();
	}
	
	
	public static void main(String[] args) {
		
		RunLengthEncoder.encode();
	}
}
