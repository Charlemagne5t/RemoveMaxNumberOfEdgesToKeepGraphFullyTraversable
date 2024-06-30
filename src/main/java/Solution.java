import java.util.Arrays;

public class Solution {
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        UnionFind ufa = new UnionFind(n);
        UnionFind ufb = new UnionFind(n);
        int res = 0;
        for(int[] e : edges) {
            boolean a = false;
            boolean b = false;
            if(e[0] == 3) {
                if(ufa.isConnected(e[1] - 1, e[2] - 1)){
                    a = true;
                }else {
                    ufa.unify(e[1] - 1, e[2] - 1);
                }
                if(ufb.isConnected(e[1] - 1, e[2] - 1)){
                    b = true;
                }else {
                    ufb.unify(e[1] - 1, e[2] - 1);
                }
            }
            if(a && b) {
                res++;
            }
        }
        for(int[] e : edges) {
            if(e[0] == 1) {
                if(ufa.isConnected(e[1] - 1, e[2] - 1)){
                    res++;
                }else {
                    ufa.unify(e[1] - 1, e[2] - 1);
                }

            }else if(e[0] == 2){
                if(ufb.isConnected(e[1] - 1, e[2] - 1)){
                    res++;
                }else {
                    ufb.unify(e[1] - 1, e[2] - 1);
                }
            }

        }
        for(int i = 1; i < n; i++){
            if(!ufa.isConnected(0, i) || !ufb.isConnected(1, i)){
                return -1;
            }
        }
        return res;
    }
}
class UnionFind {
    int edges;
    int[] parent;
    int[] sizes;
    int numberOfElements;

    public UnionFind(int edges) {
        this.edges = edges;
        parent = new int[edges];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        sizes = new int[edges];
        Arrays.fill(sizes, 1);
        numberOfElements = edges;

    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isConnected(int node1, int node2) {
        return find(node1) == find(node2);
    }

    public int find(int p) {

        int root = p;
        while (root != parent[root]) root = parent[root];

        while (p != root) {
            int next = parent[p];
            parent[p] = root;
            p = next;
        }

        return root;
    }

    public boolean unify(int node1, int node2) {
        if (isConnected(node1, node2)) {
            return false;
        }

        int parent1 = find(node1);
        int parent2 = find(node2);

        if (sizes[parent1] < sizes[parent2]) {
            sizes[parent2] += sizes[parent2];
            parent[parent1] = parent2;
            sizes[parent1] = 0;
        } else {
            sizes[parent1] += sizes[parent2];
            parent[parent2] = parent1;
            sizes[parent2] = 0;
        }
        numberOfElements--;
        return true;
    }

    public void reset(int p) {
        parent[p] = p;
        sizes[p] = 0;
    }
}
