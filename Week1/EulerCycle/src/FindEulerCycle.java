import java.util.Stack;

public class FindEulerCycle {

	private Graph G;
	private boolean marked[];
	private int edgeTo[];
	private int s;
	boolean foundCycle;
	Stack<Integer> cycle;
	
	private void dfs(int v) {
		this.checkIndex(v);
		
		if (this.foundCycle) {
			return;
		}
		
		for (int w : this.G.adj(v)) {
			
			if (this.foundCycle) {
				return;
			}

			if (w == this.s && this.edgeTo[v] != this.s) {
				this.foundCycle = true;
				this.edgeTo[this.s] = v;
				return;
			}
						
			if (!this.marked[w]) {
				this.marked[w] = true;
				this.edgeTo[w] = v;
				this.dfs(w);
			}
		}
	}
	
	private void storeCycle() {
		int v = this.s;
		do {
			this.cycle.push(v);
			v = this.edgeTo[v];
		} while (v != this.s);		
	}
	
	private void checkIndex(int v) {
		if (v < 0 || v >= this.G.V()) {
			throw new IllegalArgumentException();
		}
	}
	
	private void resetForNextCycle() {
		this.foundCycle = false;
		for (int v = 0; v < this.G.V(); ++v) {
			this.marked[v] = false;
			final int w = this.edgeTo[v];
			this.G.removeEdge(v, w);
			this.edgeTo[v] = v;
		}
	}
	
	public FindEulerCycle(Graph G_, int s_) {
		this.foundCycle = false;
		this.G = new Graph(G_);
		this.marked = new boolean[this.G.V()];
		this.edgeTo = new int[this.G.V()];
		for (int v = 0; v < this.G.V(); ++v) {
			this.marked[v] = false;
			this.edgeTo[v] = v;
		}
		this.s = s_;
		this.cycle = new Stack<Integer>();
		
		while(this.G.degree(this.s) > 0) {
			this.marked[this.s] = true;
			this.dfs(this.s);
			this.storeCycle();
			this.resetForNextCycle();
		}
	}
	
	public boolean foundEulerCycle() {
		return this.G.E() == 0 && !this.cycle.empty();
	}
	
	public Iterable<Integer> getEulerCycle() {

		if (!this.foundEulerCycle()) {
			throw new IllegalStateException();
		}
		
		return this.cycle;
	}
	
	public boolean eulerCyclePossible() {
		for (int v = 0; v < this.G.V(); ++v) {
			if (this.G.degree(v) % 2 != 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Graph G = new Graph(6);
		G.addEdge(0, 1);
		G.addEdge(1, 2);
		G.addEdge(2, 3);
		G.addEdge(3, 4);
		G.addEdge(4, 5);
		G.addEdge(5, 0);
		
		for (int s = 0; s < G.V(); ++s) {
			System.out.print("source: " + s + ", ");
			FindEulerCycle f = new FindEulerCycle(G, s);
			
			if (f.eulerCyclePossible() && f.foundEulerCycle()) {
				System.out.print("Euler cycle is: ");
				for (int v : f.getEulerCycle()) {
					System.out.print(v + "-");
				}
				System.out.println(f.s);
			} else {
				System.out.println("Graph has no Euler cycle.");
			}
		}
	}
}
