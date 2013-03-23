/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author gervaila
 */
public class EcouteurDeSouris extends MouseAdapter {

	AireDeDessin aire;

	public EcouteurDeSouris(AireDeDessin aire) {
		this.aire = aire;
		aire.addMouseListener(this);
		aire.addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = aire.clicSurPoint(new Point(e.getPoint()));
		if (p == null) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				aire.dragPoint = new Point(e.getPoint());
			}
			System.out.println("ni = " + aire.nearestIndex);
			aire.points.add(aire.nearestIndex, aire.dragPoint);
			aire.calculerCourbe();
		} else {
			if (e.getButton() == MouseEvent.BUTTON1) {
				aire.dragPoint = p;
			} else {
				aire.points.remove(p);
				aire.calculerCourbe();
			}
		}
		checkDrag();
		aire.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (aire.dragPoint != null) {
			aire.dragPoint.x = e.getX();
			aire.dragPoint.y = e.getY();
		}
		checkDrag();
		aire.calculerCourbe();
		aire.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		aire.dragPoint = null;
		aire.mouse = new Point(e.getPoint());
		aire.nearestIndex = getNearestIndex();
		aire.repaint();
	}

	private void checkDrag() {
		if (aire.dragPoint != null) {
			int padding = 10;
			if (aire.dragPoint.x < padding) {
				aire.dragPoint.x = padding;
			} else if (aire.dragPoint.x > aire.getWidth() - 1 - padding) {
				aire.dragPoint.x = aire.getWidth() - 1 - padding;
			}

			if (aire.dragPoint.y < padding) {
				aire.dragPoint.y = padding;
			} else if (aire.dragPoint.y > aire.getHeight() - 1 - padding) {
				aire.dragPoint.y = aire.getHeight() - 1 - padding;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		aire.mouse = new Point(e.getPoint());
		aire.nearestIndex = getNearestIndex();
		aire.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		aire.mouse = null;
		aire.repaint();
	}

	private int getNearestIndex() {
		int index = 0;
		int nearestIndex = 0;
		double nearestDist = Double.MAX_VALUE;
		if (!aire.points.isEmpty()) {
			Point pLast = aire.points.get(aire.points.size() - 1);
			for (Point p2 : aire.points) {
				/*
				int dist = (int) ((aire.mouse.x - p2.x) * (aire.mouse.x - p2.x) + (aire.mouse.y - p2.y) * (aire.mouse.y - p2.y)
						+ (pLast.x - aire.mouse.x) * (pLast.x - aire.mouse.x) + (pLast.y - aire.mouse.y) * (pLast.y - aire.mouse.y)
						- ((p2.x - pLast.x) * (p2.x - pLast.x) + (p2.y - pLast.y) * (p2.y - pLast.y)));
				*/
				double dist = distance(aire.mouse, p2) + distance(pLast, aire.mouse) - distance(p2, pLast);
				if (dist < nearestDist) {
					nearestDist = dist;
					nearestIndex = index;
				}
				pLast = p2;
				index++;
			}
			if (nearestIndex == 0) {
				Point pFirst = aire.points.get(0);
				pLast = aire.points.get(aire.points.size() - 1);
				if (distance(aire.mouse, pLast) < distance(aire.mouse, pFirst)) {
					nearestIndex = aire.points.size();
				}
			}
		}
		return nearestIndex;
	}
	
	private double distance(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
}
