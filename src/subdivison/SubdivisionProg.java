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

	private static class IndexNoeud {

		protected boolean closedPolygon;
		protected int numControles;

		public IndexNoeud(boolean closedPolygon, int numControles) {
			this.closedPolygon = closedPolygon;
			this.numControles = numControles;
		}

		public void setNumControles(int numControles) {
			this.numControles = numControles;
		}

		public int getIndex(int index) {
			return (index + numControles) % numControles;
		}

		public int getS() {
			return (closedPolygon ? numControles : numControles - 1) * 2;
		}
	}

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
		IndexNoeud in = new IndexNoeud(closedPolygon, controles.size());
		for (int i = 0; i < 8; i++) {
			controles = calculSubdivision(closedPolygon, controles, a, b, 0, 0, in);
			in.setNumControles(controles.size());
		}
		return controles;
	}

	public static ArrayList<Point> fourPointScheme(boolean closedPolygon, ArrayList<Point> controles, double epsilon) {
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(1.d);
		b.add(-epsilon / 2);
		b.add((1 + epsilon) / 2);
		b.add((1 + epsilon) / 2);
		b.add(-epsilon / 2);

		int numIterations = 13;

		IndexNoeud in;
		if (!closedPolygon) {
			in = new IndexNoeud(closedPolygon, controles.size()) {
				@Override
				public int getIndex(int index) {
					return Math.max(Math.min(index, numControles - 1), 0);
				}

				@Override
				public int getS() {
					return numControles * 2;
				}
			};
		} else {
			in = new IndexNoeud(closedPolygon, controles.size());
		}
		for (int i = 0; i < numIterations; i++) {
			controles = calculSubdivision(closedPolygon, controles, a, b, 0, -1, in);
			in.setNumControles(controles.size());
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

	private static ArrayList<Point> calculSubdivision(boolean closedPolygon, ArrayList<Point> controles, ArrayList<Double> a, ArrayList<Double> b, int aJ, int bJ, IndexNoeud in) {
		ArrayList<Point> res = new ArrayList<Point>();
		int s = in.getS();
		for (int i = 0; i < s; i++) {
			double x = 0, y = 0;
			if (i % 2 == 0) {
				for (int j = 0; j < a.size(); j++) {
					int index = in.getIndex(i / 2 + (j + aJ));
					x += a.get(j) * controles.get(index).x;
					y += a.get(j) * controles.get(index).y;
				}
			} else {
				for (int j = 0; j < b.size(); j++) {
					int index = in.getIndex(i / 2 + (j + bJ));
					x += b.get(j) * controles.get(index).x;
					y += b.get(j) * controles.get(index).y;
				}
			}
			res.add(new Point(x, y));
		}
		return res;
	}
}
