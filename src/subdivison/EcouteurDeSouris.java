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
	Point dragPoint;

	public EcouteurDeSouris(AireDeDessin aire) {
		this.aire = aire;
		aire.addMouseListener(this);
		aire.addMouseMotionListener(this);
		dragPoint = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = aire.clicSurPoint(new Point(e.getPoint()));
		if (p == null) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				dragPoint = new Point(e.getPoint());
			}
			System.out.println("ajout d'un point");
			int index = 0;
			int nearestIndex = 0;
			int nearestDist = Integer.MAX_VALUE;
			if (!aire.points.isEmpty()) {
				Point pLast = aire.points.get(aire.points.size() - 1);
				for (Point p2 : aire.points) {
					int dist = (int) ((dragPoint.x - p2.x) * (dragPoint.x - p2.x) + (dragPoint.y - p2.y) * (dragPoint.y - p2.y)
							+ (pLast.x - dragPoint.x) * (pLast.x - dragPoint.x) + (pLast.y - dragPoint.y) * (pLast.y - dragPoint.y)
							- ((p2.x - pLast.x) * (p2.x - pLast.x) + (p2.y - pLast.y) * (p2.y - pLast.y)));
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
					if ((dragPoint.x - pLast.x) * (dragPoint.x - pLast.x) + (dragPoint.y - pLast.y) * (dragPoint.y - pLast.y)
							< (dragPoint.x - pFirst.x) * (dragPoint.x - pFirst.x) + (dragPoint.y - pFirst.y) * (dragPoint.y - pFirst.y)) {
						nearestIndex = aire.points.size();
					}
				}
			}
			aire.points.add(nearestIndex, dragPoint);
		} else {
			if (e.getButton() == MouseEvent.BUTTON1) {
				dragPoint = p;
			} else {
				aire.points.remove(p);
			}
		}
		checkDrag();
		aire.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragPoint != null) {
			dragPoint.x = e.getX();
			dragPoint.y = e.getY();
		}
		checkDrag();
		aire.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragPoint = null;
		aire.repaint();
	}
	
	private void checkDrag() {
		if (dragPoint != null) {
			int padding = 10;
			if (dragPoint.x < padding)
				dragPoint.x = padding;
			else if (dragPoint.x > aire.getWidth() - 1 - padding)
				dragPoint.x = aire.getWidth() - 1 - padding;
			
			if (dragPoint.y < padding)
				dragPoint.y = padding;
			else if (dragPoint.y > aire.getHeight() - 1 - padding)
				dragPoint.y = aire.getHeight() - 1 - padding;
		}
	}
}
