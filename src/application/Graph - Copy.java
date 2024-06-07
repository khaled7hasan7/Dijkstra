package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

		private final Map<String, List<Vertex>> vertices;

		public Graph() {
			this.vertices = new HashMap<String, List<Vertex>>();
		}

		public void addVertex(String string) {
			vertices.putIfAbsent(string, new ArrayList<>());
		}

		public void addEdge(String label1, String label2, int disto) {
		    Vertex v2 = new Vertex(label2,disto);
		    vertices.get(label1).add(v2); 
		}
		
		public List<Vertex> getShortestPath(String start, String finish) {
			final Map<String, Integer> distances = new HashMap<String, Integer>();
			final Map<String, Vertex> previous = new HashMap<String, Vertex>();
			PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();

			for (String vertex : vertices.keySet()) {
				if (vertex.equalsIgnoreCase(start)) {
					distances.put(vertex, 0);
					nodes.add(new Vertex(vertex, 0));
				} else {
					distances.put(vertex, Integer.MAX_VALUE);
					nodes.add(new Vertex(vertex, Integer.MAX_VALUE));
				}
				previous.put(vertex, null);
			}

			while (!nodes.isEmpty()) {
				Vertex smallest = nodes.poll();	
				if (smallest.getCountry().equalsIgnoreCase(finish)) {
					final List<Vertex> path = new ArrayList<Vertex>();
					while (previous.get(smallest.getCountry()) != null) {
						System.out.println(smallest.getCountry() + "  " + smallest.getDistance());
						path.add(new Vertex (smallest.getCountry(),smallest.getDistance()));
						smallest = previous.get(smallest.getCountry());
					}
					return path;
				}
 
				if (distances.get(smallest.getCountry()) ==Integer.MAX_VALUE) {
					break;
				}

				for (Vertex neighbor : vertices.get(smallest.getCountry())) {
					int alt = distances.get(smallest.getCountry()) + neighbor.getDistance();
					if (alt < distances.get(neighbor.getCountry())) {
						distances.put(neighbor.getCountry(), alt);
						previous.put(neighbor.getCountry(), smallest);

				//		forloop: 
						for (Vertex n : nodes) {
							if (n.getCountry().equalsIgnoreCase(neighbor.getCountry()) ) {
								nodes.remove(n);
								n.setDistance(alt);
								
								nodes.add(n);
								break ;     //forloop;
							}
						}
					}
				}
			}

			return new ArrayList<Vertex>();
		}
}