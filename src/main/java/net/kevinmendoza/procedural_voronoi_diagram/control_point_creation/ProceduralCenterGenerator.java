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
package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointUtility;

class ProceduralCenterGenerator {

	private long seed;
	private final int SEARCH_RADIUS = 1;

	public ProceduralCenterGenerator(long seed){
		this.seed = seed;
	}
	
	public long getSeed(){
		return seed;
	}

	public List<int[]> getSpacedPoints(double spacing, int dimensions, double[] center){
		int[] newCenter = {(int) (center[0]/spacing), 
						   (int) (center[1]/spacing), 
						   (int) (center[2]/spacing)};
		
		List<int[]> points = new ArrayList<int[]>();
		int[] coordinates = {0,0};
		for(int xx = -SEARCH_RADIUS; xx <=SEARCH_RADIUS;xx++){
			for(int yy = -SEARCH_RADIUS; yy<=SEARCH_RADIUS;yy++){
				if(dimensions == 3 ) {
					for(int zz = -SEARCH_RADIUS; zz<=SEARCH_RADIUS;zz++){
						coordinates = new int[3];
						coordinates[0] = xx;
						coordinates[1] = yy;
						coordinates[2] = zz;
					}
				}
				else{
					coordinates = new int[2];
					coordinates[0] = xx;
					coordinates[1] = yy;
				}
				makeMooreLattice(coordinates,newCenter,spacing,points);
			}
		}
		return points;
	}
	
	private void makeMooreLattice(int[] coordinates,int[] center, double spacing,List<int[]> points){
			int[] point = new int[coordinates.length];
			for(int i = 0;i<coordinates.length;i++){
				point[i] = (int) ((coordinates[i] + center[i])*spacing);
			}
			points.add(point);
	}
		
	private double[] offsetPoint(int[] point, double spacing, double variance){
		//center point creation
		Random rand = new Random(ControlPointUtility.CreateSeedHash(seed, point));
		double[] offsetPoint = new double[point.length];
		for(int i = 0;i<point.length;i++){
			offsetPoint[i] = point[i] + rand.nextInt((int)Math.floor(spacing/variance));
		}
		return offsetPoint;
	}
	
	public List<double[]> offsetPoints(List<int[]> points, double spacing,double variance){
		List<double[]> newPoints = new ArrayList<double[]>();
		for(int[] point : points){
			newPoints.add(offsetPoint(point,spacing,variance));
		}
		return newPoints;
	}

}
