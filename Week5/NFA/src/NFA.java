public class NFA {

	private char[] re;
	private Digraph G;
	private int M;

	public NFA(String regexp) {
		re = regexp.toCharArray();
		G = this.buildEpsilontransitionDigraph();
		M = regexp.length();
	}
	
	public boolean recognizes(String txt) {

		Bag<Integer> pc = new Bag<Integer>();
		DirectedDFS dfs = new DirectedDFS(this.G, 0);
		for (int i = 0; i < G.V(); ++i) {
			if (dfs.marked(i)) {
				pc.add(i);
			}
		}
		
		for (int i = 0; i < txt.length(); ++i) {
			
			Bag<Integer> match = new Bag<Integer>();
			for (int v : pc) {
				if (v == M) {
					continue; // substring match, not interested in such cases only complete match.
				}
				
				if (this.re[v] == txt.charAt(i) || this.re[v] == '.') {
					match.add(v+1); // v+1 since match transitions always result in a single increment of the current state.
				}				
			}
			
			dfs = new DirectedDFS(this.G, match);
			pc = new Bag<Integer>();
			for (int v = 0; v < G.V(); ++v) {
				if (dfs.marked[v]) {
					pc.add(v);
				}
			}
		}
		
		for (int v : pc) {
			if (v == M) {
				return true;
			}
		}

		return false;
	}
	
	public Digraph buildEpsilontransitionDigraph() {
		
		final Digraph G = new Digraph(this.M+1); // +1 for accept state?
		final Stack<Integer> ops = new Stack<Integer>();
		
		for (int i = 0; i < M; ++i) {
			
			int lp = -1;
			int orOp = -1;
			int rp = -1;
			int asterix = -1;
			int character = -1;
			
			if (this.re[i] == '(' || this.re[i] == '|') {
				ops.push(i);
			} else if (this.re[i] == ')') {
				int op = ops.pop();
				if (op == '|') {
					orOp = op;
					lp = ops.pop();
				}
			} else if (this.re[i] == '*') {
				G.addEdge(i, i+1);
			} else {
				// character found
				character = i;
			}
			
			// Look ahead for asterix
			if (i < M-1 && this.re[i+1] == '*') {
				asterix = i+1;
			}
			
			// Add transitions
			if (character != -1) {				
				if (asterix != -1) {
					G.addEdge(i, i+1);
					G.addEdge(i+1, i);
				}
			} else if (rp != -1) {
				
				G.addEdge(lp, lp+1);
				G.addEdge(rp, rp+1);
				
				if (orOp != -1) {
					G.addEdge(lp, orOp+1);
					G.addEdge(orOp+1, rp);
				}
				
				if (asterix != -1) {
					G.addEdge(lp, asterix);
					G.addEdge(asterix, lp);
				}
			}
		}
		
		return G;
	}
}
