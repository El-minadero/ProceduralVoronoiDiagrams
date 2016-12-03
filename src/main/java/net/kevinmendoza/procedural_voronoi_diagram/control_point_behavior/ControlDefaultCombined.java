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
package net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior;

import java.util.ArrayList;
import java.util.HashSet;

public class ControlDefaultCombined extends ControlAbstractPoint {

	private ArrayList<ControlPointInterface> segs;
	private final String label;
	
	private ControlDefaultCombined(CombinedControlPointBuilder builder){
		super("Combined",builder.linearMult,builder.power);
		segs = new ArrayList<ControlPointInterface>();
		segs.addAll(builder.centers);
		hashCode = segs.get(0).hashCode();
		label = createLabel();
	}

	@Override
	protected double getUnweightedDistance(double[] query) {
		double weight=10000, tempweight;
		for(ControlPointInterface seg : segs){
			tempweight = seg.getDistance(query,true);
			if(tempweight<weight)
				weight = tempweight;
		}
		return weight;
	}


	@Override
	protected double getDistanceCutoff() {
		return 3.0;
	}

	@Override
	public double[] getClosestPoint(double[] point) {
		
		double weight=10000, tempweight;
		double[] closestPoint = {0,0};
		
		for(ControlPointInterface seg : segs){
			tempweight = seg.getDistance(point,false);
			if(tempweight<weight){
				weight = tempweight;
				closestPoint = seg.getClosestPoint(point);
			}
		}
		return closestPoint;
	}

	
	@Override
	public boolean intersects(ControlPointInterface otherControlPoint) {
		boolean intersect = false;
		for(ControlPointInterface seg : segs){
			intersect = seg.intersects(otherControlPoint);
		}
		return intersect;
	}
	
	public String createLabel(){
		String lab = "";
		for(int i = 0; i< segs.size();i++){
			lab = lab + "|" + segs.get(i).toString();
		}
		return lab;
	}
	
	@Override
	public String toString(){
		return getType() + " " + label;
	}
	
	@Override
	public int hashCode(){
		return hashCode;
	}
	
	public static class CombinedControlPointBuilder 
	extends ControlBuilderAbstract<CombinedControlPointBuilder> {
		
		private HashSet<ControlPointInterface> centers;
		
		public CombinedControlPointBuilder(){
			super();
			this.centers = new HashSet<ControlPointInterface>();
		}
		
		public CombinedControlPointBuilder addCenters(HashSet<ControlPointInterface> toAdd){
			this.centers.addAll(toAdd);
			return this;
		}
		public ControlDefaultCombined Build(){
			return new ControlDefaultCombined(this);
		}
	}

}
