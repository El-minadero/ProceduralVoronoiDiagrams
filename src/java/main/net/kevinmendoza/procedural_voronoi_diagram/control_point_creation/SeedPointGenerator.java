package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointUtility;

class SeedPointGenerator {

	private long seed;
	private final int SEARCH_RADIUS = 2;

	public SeedPointGenerator(long seed){
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
