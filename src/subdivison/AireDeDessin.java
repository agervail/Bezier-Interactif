/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class AireDeDessin extends JPanel {

	public ArrayList<Point> points;
	public ArrayList<Point> courbe;
	public boolean closedPolygon;
	public int func;
	
	public AireDeDessin() {
		points = new ArrayList<Point>();
		courbe = new ArrayList<Point>();
		new EcouteurDeSouris(this);
		closedPolygon = true;
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		dessinerPoints(g);
		calculerCourbe();
		dessinerCourbe(g);
	}

	private void dessinerPoints(Graphics g) {
		if (!points.isEmpty()) {
			Point pPrec = null;
			if (closedPolygon) {
				pPrec = points.get(points.size() - 1);
			}
			System.out.println("cp = " + closedPolygon);
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

	private void calculerCourbe() {
//		courbe = SubdivisionProg.chaikin(closedPolygon, points);
//		courbe = SubdivisionProg.fourPointsScheme(closedPolygon, points);
				switch (func) {
			case SubdivisionProg.CHAIKIN:
				courbe = SubdivisionProg.chaikin(closedPolygon, points);
				break;
			case SubdivisionProg.CORNER:
				break;
			case SubdivisionProg.FOURPOINT:
				courbe = SubdivisionProg.fourPointsScheme(closedPolygon, points);
				break;
		}
	}

	void setFunction(int func) {
		this.func =func;
	}
}