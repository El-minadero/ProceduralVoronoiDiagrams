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

public class ControlDefaultPoint extends ControlAbstractPoint {

	private final double[] point;
	private final int dimensions;
	
	private ControlDefaultPoint(ControlPointBuilder builder){
		super("Point",builder.linearMult, builder.power);
		point = builder.point;
		dimensions = point.length;
		hashCode = ControlPointUtility.CreateHash(point);
		label = ControlPointUtility.CreateLabel(point);
	}
	

	public double[] getPoint(){
		double[] np = new double[dimensions];
		for(int i = 0; i<dimensions;i++){
			np[i] = point[i];
		}
		return np;
	}
	
	@Override
	public String toString(){
		return getType() + " " + label;
	}
	
	@Override
	protected double getDistanceCutoff() {
		// TODO Auto-generated method stub
		return 2.0;
	}
	
	@Override
	public boolean intersects(ControlPointInterface centerObject) {
		double weight = centerObject.getDistance(getPoint(),true);
		if(weight < getDistanceCutoff())
			return true;
		else
			return false;
	}


	@Override
	public double getUnweightedDistance(double[] queryPoint) {
		return ControlPointUtility.GetSRootDist(getPoint(), queryPoint);
	}
	
	@Override
	public double[] getClosestPoint(double[] p){
		return getPoint();
	}
	
	public int getRGBIdentifier(){
		return 400;
	}
	
	@Override
	public int hashCode(){
		return hashCode;
	}

	public static class ControlPointBuilder
	extends ControlBuilderAbstract<ControlPointBuilder> {
		
		private double[] point;
		
		public ControlPointBuilder(){
			super();
		}
		
		public ControlPointBuilder withPoint(double[] point){
			this.point = point;
			return this;
		}
		
		public ControlDefaultPoint Build(){
			return new ControlDefaultPoint(this);
		}
	}
}
