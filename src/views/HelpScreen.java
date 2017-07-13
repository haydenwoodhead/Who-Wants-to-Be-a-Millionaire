package views;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by hayden on 5/14/17.
 */
public class HelpScreen extends JPanel {

    public static final String NAME = "help_screen";

    private JButton btnExit;

    public HelpScreen() {
		setLayout(null);

        btnExit = new JButton("");
        btnExit.setBounds(636, 22, 75, 25);
        ViewHelper.setupJButton(btnExit);
        add(btnExit);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("images/help_screen.jpg"));
		lblBackground.setBounds(0, 0, 745, 500);
		add(lblBackground);
	}

    public JButton getBtnExit() {
        return btnExit;
    }
}
