/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

import java.util.ArrayList;

/**
 *
 * @author gervaila
 */
public class SubdivisionProg {
	
	public static final int CHAIKIN = 0;
	public static final int CORNER = 1;
	public static final int FOURPOINT = 2;
	
	

	public static ArrayList<Point> chaikin(boolean closedPolygon, ArrayList<Point> controles) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(3.d / 4);
		a.add(1.d / 4);
		b.add(1.d / 4);
		b.add(3.d / 4);
		return CornerCutting(closedPolygon, controles, a, b);
	}

	public static ArrayList<Point> CornerCutting(boolean closedPolygon, ArrayList<Point> controles, ArrayList<Double> a, ArrayList<Double> b) {

		for (int i = 0; i < 8; i++) {
			controles = calculSubdivision(closedPolygon, controles, a, b, 0, 0);
		}

		return controles;
	}

	public static ArrayList<Point> fourPointsScheme(boolean closedPolygon, ArrayList<Point> controles) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(1.d);
		b.add(-1.d / 16);
		b.add(9.d / 16);
		b.add(9.d / 16);
		b.add(-1.d / 16);

		for (int i = 0; i < 5; i++) {
			controles = calculSubdivision(closedPolygon, controles, a, b, 0, -1);
		}

		return controles;
	}

	public static ArrayList<Point> calculSubdivision(boolean closedPolygon, ArrayList<Point> controles, ArrayList<Double> a, ArrayList<Double> b, int aJ, int bJ) {
		ArrayList<Point> res = new ArrayList<Point>();
		int s = controles.size() * 2;
		if (!closedPolygon) {
			s -= 2;
		}
		for (int i = 0; i < s; i++) {
			Point p = new Point(0, 0);
			double x = 0, y = 0;
			if (i % 2 == 0) {
				for (int j = 0; j < a.size(); j++) {
					x += a.get(j) * controles.get((i / 2 + (j + aJ) + controles.size()) % controles.size()).x;
					y += a.get(j) * controles.get((i / 2 + (j + aJ) + controles.size()) % controles.size()).y;
				}
			} else {
				for (int j = 0; j < b.size(); j++) {
					x += b.get(j) * controles.get((i / 2 + (j + bJ) + controles.size()) % controles.size()).x;
					y += b.get(j) * controles.get((i / 2 + (j + bJ) + controles.size()) % controles.size()).y;
				}
			}

			p = new Point(x, y);
			res.add(p);
		}
		return res;
	}
}
