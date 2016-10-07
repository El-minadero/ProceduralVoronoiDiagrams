package net.kevinmendoza.procedural_voronoi_diagram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_creation.ControlPointCreation;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

class VoronoiImplementation implements VoronoiDiagram {

	private ControlPointCreation creationBehavior;

	private VoronoiImplementation(VoronoiBuilder voronoiBuilder){
		this.creationBehavior = voronoiBuilder.creation;
	}
	
	@Override
	public List<ControlPointInterface> getLowestCostObjectsAtPoint(
			double[] point) {
		//System.out.println("STRING");
		List<ControlPointInterface> centers = creationBehavior.getLocalControlPoints(point);
		//System.out.println("size" + centers.size());
		List<ControlPointInterface> newCenters = combineCenters(centers);
		newCenters.sort((ControlPointInterface o1, ControlPointInterface o2)
				->ControlPointInterface.Compare(o1, o2, point));
		for(ControlPointInterface inter : newCenters){
			System.out.println("distances:" + inter.getDistance(point,true) );
		}
		return newCenters;
	}

	private List<ControlPointInterface> combineCenters(List<ControlPointInterface> centers) {
		
		VoronoiGraph<ControlPointInterface> newGraph = new VoronoiGraph<ControlPointInterface>(centers);
		Set<String> keys = newGraph.vertexKeys();
		
		for(String key1 : keys){
			for(String key2 : keys){
				if(!key1.equalsIgnoreCase(key2)){
					if(newGraph.getVertex(key1).intersects(newGraph.getVertex(key2))){
						newGraph.getVertex(key1).intersects(newGraph.getVertex(key2));
						newGraph.addEdge(newGraph.getVertex(key1),  newGraph.getVertex(key2));
						
					}
				}
			}
		}
		return depthFirstCenterCreation(newGraph);
	}
	
	private List<ControlPointInterface> depthFirstCenterCreation(
			VoronoiGraph<ControlPointInterface> newGraph) {
		
		List<ControlPointInterface> centers = new ArrayList<ControlPointInterface>();
		Set<String> keys = newGraph.vertexKeys();
		HashSet<String> pastBranches = new HashSet<String>();
		
		for(String key : keys){
			if(!pastBranches.contains(key)){
				
				HashSet<String> connectedKeys = new HashSet<String>();
				connectedKeys.add(key);
				recursivelySearch(connectedKeys,pastBranches,newGraph,key);
				HashSet<ControlPointInterface> controlPoints = new HashSet<ControlPointInterface>();
				
				for(String newBranchKeys : connectedKeys){
					controlPoints.add(newGraph.getVertex(newBranchKeys));
				}
				centers.add(creationBehavior.makeCombinedPoint(controlPoints));
			}
		}
		
		return centers;
	}

	private void recursivelySearch(HashSet<String> currentBranch,
			HashSet<String> pastBranches,
			VoronoiGraph<ControlPointInterface> newGraph,
			String currentVertex) {
		
			Set<String> neighbors = newGraph.getNeighborsOf(currentVertex);

			for(String key : neighbors){
				if(!currentBranch.contains(key) && !pastBranches.contains(key)){
					currentBranch.add(key);
					pastBranches.add(key);
					recursivelySearch(currentBranch,pastBranches,newGraph,key);
				}
			}
	}

	public static class VoronoiBuilder {
		
		private ControlPointCreation creation;

		public VoronoiDiagram Build() {
			return new VoronoiImplementation(this);
		}

		public VoronoiBuilder withControlPointCreation(ControlPointCreation creation) {
			this.creation=creation;
			return this;
		}

		public VoronoiBuilder() {
		}
	}
}
