import java.util.Stack;

public class ShortestDirectedCycle {

	private Digraph G;
	private Stack<Stack<Integer>> cycles;
	private int marked[];
	private int edgeTo[];
	private int currSource;
	
	private static final int UNMARKED = -1;
	
	public ShortestDirectedCycle(Digraph G_) {
		this.G = G_;
		this.marked = new int[this.G.V()];
		for (int v = 0; v < this.G.V(); ++v) {
			this.marked[v] = UNMARKED;
		}
		this.edgeTo = new int[this.G.V()];
		for (int v = 0; v < this.G.V(); ++v) {
			this.edgeTo[v] = v;
		}
	}
	
	private void checkIndex(int v) {
		this.G.checkIndex(v);
	}
	
	private void dfs(int v) {
		this.checkIndex(v);
		this.marked[v] = this.currSource; /* Mark elements on this path with current source */
		for (int w : this.G.adj(v)) {
			if (this.marked[w] != this.currSource) { /* remark elements on current path with current source. */
				this.dfs(w);
				this.edgeTo[w] = v;
			}
		}
	}
	
	private void findCycles() {
		
		
		
	}
	
	
}
