/*  ProceduralVoronoiDiagrams: a procedural voronoi diagram library.
    Copyright (C) 2016 Kevin A. Mendoza. kevinmendoza@icloud.como

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
			double[] point,int dimensions) {
		if(dimensions<2) {
			dimensions = 2;
		}
			
		//System.out.println("STRING");
		List<ControlPointInterface> centers = creationBehavior.getLocalControlPoints(point,dimensions);
		//System.out.println("size" + centers.size());
		List<ControlPointInterface> newCenters = combineCenters(centers);
		newCenters.sort((ControlPointInterface o1, ControlPointInterface o2)
				->ControlPointInterface.Compare(o1, o2, point));
		
		return newCenters;
	}

	private List<ControlPointInterface> combineCenters(List<ControlPointInterface> centers) {
		
		VoronoiGraph<ControlPointInterface> newGraph = new VoronoiGraph<ControlPointInterface>(centers);
		Set<String> keys = newGraph.vertexKeys();
		
		for(String key1 : keys){
			for(String key2 : keys){
				if(!key1.equalsIgnoreCase(key2)){
					if(newGraph.getVertex(key1).intersects(newGraph.getVertex(key2))){
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
