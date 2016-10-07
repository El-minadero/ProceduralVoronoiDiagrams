package net.kevinmendoza.procedural_voronoi_diagram.control_point_creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SeedPointGenerator {

	private long seed;
	private final int SEARCH_RADIUS = 2;

	public SeedPointGenerator(long seed){
		this.seed = seed;
	}
	
	public List<double[]> getSpacedPoints(double spacing, double[] center){
		double[] newCenter = {center[0]/spacing, center[1]/spacing, center[2]/spacing};
		List<double[]> points = new ArrayList<double[]>();
		for(int xx = -SEARCH_RADIUS; xx <=SEARCH_RADIUS;xx++){
			for(int yy = -SEARCH_RADIUS; yy<=SEARCH_RADIUS;yy++){
				for(int zz = -SEARCH_RADIUS; zz<=SEARCH_RADIUS;zz++){
					if(zz!=0 || xx!=0){
						double[] point = {(newCenter[0] + xx)*spacing,
										  (newCenter[1] + yy)*spacing,
										  (newCenter[2] + zz)*spacing};
						points.add(point);
					}
				}
			}
		}
		return points;
	}
	
		
	private double[] offsetPoint(double[] point, int spacing){
		//System.out.println("offsetting pnt");
		long i = (long) Math.floor(point[0]/spacing);
		i+= i*seed + Math.floor(point[1]/spacing);
		i+= i*seed + Math.floor(point[2]/spacing);
		i*=seed;
		Random rand = new Random(i);
		double[] seedPoint = { rand.nextInt(spacing) + point[0],
							   rand.nextInt(spacing) + point[1],
							   rand.nextInt(spacing) + point[2] };
		return seedPoint;
	}
	
	public List<double[]> offsetPoints(List<double[]> points, int spacing){
		List<double[]> newPoints = new ArrayList<double[]>();
		for(double[] point : points){
			newPoints.add(offsetPoint(point,spacing));
		}
		return newPoints;
	}

}
