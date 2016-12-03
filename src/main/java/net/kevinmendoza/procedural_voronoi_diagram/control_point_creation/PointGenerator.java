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

import java.util.Random;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlDefaultPoint;
import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public class PointGenerator extends AbstractControlGenerator {

	/**
	 *  
	 * This subclass of AbstractControlGenerator handles the creation of voronoi
	 * centers by placing points. To produce custom points, implement a 
	 * custom child of this class. 
	 * 
	 *@param long seed
	 *			a long seed value to use in the generator
	 *
	 *@param double spacing
	 *			average spacing between points
	 */
	public PointGenerator(long seed,double spacing){
		super(seed);
		setSpacing(spacing);
	}

	/**
	 * allows for a dynamic change in the average spacing of a voronoi diagram.
	 * 
	 * @param double[] pointVec
	 * 		an x,y,z 3D double array which the superclass uses to query new spacing.
	 * 		Leave this method empty if you wish to have smooth spacing.
	 * 
	 */
	@Override
	protected void changeSpacing(double[] pointVec) {
	}
	
	/**
	 * creates an object implementing the ControlPointInterface. By default this method creates
	 * a point.
	 * 
	 * @param double[] center
	 * 		an x,y,z 3D double array which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 * 
	 */
	@Override
	protected ControlPointInterface buildCenterObject(double[] center) {
		return new ControlDefaultPoint
				.ControlPointBuilder()
				.withPoint(center)
				.withWeightMult(getWeightMult(center))
				.withWeightPower(getPowerMult(center))
				.Build();
	}

	/**
	 * returns a Random object based on the coordinates of a given point.
	 * 
	 * @param double[] point
	 * 		an x,y,z 3D double array which the superclass uses to initialize a control point at 
	 * 		the given spatial coordinates.
	 */
	protected Random getPointBasedRandom(double[] point){
		return new Random(getPointBasedSeed(point));
	}
}
