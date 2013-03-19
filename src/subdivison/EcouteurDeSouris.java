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
			aire.points.add(dragPoint);
		} else {
			if (e.getButton() == MouseEvent.BUTTON1) {
				dragPoint = p;
			} else {
				aire.points.remove(p);
			}
		}
		aire.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragPoint != null) {
			dragPoint.x = e.getX();
			dragPoint.y = e.getY();
		}
		aire.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragPoint = null;
		aire.repaint();
	}
}
