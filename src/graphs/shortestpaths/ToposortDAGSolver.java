package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();

        edgeTo.put(start, null);

        Stack<V> result = new Stack<>();
        Set<V> visited = new HashSet<>();
        List<V> allV = getAllV(graph, start);

        for(V v: allV){
            if(!visited.contains(v)){
                dfsPostOrder(graph, v, visited, result);
            }
        }

        for(V v: allV){
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        distTo.put(start, 0.0);

        while(!result.isEmpty()){
            V curr = result.pop();
            for(Edge<V> e: graph.neighbors(curr)){
                V to = e.to;
                double getTo = distTo.get(to);
                double getCurr = distTo.get(curr);

                if(getTo > getCurr + e.weight){
                    distTo.put(to, distTo.get(curr) + e.weight);
                    edgeTo.put(to, e);
                }
            }
        }

        //distTo.put(start,0.0);
    }

    private List<V> getAllV(Graph<V> graph, V start){
        List<V> result  = new ArrayList<>();
        Set<V> visited = new HashSet<>();

        Queue<V> q = new LinkedList<>();
        q.offer(start);
        visited.add(start);
        result.add(start);

        while(!q.isEmpty()){
            V curr = q.poll();
            for(Edge<V> neighbor: graph.neighbors(curr)){
                if(!visited.contains(neighbor.to)){
                    visited.add(neighbor.to);
                    result.add(neighbor.to);
                    q.offer(neighbor.to);
                }
            }
        }
        return result;
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, Stack<V> result) {
        visited.add(start);
        for(Edge<V> neighbor : graph.neighbors(start)){
            V to = neighbor.to;
            if(!visited.contains(to)){
                  dfsPostOrder(graph, to, visited, result);
            }
        }
        result.add(start);
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
