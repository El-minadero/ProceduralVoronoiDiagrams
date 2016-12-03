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

import net.kevinmendoza.procedural_voronoi_diagram.control_point_creation.ControlPointCreation;

public class VoronoiFactory {

	public VoronoiFactory(){
	}
	
	public VoronoiDiagram BuildVoronoi(ControlPointCreation controlInterface) {
		return new VoronoiImplementation
				.VoronoiBuilder()
				.withControlPointCreation(controlInterface)
				.Build();
		
	}

}
