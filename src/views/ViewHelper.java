package views;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by hayden on 6/3/17.
 */
public class ViewHelper {

    /**
     * ViewHelper class for static methods used throughout the view packaged
     */

    /**
     * Setup a jbutton to be invisible and use the given actionlistener
     * @param button
     * @param listener
     */
    public static void setupJButton(JButton button, ActionListener listener) {
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(listener);
    }

    /**
     * Overloaded setupJButton with no actionlistener
     * @param button
     */
    public static void setupJButton(JButton button) {
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
}
