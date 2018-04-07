import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch {

	private int s;
	private Graph G;
	private LinkedList<Integer> q;
	private boolean[] marked;
	private int[] edgeTo;
	private int[] depth;
	
	public BreadthFirstSearch(Graph G_, int s_) {
		this.G = G_;
		
		this.s = s_;
		if (this.s < 0 || this.s >= this.G.V()) {
			throw new IllegalArgumentException();
		}
	
		this.q = new LinkedList<Integer>();

		this.marked = new boolean[this.G.V()];
		this.depth = new int[this.G.V()];
		this.edgeTo = new int[this.G.V()];
		for (int v = 0; v < this.G.V(); ++v) {
			this.marked[v] = false;
			this.depth[v] = 0;
			this.edgeTo[v] = v;
		}
			
		this.q.add(s);
		this.marked[s] = true;
		
		while (!this.q.isEmpty()) {
			final int v = this.q.pop();
			for (int w : this.G.adj(v)) {
				if (!this.marked[w]) {
					this.q.add(w);
					this.marked[w] = true;
					this.depth[w] = this.depth[v]+1;
					this.edgeTo[w] = v;
				}
			}
		}
	}
	
	public int getVertexWithLargestdepth() {
		int ret = 0;
		for (int v = 1; v < this.G.V(); ++v) {
			if (this.marked[v]) {
				ret = depth[v] > depth[ret] ? v : ret;
			}
		}
		return ret;
	}
	
	public int getDepth(int v) {
		if (v < 0 || v >= this.G.V()) {
			throw new IllegalArgumentException();
		}
		
		return this.depth[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		if (v < 0 || v >= this.G.V()) {
			throw new IllegalArgumentException();
		}
		
		Stack<Integer> st = new Stack<Integer>();
		while (v != this.s) {
			st.push(v);
			v = this.edgeTo[v];
		}
		st.push(this.s);
		
		return st;
	}
}
