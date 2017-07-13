package views;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class GetUsername extends JPanel {

	JButton btnNext;
	private JTextField txtUsername;
	private JLabel lblUsernameError;
	
	public GetUsername(ActionListener listener) {
		setLayout(null);
		
		this.btnNext = new JButton("");
		btnNext.setBounds(617, 405, 98, 62);
		ViewHelper.setupJButton(btnNext, listener);
		add(btnNext);
		
		this.txtUsername = new JTextField();
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setFont(new Font("Dialog", Font.PLAIN, 22));
		txtUsername.setForeground(Color.BLACK);
		txtUsername.setToolTipText("Enter your username...");
		txtUsername.setBounds(173, 236, 399, 30);
		txtUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtUsername.setColumns(10);
        txtUsername.addActionListener(listener);
        add(txtUsername);

		lblUsernameError = new JLabel("Error: You must enter a username!");
		lblUsernameError.setForeground(Color.RED);
		lblUsernameError.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUsernameError.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsernameError.setBounds(173, 298, 399, 15);
        lblUsernameError.setVisible(false);
        add(lblUsernameError);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("images/get_username.jpg"));
		lblBackground.setBounds(0, 0, 745, 500);
		add(lblBackground);
	}
	
	public void showNoUsernameError() {
		lblUsernameError.setVisible(true);
		this.revalidate();
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JTextField getTxtUsername() {
		return txtUsername;
	}
}
