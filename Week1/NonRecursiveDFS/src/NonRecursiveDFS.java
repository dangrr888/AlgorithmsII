import java.util.Stack;

public class NonRecursiveDFS {

	private int s;
	private boolean marked[];
	private int edgeTo[];
	private Graph G;
	
	public NonRecursiveDFS(Graph G_, int s_) {
		this.G = G_;
		this.s = s_;
		if (this.s < 0 || this.s >= this.G.V()) {
			throw new IllegalArgumentException();
		}
		
		marked = new boolean[this.G.V()];
		edgeTo = new int[this.G.V()];
		for (int i = 0; i < this.G.V(); ++i) {
			marked[i] = false;
			edgeTo[i] = i;
		}
		
		Stack<Integer> st = new Stack<Integer>();
		st.push(this.s);
		this.marked[s] = true;
		while(!st.isEmpty()) {
			final int v = st.pop();
			for (int w : this.G.adj(v)) {
				if (!this.marked[w]) {
					st.push(w);
					this.marked[w] = true;
					this.edgeTo[w] = v;
				}
			}
		}
	}
	
	public boolean hasPathTo(int v) {
		return this.marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		if (!this.hasPathTo(v)) {
			return null;
		}
		Stack<Integer> st = new Stack<Integer>();
		if (v != s) {
			st.push(v);
			while(this.edgeTo[v] != this.s) {
				v = this.edgeTo[v];
				st.push(v);
			}
		}
		st.push(s);
		return st;
	}
	
	public int source() {
		return this.s;
	}
	
	public static void main(String[] args) {
		Graph G = new Graph(13);
		G.addEdge(0, 1);
		G.addEdge(0, 2);
		G.addEdge(0, 5);
		G.addEdge(0, 6);
		G.addEdge(6, 4);
		G.addEdge(4, 5);
		G.addEdge(5, 3);
		G.addEdge(3, 4);
		G.addEdge(7, 8);
		G.addEdge(9, 10);
		G.addEdge(9, 11);
		G.addEdge(9, 12);
		G.addEdge(11, 12);

		
		System.out.println(G);
		
		for (int s = 0; s < G.V(); ++s) {
			NonRecursiveDFS nrdfs = new NonRecursiveDFS(G, s);
			System.out.println("Source: " + nrdfs.source());
			for (int v = 0; v <  G.V(); ++v) {
				System.out.print(v + ": ");
				if (nrdfs.hasPathTo(v)) {
					System.out.print("END");
					for (int w : nrdfs.pathTo(v)) {
						System.out.print("<-" + w);
					}
				} else {
					System.out.print("No Path");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
