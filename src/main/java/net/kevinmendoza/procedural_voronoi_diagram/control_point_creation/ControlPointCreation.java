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

import java.util.HashSet;
import java.util.List;

import net.kevinmendoza.procedural_voronoi_diagram.control_point_behavior.ControlPointInterface;

public interface ControlPointCreation {

	/**
	 * Method produces a list of control point objects within range of the provided center point.
	 * 
	 * Specifying two dimensions returns at least 9 objects (Moore neighborhood), 3 dimensions 
	 * returns 27 (3 moore neighborhoods), and so on.
	 * 
	 * @param double[] center
	 * 			the center coordinates to get the Moore neighborhood from.
	 * 
	 * @param int dimensions
	 * 
	 *		the dimensionality of the control points. The minimum value this can be is 2.
	 *	Note that higher dimensional voronoi diagrams suffer from poor performance.
	 * 
	 */
	List<ControlPointInterface> getLocalControlPoints(double[] center, int dimensions);

	ControlPointInterface makeCombinedPoint(
			HashSet<ControlPointInterface> controlPoints);

}
