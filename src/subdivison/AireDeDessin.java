/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import javax.swing.JPanel;

public class AireDeDessin extends JPanel {

	public ArrayList<Point> points;
	public ArrayList<Point> courbe;
	public boolean closedPolygon;
	public double a;
	public double b;
	public int func;
	public Point mouse;
	public Point dragPoint;
	public int nearestIndex;

	public AireDeDessin() {
		points = new ArrayList<Point>();
		courbe = new ArrayList<Point>();
		new EcouteurDeSouris(this);
		closedPolygon = true;
		a = 0.25;
		b = 0.5;
		mouse = new Point(getWidth() / 2, getHeight() / 2);
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		dessinerPoints(g);
		dessinerCourbe(g);
		dessinerPointilles(g);
	}

	private void dessinerPoints(Graphics g) {
		if (!points.isEmpty()) {
			Point pPrec = null;
			if (closedPolygon) {
				pPrec = points.get(points.size() - 1);
			}
			for (Point p : points) {
				dessinerPoint(g, p);
				if (pPrec != null) {
					dessinerLigne(g, p, pPrec);
				}
				pPrec = p;
			}
		}
	}

	private void dessinerCourbe(Graphics g) {
		if (!courbe.isEmpty()) {
			g.setColor(Color.red);
			Point pPrec = null;
			if (closedPolygon) {
				pPrec = courbe.get(courbe.size() - 1);
			}
			for (Point p : courbe) {
				if (pPrec != null) {
					dessinerLigne(g, p, pPrec);
				}
				pPrec = p;
			}
			g.setColor(Color.black);
		}
	}

	private void dessinerPointilles(Graphics g) {
		if (mouse != null && dragPoint == null && points.size() > 1) {
			Graphics2D g2 = (Graphics2D) g;
			float dash1[] = {5.f};
			BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
			g2.setStroke(dashed);
			g2.setColor(Color.lightGray);
			if (closedPolygon || (nearestIndex != 0 && nearestIndex != points.size())) {
				Point a = points.get((nearestIndex - 1 + points.size()) % points.size());
				Point b = points.get(nearestIndex % points.size());
				dessinerLigne(g, a, mouse);
				dessinerLigne(g, mouse, b);
			} else {
				if (nearestIndex == 0) {
					Point a = points.get(0);
					dessinerLigne(g, a, mouse);
				} else {
					Point a = points.get(points.size() - 1);
					dessinerLigne(g, a, mouse);
				}
			}
			g2.setStroke(new BasicStroke());
		}
	}

	public Point clicSurPoint(Point point) {
		for (Point p : points) {
			if (distanceSquared(point, p) < 100) {
				return p;
			}
		}
		return null;
	}

	private double distanceSquared(Point a, Point b) {
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
	}

	private void dessinerPoint(Graphics g, Point p) {
		g.drawOval(p.getAwtPoint().x - 5, p.getAwtPoint().y - 5, 10, 10);
	}

	private void dessinerLigne(Graphics g, Point p, Point pPrec) {
		g.drawLine(p.getAwtPoint().x, p.getAwtPoint().y, pPrec.getAwtPoint().x, pPrec.getAwtPoint().y);
	}

	public void calculerCourbe() {
//		courbe = SubdivisionProg.chaikin(closedPolygon, points);
//		courbe = SubdivisionProg.fourPointsScheme(closedPolygon, points);
		switch (func) {
			case SubdivisionProg.CHAIKIN:
				courbe = SubdivisionProg.chaikin(closedPolygon, points);
				break;
			case SubdivisionProg.CORNER:
				ArrayList<Double> A = new ArrayList<Double>();
				A.add(1 - a);
				A.add(a);
				ArrayList<Double> B = new ArrayList<Double>();
				B.add(1 - b);
				B.add(b);
				courbe = SubdivisionProg.cornerCutting(closedPolygon, points, A, B);
				break;
			case SubdivisionProg.FOURPOINT:
				courbe = SubdivisionProg.fourPointScheme(closedPolygon, points, a);
				break;
			case SubdivisionProg.SPLINE:
				courbe = SubdivisionProg.subdivisionSpline(closedPolygon, points);
				break;
		}
	}

	void setFunction(int func) {
		this.func = func;
	}
}