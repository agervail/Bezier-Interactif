/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subdivison;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author gervaila
 */
public class Subdivison {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        Fenetre f = new Fenetre();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
