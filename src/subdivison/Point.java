/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

/**
 *
 * @author gervaila
 */
public class Point {
	public double x;
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(java.awt.Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public java.awt.Point getAwtPoint() {
		return new java.awt.Point((int)x, (int)y);
	}
	
}
