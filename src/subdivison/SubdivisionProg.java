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

	public static final int CORNER = 0;
	public static final int CHAIKIN = 1;
	public static final int FOURPOINT = 2;
	public static final int SPLINE = 3;

	public static ArrayList<Point> chaikin(boolean closedPolygon, ArrayList<Point> controles) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(0.75);
		a.add(0.25);
		b.add(0.25);
		b.add(0.75);
		return cornerCutting(closedPolygon, controles, a, b);
	}

	public static ArrayList<Point> cornerCutting(boolean closedPolygon, ArrayList<Point> controles, ArrayList<Double> a, ArrayList<Double> b) {
		for (int i = 0; i < 8; i++) {
			controles = calculSubdivision(closedPolygon, controles, a, b, 0, 0);
		}

		return controles;
	}

	public static ArrayList<Point> fourPointsScheme(boolean closedPolygon, ArrayList<Point> controles, double epsilon) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(1.d);
		b.add(-epsilon / 2);
		b.add((1 + epsilon) / 2);
		b.add((1 + epsilon) / 2);
		b.add(-epsilon / 2);
		
		int numIterations = 13;

		for (int i = 0; i < numIterations; i++) {
			controles = calculSubdivision(true, controles, a, b, 0, -1);
		}

		if (!closedPolygon) {
			int taille = controles.size();
			int n = (int) Math.pow(2, numIterations);
			for (int i = 0; i < n; i++) {
				controles.remove(taille - n);
			}
		}

		return controles;
	}

	public static ArrayList<Point> subdivisionSpline(boolean closedPolygon, ArrayList<Point> controles) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(0.5);
		a.add(0.5);
		b.add(1.d / 8);
		b.add(6.d / 8);
		b.add(1.d / 8);
		return cornerCutting(closedPolygon, controles, a, b);
	}

	public static ArrayList<Point> calculSubdivision(boolean closedPolygon, ArrayList<Point> controles, ArrayList<Double> a, ArrayList<Double> b, int aJ, int bJ) {
		ArrayList<Point> res = new ArrayList<Point>();
		int s = controles.size() * 2;
		if (!closedPolygon) {
			s -= 2;
		}
		for (int i = 0; i < s; i++) {
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
			res.add(new Point(x, y));
		}
		return res;
	}
}
