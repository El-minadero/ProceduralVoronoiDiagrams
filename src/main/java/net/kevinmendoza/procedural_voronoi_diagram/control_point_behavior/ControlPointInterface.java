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

public interface ControlPointInterface {

	boolean intersects(ControlPointInterface otherControlPoint);
	
	int getRGBIdentifier();

	double[] getClosestPoint(double[] point);
	
	double getDistance(double[] point,boolean weighted);
	
	boolean isType(String otherType);
	
	String toString();

	int hashCode();
	
	static int Compare(ControlPointInterface o1, ControlPointInterface o2, double[] point){
		double dist1 = o1.getDistance(point, true);
		double dist2 = o2.getDistance(point, true);
		if(dist1>dist2)
			return 1;
		else if(dist1==dist2)
			return 0;
		return -1;
	}
	
}
