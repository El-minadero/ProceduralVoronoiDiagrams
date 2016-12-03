package net.kevinmendoza.procedural_voronoi_diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class VoronoiGraph<V> {

	private HashMap<String,Vertex> vertices;
	private HashMap<Integer,Edge> edges;
	
	public VoronoiGraph(){
		vertices = new HashMap<String,Vertex>();
		edges = new HashMap<Integer,Edge>();
	}
	
	public VoronoiGraph(List<V> nodes){
		vertices = new HashMap<String,Vertex>();
		edges = new HashMap<Integer,Edge>();
		for(V node : nodes){
			vertices.put(node.toString(), new Vertex(node));
		}
	}

	public boolean addEdge(V o1, V o2) {
		Vertex one = vertices.get(o1.toString());
		Vertex two = vertices.get(o2.toString());
		if(one.equals(two)){
			return false;  
		}
		Edge e = new Edge(one, two);
		if(edges.containsKey(e.hashCode())){
			return false;
		}
		else if(one.containsNeighbor(e) || two.containsNeighbor(e)){
			return false;
		}
		edges.put(e.hashCode(), e);
		one.addNeighbor(e);
		two.addNeighbor(e);
		return true;
	}

	public V getVertex(String key) {
		return this.vertices.get(key).getSegment();
	}
	
	public Set<String> vertexKeys(){
		return this.vertices.keySet();
	}

	public Set<String> getNeighborsOf(String key) {
		Vertex center = vertices.get(key);
		HashSet<String> connected = new HashSet<String>();
		for(Edge e : center.getNeighbors()){
			connected.add(e.getNeighbor(center).getLabel());
		}
		return connected;
	}
	
	private class Edge {

		private Vertex one,two;

		private Edge(Vertex one, Vertex two){
			this.one = one;
			this.two = two;
		}

		private Vertex getNeighbor(Vertex current){
			if(!(current.equals(one) || current.equals(two))){
				return null;
			}
			return (current.equals(one)) ? two : one;
		}

		private Vertex getOne(){
			return one;
		}

		private Vertex getTwo(){
			return two;
		}

		@Override
		public String toString() {
			return one.toString() + two.toString();
		}

		@Override
		public boolean equals(Object other){
			if(!(other instanceof VoronoiGraph.Edge)){
				return false;
			}
			Edge e = (Edge) other;
			return e.getOne().equals(one) && e.getTwo().equals(two);
		}


		@Override
		public int hashCode() {
			return (one.getLabel() + two.getLabel()).hashCode();
		}
	}

	private class Vertex {

		private ArrayList<Edge> neighborhood;
		private V seg;

		private Vertex(V seg){
			this.seg = seg;
			this.neighborhood = new ArrayList<Edge>();
		}

		private V getSegment(){
			return seg;
		}

		private void addNeighbor(Edge edge){
			if(this.neighborhood.contains(edge)){
				return;
			}
			this.neighborhood.add(edge);
		}

		private boolean containsNeighbor(Edge edge){
			return this.neighborhood.contains(edge);
		}

		private void removeNeighbor(Edge edge){
			this.neighborhood.remove(edge);
		}

		private String getLabel(){
			return seg.toString();
		}

		public ArrayList<Edge> getNeighbors() {
			return new ArrayList<Edge>(this.neighborhood);
		}

		@Override
		public int hashCode() {
			return this.getLabel().hashCode();
		}

		@Override
		public boolean equals(Object other){
			if(!(other instanceof VoronoiGraph.Vertex)){
				return false;
			}
			Vertex v = (Vertex) other;
			return this.getLabel().equals(v.getLabel());
		}
	}

}