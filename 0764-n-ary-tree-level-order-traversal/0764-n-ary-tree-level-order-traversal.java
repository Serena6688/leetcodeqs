import java.util.*;

public class ShortestPathBinaryMatrix {

    public int shortestPathBinaryMatrix(int[][] grid) {

        int n = grid.length;

        //if start or end is blocked, no path exists
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];

        //(row, col, distance)
        queue.offer(new int[]{0, 0, 1});
        visited[0][0] = true;

        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };

        while (!queue.isEmpty()) {

            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], dist = curr[2];

            //if we've reached the target, return distance
            if (r == n - 1 && c == n - 1) {
                return dist;
            }

            //explore all 8 directions
            for (int[] dir : directions) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                //check bounds
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {

                    //check if cell is open and not visited
                    if (grid[nr][nc] == 0 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc, dist + 1});
                    }
                }
            }
        }

        // No path found
        return -1;
    }

    // Test cases
    public static void main(String[] args) {
        ShortestPathBinaryMatrix sol = new ShortestPathBinaryMatrix();

        int[][] grid1 = {
            {0, 1},
            {1, 0}
        };
        System.out.println(sol.shortestPathBinaryMatrix(grid1)); //expected: 2

        int[][] grid2 = {
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };
        System.out.println(sol.shortestPathBinaryMatrix(grid2)); //expected: 4

        int[][] grid3 = {
            {1, 0},
            {0, 0}
        };
        System.out.println(sol.shortestPathBinaryMatrix(grid3)); //expected: -1
    }
}

import java.util.*;

public class RedundantConnection {

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;

        //AL representation of the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        //process edges one by one
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            Set<Integer> visited = new HashSet<>();

            //if u can already reach v, then adding this edge creates a cycle
            if (hasPath(graph, u, v, visited)) {
                return edge;
            }

            //otherwise, add the edge to the graph
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return new int[0];
    }

    private boolean hasPath(Map<Integer, List<Integer>> graph, int curr, int target, Set<Integer> visited) {
        //if we reacehd the target, a path exists
        if (curr == target) {
            return true;
        }
        //mark current node as visited
        visited.add(curr);
        //explore neighbors
        for (int neighbor : graph.get(curr)) {
            //only explore unvisted nodes to avoid cycles/infinite loops
            if (!visited.contains(neighbor)) {
                //recursively search neighbor
                if (hasPath(graph, neighbor, target, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        RedundantConnection sol = new RedundantConnection();

        int[][] edges1 = {
            {1, 2},
            {1, 3},
            {2, 3}
        };
        System.out.println(Arrays.toString(sol.findRedundantConnection(edges1))); //expected: [2, 3]

        int[][] edges2 = {
            {1, 2},
            {2, 3},
            {3, 4},
            {1, 4},
            {1, 5}
        };
        System.out.println(Arrays.toString(sol.findRedundantConnection(edges2))); //expected: [1, 4]

        int[][] edges3 = {
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 5},
            {2, 5}
        };
        System.out.println(Arrays.toString(sol.findRedundantConnection(edges3))); //expected: [2, 5]
    }
}

import java.util.*;

public class IsGraphBipartite {

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1); //can also use default initialization of 0/1/2

        //important: graph may be disconnected, so we need to check every node
        for (int start = 0; start < n; start++) {

            //if this node is already colored, it was part of a component we already explored
            if (color[start] != -1) {
                continue;
            }

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(start);
            color[start] = 0; //start this component with color 0

            while (!queue.isEmpty()) {
                int node = queue.poll();

                //explore all neighbors of the current node
                for (int neighbor : graph[node]) {

                    //if neighbor has not been colored yet, assign it the opposite color
                    if (color[neighbor] == -1) {
                        color[neighbor] = 1 - color[node];
                        queue.offer(neighbor);
                    }

                    //if neighbor already has the same color, then this graph is not bipartite
                    else if (color[neighbor] == color[node]) {
                        return false;
                    }
                }
            }
        }

        //if we finish without conflicts, the graph is bipartite
        return true;
    }

    public static void main(String[] args) {
        IsGraphBipartite sol = new IsGraphBipartite();

        int[][] graph1 = {
            {1, 3},
            {0, 2},
            {1, 3},
            {0, 2}
        };
        System.out.println(sol.isBipartite(graph1)); //expected: true

        int[][] graph2 = {
            {1, 2, 3},
            {0, 2},
            {0, 1, 3},
            {0, 2}
        };
        System.out.println(sol.isBipartite(graph2)); //expected: false

        int[][] graph3 = {
            {1},
            {0},
            {3},
            {2}
        };
        System.out.println(sol.isBipartite(graph3)); //expected: true
    }
}
